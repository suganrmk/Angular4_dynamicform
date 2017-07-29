package com.perficient.hr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeRolesDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.RolesComponents;
import com.perficient.hr.service.EmployeeRolesService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeRolesService")
public class EmployeeRolesServiceImpl implements EmployeeRolesService {

protected Logger logger = LoggerFactory.getLogger(EmployeeRolesServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    EmployeeRolesDAO employeeRolesDAO;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
	@Override
	public Object saveEmpRoles(RolesComponents rolesComponents, String userId) {
		LoggerUtil.infoLog(logger, "Add Designation Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			Employee employee = employeeDAO.loadById(userId, session);
			ExceptionHandlingUtil.closeSession(session);
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			rolesComponents.setDtModified(new Date());
			rolesComponents.setModifiedBy(employee.getPk());
			RolesComponents rolesComp = (RolesComponents) loadEmpByRoles(rolesComponents.getRole().getPk().toString(), userId);
			if(rolesComp == null){
				rolesComponents.setDtCreated(new Date());
				rolesComponents.setCreatedBy(employee.getPk());
				session.save(rolesComponents);
				for(Employee emp : rolesComponents.getEmployee()){
					EmployeeRoles empRoles =  new EmployeeRoles();
					empRoles.setActive(PerfHrConstants.ACTIVE);
					empRoles.setDtCreated(new Date());
					empRoles.setCreatedBy(employee.getPk());
					empRoles.setDtModified(new Date());
					empRoles.setModifiedBy(employee.getPk());
					empRoles.setEmployee(emp);
					empRoles.setRoleId(rolesComponents.getRole());
					employeeRolesDAO.saveEmpRoles(empRoles, session);
				}
			} else {
				rolesComponents.setPk(rolesComp.getPk());
				rolesComponents.setCreatedBy(rolesComp.getCreatedBy());
				rolesComponents.setDtCreated(rolesComp.getDtCreated());
				session.update(rolesComponents);
				
				List<Employee> employeesDB = employeeRolesDAO.loadEmpByRoleId(rolesComponents.getRole().getPk(), session);
				List<String> dbList = new ArrayList<>();
				for(Employee emp: employeesDB){
					dbList.add(emp.getPk().toString());
				}
				List<String> tempList = new ArrayList<>();
				tempList.addAll(dbList);
				
				List<String> updatedList = new ArrayList<>();
				for(Employee emp: rolesComponents.getEmployee()){
					updatedList.add(emp.getPk().toString());
				}
				
				//Deleted Notifiers
				dbList.removeAll(updatedList);
				for(String empPk : dbList){
					EmployeeRoles empRole = employeeRolesDAO.getEmpRolesByRoleandEmployeeId(rolesComponents.getRole().getPk().toString(), empPk, session);
					empRole.setActive(PerfHrConstants.INACTIVE);
					session.update(empRole);
				}
				//New Notifiers
				updatedList.removeAll(tempList);
				for(String newPk : updatedList){
					Employee emp = employeeDAO.loadById(newPk, session);
					EmployeeRoles empRoles =  new EmployeeRoles();
					empRoles.setActive(PerfHrConstants.ACTIVE);
					empRoles.setDtCreated(new Date());
					empRoles.setCreatedBy(employee.getPk());
					empRoles.setDtModified(new Date());
					empRoles.setModifiedBy(employee.getPk());
					empRoles.setEmployee(emp);
					empRoles.setRoleId(rolesComponents.getRole());
					employeeRolesDAO.saveEmpRoles(empRoles, session);
				}
			}
			tx.commit();
			return rolesComponents;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add employee roles: "+rolesComponents.getRole(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add designation: "+rolesComponents.getRole(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object loadEmpByRoles(String roleId, String userId) {
		LoggerUtil.infoLog(logger, "Load All employee list.");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			RolesComponents rolesComponents = employeeRolesDAO.loadRoleComponents(Long.parseLong(roleId), session);
			if(rolesComponents != null){
				List<EmployeeRoles> empRoles = employeeRolesDAO.loadEmpRolesByRoles(roleId, session);
				List<Employee> employeeList = new ArrayList<>();
				for(EmployeeRoles empRole : empRoles){
					employeeList.add(empRole.getEmployee());
				}
				rolesComponents.setEmployee(employeeList);	
			}
			return rolesComponents;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee List ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee List ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadEmployeeRoles(String userId) {
		LoggerUtil.infoLog(logger, "Load Employees Role components list.");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<EmployeeRoles> empRoles = employeeRolesDAO.loadEmpRolesByEmployeeId(userId, session);
			List<Long> roleList = new ArrayList<>();
			for(EmployeeRoles empRole : empRoles){
				roleList.add(empRole.getRoleId().getPk());
			}
			List<RolesComponents> roleCompList = employeeRolesDAO.loadRoleCompList(roleList, session);
			RolesComponents roles = new RolesComponents();
			HashMap<String, String> compList = roles.getComponents(roleCompList);
			return compList;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee components ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee components ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadRolesByEmpId(String userId) {
		LoggerUtil.infoLog(logger, "Load Employee's Role.");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return employeeRolesDAO.loadEmpRolesByEmployeeId(userId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load employee roles ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load employee roles ", e); 
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}
}