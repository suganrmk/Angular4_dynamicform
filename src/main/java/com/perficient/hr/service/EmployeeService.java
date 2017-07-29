package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.form.EmployeeForm;
import com.perficient.hr.model.Employee;

public interface EmployeeService {
	
	public Object loadByUserId(String employeeId);
	
	public Object loadById(String employeeId);
	
	public Object loadByEmployeeId(String employeeId);
	
	public Object loadEmployees();
	
	public Object loadAllEmployeeByNames();
	
	public Object loadAllEmployeeNamesByDate(String stDate, String endDate);
	
	public Object loadAllEmployees();
	
//	@PreAuthorize("@emprolesService.hasRoles('empUpBtn')")
	public Object updateEmployee(Employee employee, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('empSvBtn')")
	public Object addEmployee(Employee employee, String userId);
	
//	@PreAuthorize("@emprolesService.hasRoles('empSvBtn')")
	public Object addNewEmployee(EmployeeForm employeeForm, String userId);
	
	public Object loadUserNameViewById(String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('empSvBtn')")
	public Object generateCrendentials(String userId);
	
	public Object loadEmployeeByDesHistory(String stDate, String endDate, String designation);
	
	public Object loadEmployeesByDesignation(String designation);
	
	public Object getEmployeeHierarchy(String userId, int limit, int year);

}
