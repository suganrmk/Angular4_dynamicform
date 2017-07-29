package com.perficient.hr.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;

import com.perficient.hr.model.EmployeeLeaveBalance;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.EmployeeOfficeEntry;

public interface EmployeeLeavesDAO {

	public List<Object> loadLeaves(String leaveType, String employeeId, String startDate, String endDate, Session session) throws ParseException;
	
	public EmployeeLeaves loadLeaveById(String leaveId, Session session);
	
	public EmployeeLeaves saveLeave(EmployeeLeaves employeeLeaves, Session session);
	
	public EmployeeLeaveDetails saveLeaveDetails(EmployeeLeaveDetails employeeLeavesDetails, Session session);
	
	public List<Object[]> getLeaveDetailsById(Long employeeLeaveId, Session session);
	
	public boolean updateLeave(EmployeeLeaves employeeLeaves, Session session);
	
	public boolean updateLeaveDetails(Long empLeaveId, boolean isActive, Session session);
	
	public Long getCarryLeaves(String employeeId, String year, Session session);
	
	public Long getLeaveBalance(String leaveType, String calYear, String calMonth, String employeeId, 
			Session session) throws ParseException;
	
	public EmployeeLeaveBalance getEmpLeaveBalance(String calYear, String employeeId, 
			Session session) throws ParseException;
	
	public List<Object[]> getEmpLeaveBalanceList(int year, String month, Set<Long> employeeIdList, 
			Session session) throws ParseException;
	
	public EmployeeLeaveBalance saveEmpLeaveBalance(EmployeeLeaveBalance employeeLeaveBalance, 
			Session session) throws ParseException;
	
	public EmployeeLeaveBalance updateEmpLeaveBalance(EmployeeLeaveBalance employeeLeaveBalance, 
			Session session) throws ParseException;
	
	public Long getLeavesTakenByDate(String leaveType, Date startDt, Date endDt, Long employeeId, 
			 Session session) throws ParseException;
	
	public Object getMinMaxLeaveByDate(String leaveType, Date startDt, Date endDt, Long employeeId, 
			 Session session) throws ParseException;
	
	public Map<Date, Integer> getLeavesBtwDate(List<String> leaveType, Date startDt, Date endDt, Long employeeId, Session session);
	
	public Object getLeaveBalanceByMonth(String leaveType, String calYear, String employeeId, 
			Session session) throws ParseException;
	
	public Object getLeaveBalanceByType(String leaveType, String calYear, String employeeId, 
			Session session) throws ParseException;
	
	public List<EmployeeLeaveDetails> loadLeaveReport(EmployeeLeaves employeeLeaves, Session session);
	
	public List<Long> loadEmployeesByLeaveDate(Date leaveDate, Session session);
	
	public List<Object[]> loadLeaveReportByEmpId(EmployeeLeaves employeeLeaves, Session session) throws ParseException;

	public void saveLeave(Session session, Row row) throws ParseException;

	public List<Object[]> isLeaveApplied(EmployeeLeaves employeeLeaves, Session session);
	
	public boolean isLeaveApplied(Date date, Session session);
	
	public Long getHoursByLeaveId(EmployeeLeaves employeeLeaves, Session session);
	
	public void saveOfficeEntry(EmployeeOfficeEntry employeeOfficeEntry, Session session);
	
	public List<Object> loadLeaveByEmpIdAndDate(Long empId, String startDate, String endsAt, Session session);
}
