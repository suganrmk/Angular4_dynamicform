package com.perficient.hr.service;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.Session;
import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.EmployeeLeaves;

public interface EmployeeLeavesService {

	public Object parseDocument(String fileName, String startYear, String totalLeaves);
	
	public Object parseOpeningBalDocument(String fileName);
	
	public Object importWfhCalInfoFromIcs(String fileName);
	
	public Object importOfficeEntry(String fileName, String employeeId);
	
	public Object loadLeaves(String leaveType, String calYear, String employeeId, String startDate, String endDate);
	
	public Object loadLeavesByYear(String leaveType, String calYear);
	
	public Object loadLeavesByMonth(String leaveType, String calYear, String calMonth);
	
	public Object loadLeaveById(String leaveId);
	
	public Object loadEmployeesByLeaveDate(Date leaveDate);
	
	public Object loadMyLeaves(String leaveType, String calYear, String employeeId);
	
	public Object applyLeave(EmployeeLeaves employeeLeaves, String userId, boolean isSystem);
	
	@PreAuthorize("@emprolesService.hasRoles('ptoUpBtn')")
	public Object updateLeave(EmployeeLeaves employeeLeaves, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('wfhUpBtn')")
	public Object updateWfh(EmployeeLeaves employeeLeaves, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('ptoDlBtn')")
	public Object deleteLeave(EmployeeLeaves employeeLeaves, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('wfhDlBtn')")
	public Object deleteWfh(EmployeeLeaves employeeLeaves, String userId);
	
	public Object getEmpLeaveBalance(String calYear, String employeeId);
	
	public Object updateEmpLeaveBalanceOnResignation(Date lastWorkingDate, String employeeId, Session session) throws ParseException;
	
	public Object getLeaveBalanceByMonth(String leaveType, String calYear, String employeeId);
	
	public Object getLeaveBalanceByType(String leaveType, String calYear, String employeeId);
	
	public Object loadLeaveReport(EmployeeLeaves employeeLeaves);
	
	public Object loadAllLeaveReport(EmployeeLeaves employeeLeaves);
	
	public Object saveEmployeeLeaveBalance(int calYear, String id, Session session) throws ParseException;

}
