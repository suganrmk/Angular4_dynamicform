package com.perficient.hr.aspect;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.model.AuditDetails;
import com.perficient.hr.model.Designations;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeDesignation;
import com.perficient.hr.model.EmployeeGoals;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.EmployeeLeavesView;
import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.EmployeeView;
import com.perficient.hr.model.Holidays;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.ProjectGoals;
import com.perficient.hr.model.ProjectMembers;
import com.perficient.hr.model.Projects;
import com.perficient.hr.model.Roles;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Aspect
public class AuditAspect {

	protected Logger PftLogger = LoggerFactory.getLogger(AuditAspect.class);
	
	@Autowired
	private HttpSession httpSession;
	
	private AuditDetails auditDetails =  new AuditDetails();
	
	@AfterReturning(pointcut=" execution(* com.perficient.hr.dao.*.apply*(..))",returning="object")
	private void afterReturning(JoinPoint joinPoint, Object object) throws Exception {
		if(httpSession != null && httpSession.getAttribute(PerfHrConstants.USER_ID) != null && joinPoint != null && joinPoint.getSignature() != null && 
				PerfUtils.mandatory(joinPoint.getSignature().getName()) &&  object != null){
			auditDetails.setComponentMethod(joinPoint.getSignature().getName());
			String signatureAsArray[] = joinPoint.getSignature().toString().split("\\.");
			auditDetails.setComponentName(signatureAsArray[4]);
			auditDetails.setEmployeeId(httpSession.getAttribute(PerfHrConstants.USER_ID).toString());
			auditDetails.setDate(new Date());
			auditDetails = findTypeOfObject(auditDetails,object);
			auditDetails.setHttpStatus(Response.Status.OK.getStatusCode());
			//loginService.saveAuditDetail(auditDetails);	
		}
	}
	
	@Before("execution(* com.perficient.hr.dao.*.update*(..)) ||" + "execution(* com.perficient.hr.dao.*.delete*(..)) ")
	private void before(JoinPoint joinPoint) throws Exception {
		if(httpSession != null && httpSession.getAttribute(PerfHrConstants.USER_ID) != null && joinPoint != null && joinPoint.getSignature() != null &&
				PerfUtils.mandatory(joinPoint.getSignature().getName()) && joinPoint.getArgs()  != null ){
			auditDetails.setComponentMethod(joinPoint.getSignature().getName());
			String signatureAsArray[] = joinPoint.getSignature().toString().split("\\.");
			auditDetails.setComponentName(signatureAsArray[4]);
			auditDetails.setEmployeeId(httpSession.getAttribute(PerfHrConstants.USER_ID).toString());
			auditDetails.setDate(new Date());
			Object object[] = joinPoint.getArgs();
			auditDetails = findTypeOfObject(auditDetails,object[0]);
			auditDetails.setHttpStatus(Response.Status.OK.getStatusCode());
			//loginService.saveAuditDetail(auditDetails);
		}
	}

	@AfterThrowing(pointcut="execution(* com.perficient.hr.controller.*.update*(..)) ||" + "execution(* com.perficient.hr.controller.*.delete*(..))" + 
	"execution(* com.perficient.hr.controller.*.apply*(..)) ",throwing="excep")
	private void AfterThrowing(JoinPoint joinPoint,Throwable excep) throws Exception {
		auditDetails.setComponentMethod(joinPoint.getSignature().getName());
		String signatureAsArray[] = joinPoint.getSignature().toString().split("\\.");
		auditDetails.setComponentName(signatureAsArray[4]);
		auditDetails.setEmployeeId(httpSession.getAttribute(PerfHrConstants.USER_ID).toString());
		auditDetails.setDate(new Date());
		Object object[] = joinPoint.getArgs();
		auditDetails = findTypeOfObject(auditDetails,object[0]);
		auditDetails.setHttpStatus(Response.Status.FORBIDDEN.getStatusCode());
//		loginService.saveAuditDetail(auditDetails);
   }
	
	
	//Find type of object and set the primary key
	private AuditDetails findTypeOfObject(AuditDetails auditDetails, Object object) throws Exception{
		if(object instanceof Designations){
			Designations designations = (Designations) object;
			auditDetails.setRecord_pk(designations.getPk());
		}else if(object instanceof Employee){
			Employee employee = (Employee) object;
			auditDetails.setRecord_pk(employee.getPk());
		}else if(object instanceof EmployeeDesignation){
			EmployeeDesignation employeeDesignation = (EmployeeDesignation) object;
			auditDetails.setRecord_pk(employeeDesignation.getPk());
		}else if(object instanceof EmployeeGoals){
			EmployeeGoals employeeGoals = (EmployeeGoals) object;
			auditDetails.setRecord_pk(employeeGoals.getPk());
		}else if(object instanceof EmployeeLeaveDetails){
			EmployeeLeaveDetails employeeLeaveDetails = (EmployeeLeaveDetails) object;
			auditDetails.setRecord_pk(employeeLeaveDetails.getPk());
		}else if(object instanceof EmployeeLeaves){
			EmployeeLeaves employeeLeaves = (EmployeeLeaves) object;
			auditDetails.setRecord_pk(employeeLeaves.getPk());
		}else if(object instanceof EmployeeLeavesView){
			EmployeeLeavesView employeeLeavesView = (EmployeeLeavesView) object;
			auditDetails.setRecord_pk(employeeLeavesView.getEmployeeLeavesId());
		}else if(object instanceof EmployeeRoles){
			EmployeeRoles employeeRoles = (EmployeeRoles) object;
			auditDetails.setRecord_pk(employeeRoles.getPk());
		}else if(object instanceof EmployeeView){
			EmployeeView employeeView = (EmployeeView) object;
			auditDetails.setRecord_pk(employeeView.getPk());
		}else if(object instanceof Holidays){
			Holidays holidays = (Holidays) object;
			auditDetails.setRecord_pk(holidays.getPk());
		}else if(object instanceof Notification){
			Notification notification = (Notification) object;
			auditDetails.setRecord_pk(notification.getPk());
		}else if(object instanceof ProjectGoals){
			ProjectGoals projectGoals = (ProjectGoals) object;
			auditDetails.setRecord_pk(projectGoals.getPk());
		}else if(object instanceof ProjectMembers){
			ProjectMembers projectMembers = (ProjectMembers) object;
			auditDetails.setRecord_pk(projectMembers.getPk());
		}else if(object instanceof Projects){
			Projects projects = (Projects) object;
			auditDetails.setRecord_pk(projects.getPk());
		}else if(object instanceof Roles){
			Roles roles = (Roles) object;
			auditDetails.setRecord_pk(roles.getPk());
		}
		auditDetails = getEmployeeDetails(auditDetails);
		return auditDetails;
	}

	//set first name
	private AuditDetails getEmployeeDetails(AuditDetails auditDetails) throws Exception {
//		Employee employee = (Employee) loginService.getEmployeeDetail(httpSession.getAttribute(PerfHrConstants.USER_ID).toString());
//		auditDetails.setEmployeeName(employee.getFirstName().concat(" "+employee.getLastName()));
		return auditDetails;
	}

}
