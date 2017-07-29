package com.perficient.hr.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaveBalance;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.EmployeeOfficeEntry;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.model.type.NotificationStatusType;
import com.perficient.hr.model.type.NotificationType;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeLeavesDAO")
public class EmployeeLeavesDAOImpl implements EmployeeLeavesDAO {

	protected Logger logger = LoggerFactory.getLogger(EmployeeLeavesDAOImpl.class);

	private String employeeId = "employeeId";
	private String startDate = "-01-01";
	
	@Autowired
	EmployeeDAO employeeDAO;
    
	@Override
	public void saveLeave(Session session, Row row) throws ParseException{
		String leaveType = row.getCell(12).toString().toUpperCase().replace(" ", "_");
        int hours = 0;
        try {
        	hours = Math.round(Float.parseFloat(row.getCell(31).toString()));	
        } catch (NumberFormatException e) {
			
		}
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        if((leaveType.equals(LeaveType.PTO.getLeaveType()) 
        		|| leaveType.equals(LeaveType.UNPLANNED_PTO.getLeaveType())) && hours != 0){
    		String sqlQuery =" from Employee as o where o.employeeId=:employeeId";
    		Query query = session.createQuery(sqlQuery);
    		query.setParameter(employeeId, row.getCell(18).toString());
    		Employee employee = (Employee) query.uniqueResult();
    		if(employee != null) {
    			EmployeeLeaves employeeLeaves = new EmployeeLeaves();
    			employeeLeaves.setEmployeeId(employee.getPk());
    			employeeLeaves.setAppliedById(employee.getPk());
    			employeeLeaves.setRequestType(leaveType);
    			String comments = row.getCell(33).toString();
    			if(comments.length() >= 100)
    				comments = comments.substring(0, 99);
        		employeeLeaves.setComments(comments);
        		employeeLeaves.setTitle(row.getCell(19).toString()+" - "+leaveType);
        		Date dt = sdf.parse("01-"+row.getCell(29).toString());
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(dt);
        		logger.info(row.getCell(19).toString()+" Date "+row.getCell(30).toString()+" month "+(cal.get(Calendar.MONTH)+1));
        		Date leaveDate = DateUtils.getDate(row.getCell(30).toString(), (cal.get(Calendar.MONTH)+1));
        		employeeLeaves.setStartsAt(leaveDate);
        		employeeLeaves.setEndsAt(leaveDate);
        		employeeLeaves.setHours((hours <= 4) ? 4:8);
        		employeeLeaves.setDtCreated(new Date());
        		employeeLeaves.setDtModified(new Date());
        		employeeLeaves.setCreatedBy(employee.getPk());
        		employeeLeaves.setModifiedBy(employee.getPk());
        		
        		session.save(employeeLeaves);
        		
        		EmployeeLeaveDetails empLeaveDetails = new EmployeeLeaveDetails();
        		empLeaveDetails.setEmployeeLeavesId(employeeLeaves.getPk());
        		empLeaveDetails.setLeaveDate(leaveDate);
        		empLeaveDetails.setHours((hours <= 4) ? 4:8);
        		empLeaveDetails.setDtCreated(new Date());
        		empLeaveDetails.setDtModified(new Date());
        		empLeaveDetails.setCreatedBy(employee.getPk());
        		empLeaveDetails.setModifiedBy(employee.getPk());
        		saveLeaveDetails(empLeaveDetails, session);
        		
        		Employee supervisor = employeeDAO.loadById(String.valueOf(employee.getSupervisor()), session);
        		
        		Notification notification = new Notification();
        		notification.setIdGeneric(employeeLeaves.getPk());
        		notification.setNotificationTo(supervisor.getPk());
        		notification.setNotificationStatus(NotificationStatusType.APPROVED.getNotificationStatusType());
        		notification.setNotificationType(NotificationType.PTO.getNotificationType());
        		notification.setDtCreated(new Date());
        		notification.setDtModified(new Date());
        		notification.setCreatedBy(employee.getPk());
        		notification.setModifiedBy(employee.getPk());
        		session.save(notification);
    		}
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> loadLeaves(String leaveType, String employeeId, 
			String startDate, String endDate, Session session) throws ParseException{
		String sqlQuery = "select el, sum(eld.hours) as leavehours,SUM(CASE WHEN (hd.pk is null) THEN eld.hours ELSE 0 END) as totalhours, nt.notificationTo, n.notificationStatus from "
				+ " EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ " left join eld.notification as n"
				+ " left join eld.notificationView as nt"
				+ " WHERE el.requestType in (:requestTypes) AND el.active=:active and nt.notificationType=:notificationType and n.notificationTo=el.employeeId"
				+ " and el.pk=nt.idGeneric and eld.active=:active AND eld.leaveDate"
				+ " between '"+startDate+"' and '"+endDate+"'";
		if(employeeId != null)
			sqlQuery += " AND el.employeeId=:employeeId";
		sqlQuery += " group by el.pk";
		Query query = session.createQuery(sqlQuery);
		if(leaveType.equals(LeaveType.PTO.getLeaveType())){
			query.setParameter("notificationType", LeaveType.PTO.getLeaveType());
			leaveType = LeaveType.ALL_PTO.getLeaveType();
		} else {
			query.setParameter("notificationType", LeaveType.WFH.getLeaveType());
			leaveType = LeaveType.ALL_WFH.getLeaveType();
		}
			
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		if(employeeId != null)
			query.setParameter("employeeId", Long.parseLong(employeeId));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLeaveDetails> loadLeaveReport(EmployeeLeaves employeeLeaves, Session session){
		String sqlQuery = "select eld.leaveDate, eld.hours, el.requestType from "
				+ " EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ " WHERE el.requestType in (:requestTypes) AND el.active=:active and el.pk=eld.employeeLeavesId and el.employeeId=:employeeId "
				+ " and eld.active=:active AND eld.leaveDate "
				+ " between '"+new java.sql.Timestamp(employeeLeaves.getStartsAt().getTime())+"' and "
				+ " '"+new java.sql.Timestamp(employeeLeaves.getEndsAt().getTime())+"'";
		String leaveType = employeeLeaves.getRequestType();
		Query query = session.createQuery(sqlQuery);
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeId", employeeLeaves.getEmployeeId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> isLeaveApplied(EmployeeLeaves employeeLeaves, Session session) {
		String sqlQuery = "select eld.leaveDate, eld.hours, el.dtFromHalf, el.dtEndHalf from EmployeeLeaves el, EmployeeLeaveDetails eld WHERE el.active=:active AND "
				+ "eld.active=:active and el.pk=eld.employeeLeavesId and el.employeeId=:employeeId and eld.leaveDate between '"+ new java.sql.Date(employeeLeaves.getStartsAt().getTime())+"' and "
						+ " '"+new java.sql.Date(employeeLeaves.getEndsAt().getTime())+"'";
		if(employeeLeaves.getPk() != null){
			sqlQuery += " and el.pk<>'"+employeeLeaves.getPk()+"'";
		}
		
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeId", employeeLeaves.getEmployeeId());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> loadLeaveReportByEmpId(EmployeeLeaves employeeLeaves, Session session) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(employeeLeaves.getEndsAt().getTime());
		int calYear = cal.get(Calendar.YEAR);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String month = new SimpleDateFormat("MMM").format(cal.getTime()).toString().toLowerCase();
		if(month.equalsIgnoreCase("DEC")){
			month = "decem";
		}
		
		//set the last date of the month and check
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date restOfLeavesEndDt = cal.getTime();
		Date restOfLeavesStDt  = cal.getTime();
		String restOfLeaveStartDate = "";
		String restOfLeavesEndDate = "";
		//check if the end date provided and the last date of the month are same
		if(cal.getTime().getTime() != employeeLeaves.getEndsAt().getTime()){
			restOfLeavesEndDate = sf.format(restOfLeavesEndDt);
			cal.setTimeInMillis(employeeLeaves.getEndsAt().getTime());
			//Add one more day to the end date
			cal.add(Calendar.DATE, 1);
			restOfLeavesStDt = cal.getTime();
			restOfLeaveStartDate = sf.format(restOfLeavesStDt);
		}
		
		String sqlQuery = "";
		if(employeeLeaves.getRequestType().equals(LeaveType.ALL_PTO.getLeaveType())){
			sqlQuery = "select a.*, b.* from (SELECT e.employee_id  as employeeId, e.pk as pk, e.firstname as firstName, e.lastname as lastName, sum(ld.hours) as totalHours,"
				+ " sum(if(el.request_type='"+LeaveType.PTO.getLeaveType()+"', ld.hours, 0)) AS ptohours,"
				+ " sum(if(el.request_type='"+LeaveType.UNPLANNED_PTO.getLeaveType()+"', ld.hours, 0)) AS unplannedhours,"
				+ " sum(if(el.request_type='"+LeaveType.LOSS_OF_PAY.getLeaveType()+"', ld.hours, 0)) AS lophours,"
				+ " sum(if(el.request_type='"+LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()+"', ld.hours, 0)) AS matpaidhours,"
				+ " sum(if(el.request_type='"+LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType()+"', ld.hours, 0)) AS matunpaidhours,"
				+ " elb.op_bal as carried_on, elb."+month+" as leave_bal, "
				+ " sum(if(el.request_type='"+LeaveType.LOP_ADJUSTMENTS.getLeaveType()+"', ld.hours, 0)) AS lopadjustments, "
				+ " case when (e.last_working_date is not null and MONTH(e.last_working_date) < MONTH('"+sf.format(employeeLeaves.getEndsAt())+"')) then '1' else '0' end as isValidUser"
				+ " FROM employee_leaves_details ld"
				+ " left join employee_leaves el on ld.employee_leaves_pk=el.pk"
				+ " left join employee e on e.pk=el.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " left join employee_leave_balance elb on elb.year = '"+calYear+"' and elb.employee_pk = e.pk"
				+ " where el.employee_pk=e.pk"
				+ " and el.active='"+PerfHrConstants.ACTIVE+"' and ld.active='"+PerfHrConstants.ACTIVE+"'"
				+ " and ld.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by e.employee_id) a"
				+ " left outer join "
				+ " (select e.employee_id  as employeeId, "
				+ " sum(if(el.request_type in ('"+LeaveType.PTO.getLeaveType()+"', '"+LeaveType.UNPLANNED_PTO.getLeaveType()+"'),"
				+ " ld.hours, 0)) AS nonearnedhours FROM employee_leaves_details ld left join employee_leaves el on ld.employee_leaves_pk=el.pk "
				+ " left join employee e on e.pk=el.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where el.employee_pk=e.pk and el.active='"+PerfHrConstants.ACTIVE+"' and ld.active='"+PerfHrConstants.ACTIVE+"'  and "
				+ " ld.leave_dt between '"+restOfLeaveStartDate+"' and '"+restOfLeavesEndDate+"'"
				+ " group by e.employee_id) b on a.employeeId=b.employeeId";
		} else {
			sqlQuery = "SELECT e.employee_id  as employeeId, e.pk as pk, e.firstname as firstName, e.lastname as lastName, sum(ld.hours) as totalHours,"
				+ " sum(if(el.request_type='"+LeaveType.WFH.getLeaveType()+"', ld.hours, 0)) AS wfhhours,"
				+ " sum(if(el.request_type='"+LeaveType.MATERNITY_WFH.getLeaveType()+"', ld.hours, 0)) AS matwfhhours,"
				+ " sum(if(el.request_type='"+LeaveType.PATERNITY_WFH.getLeaveType()+"', ld.hours, 0)) AS patwfhhours"
				+ " FROM employee_leaves_details ld"
				+ " left join employee_leaves el on ld.employee_leaves_pk=el.pk"
				+ " left join employee e on e.pk=el.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where el.employee_pk=e.pk"
				+ " and el.active='"+PerfHrConstants.ACTIVE+"' and ld.active='"+PerfHrConstants.ACTIVE+"'"
				+ " and ld.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by e.employee_id";	
		}
		SQLQuery query = session.createSQLQuery(sqlQuery);
		//Don't change the order in which the scalar values are set.  All values were handled based on its index position.
		query.addScalar("employeeId", new StringType());
		query.addScalar("firstName", new StringType());
		query.addScalar("lastName", new StringType());
		query.addScalar("totalHours", new LongType());
		if(employeeLeaves.getRequestType().equals(LeaveType.ALL_PTO.getLeaveType())){
			query.addScalar("ptohours", new LongType());
			query.addScalar("unplannedhours", new LongType());
			query.addScalar("lophours", new LongType());
			query.addScalar("matpaidhours", new LongType());
			query.addScalar("matunpaidhours", new LongType());
			query.addScalar("carried_on", new LongType());
			query.addScalar("leave_bal", new LongType());
			query.addScalar("nonearnedhours", new LongType());
			query.addScalar("lopadjustments", new LongType());
			query.addScalar("isValidUser", new StringType());
		} else {
			query.addScalar("wfhhours", new LongType());
			query.addScalar("matwfhhours", new LongType());
			query.addScalar("patwfhhours", new LongType());
		}
		query.addScalar("pk", new LongType());
		return query.list();
	}
	 
	private List<String> getLeaveTypeList(String leaveType){
		List<String> leaveTypeList = new ArrayList<>();
		if(leaveType.equals(LeaveType.ALL_PTO.getLeaveType())){
			leaveTypeList.add(LeaveType.PTO.getLeaveType());
			leaveTypeList.add(LeaveType.UNPLANNED_PTO.getLeaveType());
			leaveTypeList.add(LeaveType.COMPENSATORY_OFF.getLeaveType());
			leaveTypeList.add(LeaveType.LOSS_OF_PAY.getLeaveType());
			leaveTypeList.add(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType());
			leaveTypeList.add(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType());
			leaveTypeList.add(LeaveType.SABATICAL.getLeaveType());
		} else if(leaveType.equals(LeaveType.ONLY_PTO.getLeaveType())){
			leaveTypeList.add(LeaveType.PTO.getLeaveType());
			leaveTypeList.add(LeaveType.UNPLANNED_PTO.getLeaveType());
		} else if(leaveType.equals(LeaveType.ALL_WFH.getLeaveType())){
			leaveTypeList.add(LeaveType.WFH.getLeaveType());
			leaveTypeList.add(LeaveType.PATERNITY_WFH.getLeaveType());
			leaveTypeList.add(LeaveType.MATERNITY_WFH.getLeaveType());
		} else
			leaveTypeList.add(LeaveType.valueOf(leaveType).getLeaveType());
		return leaveTypeList;
	}
	
	@Override
	public EmployeeLeaves saveLeave(EmployeeLeaves employeeLeaves, Session session){
		session.save(employeeLeaves);
		return employeeLeaves;
	}
	
	@Override
	public EmployeeLeaveDetails saveLeaveDetails(EmployeeLeaveDetails employeeLeaveDetails, Session session){
		session.save(employeeLeaveDetails);
		return employeeLeaveDetails;
	}

	@Override
	public boolean updateLeave(EmployeeLeaves employeeLeaves, Session session){
		session.merge(employeeLeaves);
		return true;
	}
	
	@Override
	public boolean updateLeaveDetails(Long empLeaveId, boolean isActive, Session session){
		String sqlQuery = "Update EmployeeLeaveDetails el set el.active=:active where el.employeeLeavesId=:employeeLeavesId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", isActive);
		query.setParameter("employeeLeavesId", empLeaveId);
		query.executeUpdate();
		return true;
	}
	
	@Override
	public EmployeeLeaves loadLeaveById(String leaveId, Session session){
		return (EmployeeLeaves)session.get(EmployeeLeaves.class, Long.parseLong(leaveId));
	}
	
	@Override
	public Long getLeaveBalance(String leaveType, String calYear, String calMonth,
		String employeeId, Session session) throws ParseException {
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, Integer.parseInt(calMonth));
        gc.set(Calendar.YEAR, Integer.parseInt(calYear));
        gc.set(Calendar.DAY_OF_MONTH, 0);
        Date dt = gc.getTime();
		String sqlQuery = "SELECT SUM(eld.hours) from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ "WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+new java.sql.Timestamp(DateUtils.getDate(calYear+startDate).getTime())+"' and "
				+ " '"+new java.sql.Timestamp(dt.getTime())+"' and hd.pk is NULL";
		Query query = session.createQuery(sqlQuery);
		//if(leaveType.equals(LeaveType.PTO.getLeaveType()))
		leaveType = LeaveType.ONLY_PTO.getLeaveType();
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		Object obj = query.uniqueResult();
		return obj != null ?(Long)obj:0;
	}
	
	@Override
	public Long getLeavesTakenByDate(String leaveType, Date startDt, Date endDt, Long employeeId, Session session)
			throws ParseException {
		String sqlQuery = "SELECT SUM(eld.hours) from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el ";
		if(!leaveType.equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
				&& !leaveType.equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())){
			sqlQuery += " left join eld.holidays as hd WITH hd.active=:active ";
		}
		sqlQuery += " WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+new java.sql.Timestamp(startDt.getTime())+"' and "
				+ " '"+new java.sql.Timestamp(endDt.getTime())+"' ";
		if(!leaveType.equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
				&& !leaveType.equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())){
			sqlQuery += " and hd.pk is NULL ";
		}
		
		Query query = session.createQuery(sqlQuery);
		if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ONLY_PTO.getLeaveType();
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("employeeId", employeeId);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		Object obj = query.uniqueResult();
		return obj != null ?(Long)obj:0;
	}
	
	@Override
	public EmployeeLeaveBalance getEmpLeaveBalance(String calYear, String employeeId, Session session) throws ParseException {
		String sqlQuery = "from EmployeeLeaveBalance as elb WHERE elb.active=:active and elb.year=:year and elb.employeeId=:employeeId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("year", Integer.parseInt(calYear));
		query.setParameter("employeeId", Long.parseLong(employeeId));
		return (EmployeeLeaveBalance) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getEmpLeaveBalanceList(int calYear, String month, Set<Long> employeeIdList, Session session) throws ParseException {
		String sqlQuery = "select e.employee_id as employeeId, e.firstname as firstName, e.lastname as lastName, elb.op_bal as opening_bal, elb."+month+" as leave_bal from employee e "
				+ " left join employee_leave_balance elb on e.pk=elb.employee_pk "
				+ " WHERE elb.active='"+PerfHrConstants.ACTIVE+"' and e.active='"+PerfHrConstants.ACTIVE+"' "
				+ " and elb.year = '"+calYear+"' and e.pk in ("+employeeIdList.toString().replace("[", "").replace("]", "")+")";
		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.addScalar("employeeId", new StringType());
		query.addScalar("firstName", new StringType());
		query.addScalar("lastName", new StringType());
		query.addScalar("opening_bal", new LongType());
		query.addScalar("leave_bal", new LongType());
		return query.list();
	}
	
	@Override
	public EmployeeLeaveBalance saveEmpLeaveBalance(EmployeeLeaveBalance employeeLeaveBalance, Session session) throws ParseException {
		session.save(employeeLeaveBalance);
		return employeeLeaveBalance;
	}
	
	@Override
	public EmployeeLeaveBalance updateEmpLeaveBalance(EmployeeLeaveBalance employeeLeaveBalance, Session session) throws ParseException {
		session.merge(employeeLeaveBalance);
		return employeeLeaveBalance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getLeaveBalanceByMonth(String leaveType, String calYear,
		String employeeId, Session session) throws ParseException {
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.YEAR, Integer.parseInt(calYear));
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 12);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        double arr[] = new double[12];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery = "SELECT eld.leaveDate, eld.hours from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ "WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+format.format(monthStart)+"' and "
				+ " '"+format.format(monthEnd)+"' and hd.pk is NULL";
		Query query = session.createQuery(sqlQuery);
		if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ONLY_PTO.getLeaveType();
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		List<Object[]> resultList = query.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Object[] val: resultList){
			Date dt = sdf.parse(String.valueOf(val[0]));
			gc.setTimeInMillis(dt.getTime());
			double hoursVal = 0;
			if(Integer.parseInt(String.valueOf(val[1])) != 0){
				hoursVal = (double) (Long.valueOf(String.valueOf(val[1])) < 8 ?0.5:1);
			}
			arr[gc.get(Calendar.MONTH)] = arr[gc.get(Calendar.MONTH)]+hoursVal;
		}
		return arr;
	}
	
	@Override
	public Long getCarryLeaves(String employeeId, String year, Session session) {
		String sql = "select sum(ld.hours) from EmployeeLeaveDetails as ld left join ld.employeeLeaves as el "
				+ " WHERE el.active=:active AND ld.active=:active and el.requestType in (:requestTypes) AND el.employeeId =:employeeId and ld.leaveDate "
				+ "between '"+year+"-01-01' and '"+Integer.parseInt(year)+"-12-31'";
		Query query = session.createQuery(sql);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameterList("requestTypes", getLeaveTypeList(LeaveType.ONLY_PTO.getLeaveType()));
		return (Long) query.uniqueResult();
	}

	@Override
	public Long getHoursByLeaveId(EmployeeLeaves employeeLeaves, Session session) {
		String sqlQuery = "SELECT SUM(eld.hours) from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el ";
		if(!employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
				&& !employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())){
			sqlQuery += " left join eld.holidays as hd WITH hd.active=:active ";
		}
		sqlQuery += "WHERE el.pk=eld.employeeLeavesId AND el.active=:active and eld.active=:active AND el.pk=:pk";
		if(!employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
				&& !employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())){
			sqlQuery += "  and hd.pk is NULL ";
		}
		Query query = session.createQuery(sqlQuery);
		query.setParameter("pk", employeeLeaves.getPk());
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return (Long) query.uniqueResult();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> loadEmployeesByLeaveDate(Date leaveDate, Session session) {
		String sql = "select el.employeeId from EmployeeLeaves el, EmployeeLeaveDetails eld WHERE el.active=:active AND "
				+ "eld.active=:active and el.pk=eld.employeeLeavesId and eld.leaveDate=:leaveDate";
		Query query = session.createQuery(sql);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("leaveDate", leaveDate);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Date, Integer> getLeavesBtwDate(List<String> leaveType, Date startDt, Date endDt, Long employeeId,
			Session session) {
		String sqlQuery = "SELECT eld.leaveDate, eld.hours from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ "WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+new java.sql.Timestamp(startDt.getTime())+"' and "
				+ " '"+new java.sql.Timestamp(endDt.getTime())+"' and hd.pk is NULL";
		Query query = session.createQuery(sqlQuery);
		/*if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ONLY_PTO.getLeaveType();*/
		query.setParameterList("requestTypes", leaveType);
		query.setParameter("employeeId", employeeId);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		Map<Date, Integer> resultMap = new HashMap<>();
		List<Object[]> resultList = query.list();
		for(Object[] obj: resultList){
			resultMap.put((Date) obj[0], (int)obj[1]);
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getLeaveBalanceByType(String leaveType, String calYear, String employeeId, Session session)
			throws ParseException {
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.YEAR, Integer.parseInt(calYear));
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 12);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        //double arr[] = new double[12];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery = "SELECT eld.leaveDate, eld.hours, el.requestType from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el left join eld.holidays as hd WITH hd.active=:active "
				+ "WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+format.format(monthStart)+"' and "
				+ " '"+format.format(monthEnd)+"' and hd.pk is NULL";
		Query query = session.createQuery(sqlQuery);
		if(leaveType.equals(LeaveType.PTO.getLeaveType()))
			leaveType = LeaveType.ONLY_PTO.getLeaveType();
		query.setParameterList("requestTypes", getLeaveTypeList(leaveType));
		query.setParameter("employeeId", Long.parseLong(employeeId));
		query.setParameter("active", PerfHrConstants.ACTIVE);
		List<Object[]> resultList = query.list();
		Map<String, Double> leavesByType = new HashMap<>();
		for(Object[] val: resultList){
			String type = String.valueOf(val[2]);
			double hoursVal = 0;
			if(Integer.parseInt(String.valueOf(val[1])) != 0){
				hoursVal = (double) (Long.valueOf(String.valueOf(val[1])) < 8 ?0.5:1);
			}
			if(leavesByType.get(type) != null){
				leavesByType.put(type, leavesByType.get(type)+hoursVal);
			} else {
				leavesByType.put(type, hoursVal);
			}
		}
		return leavesByType;
	}

	@Override
	public Object getMinMaxLeaveByDate(String leaveType, Date startDt, Date endDt, Long employeeId, Session session)
			throws ParseException {
		String sqlQuery = "SELECT min(eld.leaveDate), max(eld.leaveDate) from EmployeeLeaveDetails as eld left join eld.employeeLeaves as el "
				+ "WHERE el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) AND el.active=:active "
				+ "and eld.active=:active AND el.employeeId=:employeeId AND eld.leaveDate "
				+ "between '"+new java.sql.Timestamp(startDt.getTime())+"' and "
				+ " '"+new java.sql.Timestamp(endDt.getTime())+"'";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("requestTypes", leaveType);
		query.setParameter("employeeId", employeeId);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return query.uniqueResult();
	}

	@Override
	public boolean isLeaveApplied(Date date, Session session) {
		String sqlQuery = "select eld.leaveDate from EmployeeLeaves el, EmployeeLeaveDetails eld WHERE el.active=:active AND "
				+ "eld.active=:active and el.pk=eld.employeeLeavesId and el.requestType in (:requestTypes) and eld.leaveDate='"+new java.sql.Timestamp(date.getTime())+"'";
		Query query = session.createQuery(sqlQuery);
		List<String> leaveTypeList = new ArrayList<>();
		leaveTypeList.add(LeaveType.PTO.getLeaveType());
		leaveTypeList.add(LeaveType.UNPLANNED_PTO.getLeaveType());
		leaveTypeList.add(LeaveType.LOSS_OF_PAY.getLeaveType());
		leaveTypeList.add(LeaveType.WFH.getLeaveType());
		leaveTypeList.add(LeaveType.MATERNITY_WFH.getLeaveType());
		leaveTypeList.add(LeaveType.PATERNITY_WFH.getLeaveType());
		query.setParameterList("requestTypes", leaveTypeList);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		if(query.list().size() != 0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getLeaveDetailsById(Long employeeLeaveId, Session session) {
		String sqlQuery = "select eld.leaveDate, eld.hours from EmployeeLeaveDetails eld,EmployeeLeaves el WHERE el.active=:active AND "
				+ "eld.active=:active and el.pk=eld.employeeLeavesId and eld.employeeLeavesId=:employeeLeaveId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("employeeLeaveId", employeeLeaveId);
		return query.list();
	}

	@Override
	public void saveOfficeEntry(EmployeeOfficeEntry employeeOfficeEntry, Session session) {
		session.save(employeeOfficeEntry);
	}

	public List<Object> loadLeaveByEmpIdAndDate(Long empId, String startsAt, String endDate, Session session) {
		String sqlQuery = "select eld.leaveDate from EmployeeLeaveDetails eld,EmployeeLeaves el WHERE el.active=:active AND "
				+ "eld.active=:active and el.pk=eld.employeeLeavesId and el.employeeId=:empId and eld.leaveDate ='"+startsAt+"'";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("empId", empId);
//		query.setParameter("startsAt", startsAt);
		return  query.list();
	}

}