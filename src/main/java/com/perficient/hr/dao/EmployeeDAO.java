package com.perficient.hr.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeNamesView;
import com.perficient.hr.model.EmployeeView;

public interface EmployeeDAO {

	public EmployeeView loadByUserId(String employeeId, Session session);
	
	public Employee loadById(String pk, Session session);
	
	public Employee loadByEmail(String email, Session session);
	
	public Employee loadByEmployeeId(String employeeId, Session session);
	
	public List<EmployeeView> loadEmployees(Session session);
	
	public List<EmployeeNamesView> loadAllEmployeeByNames(Session session);
	
	public List<EmployeeNamesView> loadEmployeeNamesByDesignationId(String designationId, Session session);
	
	public List<EmployeeNamesView> loadAllEmployeeNamesByDate(Date stDate, Date endDate, Session session);
	
	public EmployeeNamesView loadUserNameById(String pk, Session session);
	
	public EmployeeNamesView loadUserNameViewById(String employeeId, Session session);
	
	public EmployeeNamesView loadUserNameViewByEmail(String email, Session session);
	
	public List<Employee> loadAllEmployees(Session session);
	
	public List<Employee> loadEmployeesByPk(Set<Long> empReportIds, Session session);
	
	public boolean updateEmployee(Employee employee, String userId, Session session);
	
	public boolean addEmployee(Employee employee, Session session);
	
	public List<EmployeeView> loadEmployeeByDesHistory(String stDate, String endDate, String desingation, Session session);
	
	public List<Employee> getEmployeeHierarchy(long pk, int limit, Date stDate, Date endDate, Session session);

}
