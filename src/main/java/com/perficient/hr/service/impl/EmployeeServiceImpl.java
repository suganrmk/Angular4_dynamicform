package com.perficient.hr.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.DesignationsDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.form.EmployeeForm;
import com.perficient.hr.model.Designations;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeBackOfficeInfo;
import com.perficient.hr.model.EmployeeEducation;
import com.perficient.hr.model.EmployeeInterviewFeedback;
import com.perficient.hr.model.EmployeeLeaveBalance;
import com.perficient.hr.model.EmployeeNamesView;
import com.perficient.hr.model.EmployeeView;
import com.perficient.hr.model.EmployeeWorkExperience;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.EmployeeLeavesService;
import com.perficient.hr.service.EmployeeService;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Repository("employeeService")
public class EmployeeServiceImpl extends PrftDbObjectManager<Employee> implements EmployeeService{

	protected Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	private String getError = "Unable to get employee: ";
	private String updateError = "Unable to update employee: ";

    @Autowired
	private EmployeeDAO employeeDAO;
    
    @Autowired
	private LoginService loginService;
    
    @Autowired
	private DesignationsDAO designationsDAO;
    
    @Autowired
	private EmployeeLeavesService employeeLeavesService;
    @Autowired
    private EmployeeLeavesDAO employeeLeavesDAO;
	
    @Override
    public Object loadByUserId(String pk) {
    	LoggerUtil.infoLog(logger, "Loading employee record for: "+pk);
		Session session = null;
		EmployeeView employee = null;
		try {
			session = sessionFactory.openSession();
			employee = employeeDAO.loadByUserId(pk, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, getError+pk , e);
			return ExceptionHandlingUtil.returnErrorObject(getError+pk , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}

    @Override
	public Object loadById(String pk) {
		LoggerUtil.infoLog(logger, "Loading employee record for: "+pk);
		Session session = null;
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			employee = employeeDAO.loadById(pk, session);;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, getError+pk , e);
			return ExceptionHandlingUtil.returnErrorObject(getError+pk , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}
    
    @Override
	public Object loadByEmployeeId(String employeeId) {
    	LoggerUtil.infoLog(logger, "Loading employee record for: "+employeeId);
		Session session = null;
		Employee employee = null;
		try {
			session = sessionFactory.openSession();
			employee = employeeDAO.loadByEmployeeId(employeeId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, getError+employeeId , e);
			return ExceptionHandlingUtil.returnErrorObject(getError+employeeId , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}
    
    @Override
	public Object loadEmployees() {
    	LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		List<EmployeeView> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadEmployees(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}
    
    @Override
	public Object loadAllEmployeeByNames() {
    	LoggerUtil.infoLog(logger, "Load All employee Name list.");
		Session session = null;
		List<EmployeeNamesView> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadAllEmployeeByNames(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee Name List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee Name List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}
    
    @Override
	public Object loadAllEmployeeNamesByDate(String stDate, String endDate) {
    	LoggerUtil.infoLog(logger, "Load All employee Name list by date.");
		Session session = null;
		List<EmployeeNamesView> list = null;
		try {
			session = sessionFactory.openSession();
			DateUtils.convertMilliSecondsToStringDate(stDate);
			list = employeeDAO.loadAllEmployeeNamesByDate(DateUtils.convertMilliSecondsToDate(stDate), 
					DateUtils.convertMilliSecondsToDate(endDate), session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee Name List by date", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee Name List by date", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

    @Override
	public Object addEmployee(Employee employee, String userId) {
		LoggerUtil.infoLog(logger, "Save New Employee deatils");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee loggedEmployee = employeeDAO.loadById(userId, session);
			employee.setFirstName(PerfUtils.capitalizeFully(employee.getFirstName()));
			employee.setLastName(PerfUtils.capitalizeFully(employee.getLastName()));
			employee.setDtCreated(new Date());
			employee.setDtModified(new Date());
			employee.setCreatedBy(loggedEmployee.getPk());
			employee.setModifiedBy(loggedEmployee.getPk());
			
			Employee emp = employeeDAO.loadByEmployeeId(employee.getEmployeeId(), session);
			//Employee with same employee id is still active
			if(emp != null && emp.getLastWorkDate() == null){
				throw new RecordExistsException("Employee Id already exists: "+employee.getEmployeeId());
			} else if(emp != null && emp.getLastWorkDate() != null){
				//Employee rejoining-update the old entry employee id with _R sequence
				String empId = emp.getEmployeeId();
				if(empId.contains("_R")){
					String index = emp.getEmployeeId().substring(emp.getEmployeeId().indexOf("_R")+2, emp.getEmployeeId().length());
					empId = emp.getEmployeeId().substring(0, emp.getEmployeeId().indexOf("_")+2)+(Integer.parseInt(index)+1);
				} else {
					empId += "_R1";
				}
				emp.setEmployeeId(empId);
				emp.setDtModified(new Date());
				emp.setModifiedBy(loggedEmployee.getPk());
				employeeDAO.updateEmployee(emp, userId, session);
			}
			session.save(employee);
			Calendar cal = Calendar.getInstance();
			cal.setTime(employee.getJoinDate());
			employeeLeavesService.saveEmployeeLeaveBalance(cal.get(Calendar.YEAR), employee.getPk().toString(), session);
			tx.commit();	
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to save employee: "+employee.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save employee: "+employee.getEmployeeId(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employee;
	}
    
    @Override
	public Object addNewEmployee(EmployeeForm employeeForm, String userId) {
		LoggerUtil.infoLog(logger, "Save New Employee details");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeForm.getEmployee();
			Designations designation = designationsDAO.loadDesignationById(employee.getDesignations().getPk().toString(), session);
			employee.setDesignations(designation);
			employee.setFirstName(PerfUtils.capitalizeFully(employee.getFirstName()));
			employee.setLastName(PerfUtils.capitalizeFully(employee.getLastName()));
			//employee.setFlag(PerfHrConstants.INACTIVE);
			try{
				exists(employee, "employeeId", employee.getEmployeeId(), null);
			} catch (RecordExistsException e) {
				throw new RecordExistsException("Employee Id already exists: "+employee.getEmployeeId());
			}
			session.save(employee);
			Calendar cal = Calendar.getInstance();
			cal.setTime(employee.getJoinDate());
			employeeLeavesService.saveEmployeeLeaveBalance(cal.get(Calendar.YEAR), employee.getPk().toString(), session);
			EmployeeBackOfficeInfo employeeBackOfficeInfo = employeeForm.getEmployeeBackOfficeInfo();
			employeeBackOfficeInfo.setEmployeeId(employee.getPk());
			session.save(employeeBackOfficeInfo);
			employeeBackOfficeInfo.setEmployeeId(employee.getPk());
			for(EmployeeInterviewFeedback empFeedBack: employeeForm.getEmpInterviewFeedbackList()){
				empFeedBack.setEmployeeId(employee.getPk());
				session.save(empFeedBack);
			}
			for(EmployeeEducation employeeEducation: employeeForm.getEmployeeEducationList()){
				employeeEducation.setEmployeeId(employee.getPk());
				session.save(employeeEducation);
			}
			for(EmployeeWorkExperience employeeWorkExperience: employeeForm.getEmpWorkExperienceList()){
				employeeWorkExperience.setEmployeeId(employee.getPk());
				session.save(employeeWorkExperience);
			}
			tx.commit();	
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.CONFLICT.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to save employee: "+employeeForm.getEmployee().getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save employee: "+employeeForm.getEmployee().getEmployeeId(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employeeForm;
	}
    
    @Override
	public Object generateCrendentials(String userId) {
    	LoggerUtil.infoLog(logger, "generateCrendentials");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			loginService.createUser(employee, session);
			Calendar cal = Calendar.getInstance();
			cal.setTime(employee.getJoinDate());
			employeeLeavesService.saveEmployeeLeaveBalance(cal.get(Calendar.YEAR), userId, session);
			employee.setFlag(PerfHrConstants.ACTIVE);
			employeeDAO.updateEmployee(employee, userId, session);
			tx.commit();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to generateCrendentials for employee: "+userId, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to generateCrendentials for employee: "+userId, e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}
    
	@Override
	public Object updateEmployee(Employee employee, String userId) {
		LoggerUtil.infoLog(logger, "Update employee details for the Employee: "+userId);
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee empUpdate = employeeDAO.loadByEmployeeId(employee.getEmployeeId(), session);
			if(empUpdate.getDesignations().getPk() != employee.getDesignations().getPk()){
				if(employee.getDesignation_effective_date() == null){
					throw new PrftException("Please provide Designation Effective Date.");		
				} else if((empUpdate.getDesignation_effective_date() != null
							&& (employee.getDesignation_effective_date().getTime() == empUpdate.getDesignation_effective_date().getTime()))){
					throw new PrftException("Please provide new Designation Effective Date.");
				}
			}
			employee.setFirstName(PerfUtils.capitalizeFully(employee.getFirstName()));
			employee.setLastName(PerfUtils.capitalizeFully(employee.getLastName()));
			employee.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
			employee.setDtModified(new Date());
			try{
				exists(employee, "employeeId", employee.getEmployeeId(), employee.getPk());
			} catch (RecordExistsException e) {
				throw new RecordExistsException("Employee Id already exists: "+employee.getEmployeeId());
			}
			if(empUpdate.getLastWorkDate() == null && employee.getLastWorkDate() != null){
				employee.setFlag(PerfHrConstants.INACTIVE);
				if(!(empUpdate.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR)
						|| empUpdate.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
						||  empUpdate.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING))){
					EmployeeLeaveBalance empLeaveBal = (EmployeeLeaveBalance) employeeLeavesService.updateEmpLeaveBalanceOnResignation(employee.getLastWorkDate(), employee.getPk().toString(), session);
					if(empLeaveBal != null)
						employeeLeavesDAO.updateEmpLeaveBalance(empLeaveBal, session);	
				}
			}
			employeeDAO.updateEmployee(employee, userId, session);
			tx.commit();
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.CONFLICT.value());
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		}  catch(Exception e){
			LoggerUtil.errorLog(logger, updateError+employee.getEmployeeId(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(updateError+employee.getEmployeeId(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}

	@Override
	public Object loadEmployeeByDesHistory(String stDate,
			String endDate, String designationName) {
		LoggerUtil.infoLog(logger, "Load Employee Designation History");
		List<EmployeeView> list = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadEmployeeByDesHistory(stDate, endDate, designationName, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to employee List By Designation", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to employee List By Designation", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadAllEmployees() {
		LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		List<Employee> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadAllEmployees(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load all employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load all employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object getEmployeeHierarchy(String userId, int limit, int year) {
		LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		List<Employee> list = null;
		try {
			session = sessionFactory.openSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			list = employeeDAO.getEmployeeHierarchy(Long.parseLong(userId), limit, sdf.parse(year+"-01-01"), sdf.parse(year+"-12-31"), session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load all employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load all employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadUserNameViewById(String userId) {
		LoggerUtil.infoLog(logger, "Load loadUserNameById.");
		Session session = null;
		EmployeeNamesView env = null;
		try {
			session = sessionFactory.openSession();
			env = employeeDAO.loadUserNameById(userId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load loadUserNameById ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to loadUserNameById ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return env;
	}

	@Override
	public Object loadEmployeesByDesignation(String designation) {
		LoggerUtil.infoLog(logger, "Load All employee Name list.");
		Session session = null;
		List<EmployeeNamesView> list = null;
		try {
			session = sessionFactory.openSession();
			list = employeeDAO.loadEmployeeNamesByDesignationId(designation, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee Name List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee Name List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

}
