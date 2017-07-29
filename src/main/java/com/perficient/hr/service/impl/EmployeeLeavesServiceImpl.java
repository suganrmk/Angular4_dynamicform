package com.perficient.hr.service.impl;

import java.io.FileInputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.dao.HolidaysDAO;
import com.perficient.hr.dao.NotificationDAO;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaveBalance;
import com.perficient.hr.model.EmployeeLeaveDetails;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.EmployeeNamesView;
import com.perficient.hr.model.EmployeeOfficeEntry;
import com.perficient.hr.model.Holidays;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.model.type.NotificationStatusType;
import com.perficient.hr.model.type.NotificationType;
import com.perficient.hr.service.EmployeeLeavesService;
import com.perficient.hr.service.NotificationService;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.util.CompatibilityHints;

@Repository("employeeLeavesService")
public class EmployeeLeavesServiceImpl implements EmployeeLeavesService {

	protected Logger logger = LoggerFactory.getLogger(EmployeeLeavesServiceImpl.class);

	public int[] LEAVE_EARNED_MONTH = { 12, 24, 40, 52, 64, 80, 92, 104, 120, 132, 144, 160 };

	@Autowired
	EmployeeDAO employeeDAO;

	@Autowired
	EmployeeLeavesDAO employeeLeavesDAO;

	@Autowired
	NotificationDAO notificationDAO;

	@Autowired
	NotificationService notificationService;

	@Autowired
	HolidaysDAO holidayDAO;

	@Autowired
	public PerfProperties perfProperties;

	@Autowired
	public EmailTrackDAO emailTrackDAO;

	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	private String startDate = "-01-01";
	
	private static final int LEAVE_LIMIT = -40;

	@Override
	public Object parseDocument(String fileName, String startYear, String totalLeaves) {
		LoggerUtil.infoLog(logger, "Document Parsing. File :" + fileName);
		Session session = null;
		Transaction tx = null;
		try (FileInputStream fis = new FileInputStream(fileName);) {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			// Workbook workbook = new XSSFWorkbook(fis);
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
			int count = 0, empNotFound = 0;
			for (int i = 0; i < numberOfSheets; i++) {
				// Sheet sheet = workbook.getSheetAt(i);
				org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				Calendar cal = Calendar.getInstance();
				int hoursCol=0, empIdCol=0, leaveTypeCol=0, contractorLeaveTypeCol=0, commentCol=0, titleCol=0, monthCol=0, dateCol = 0;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if(row.getRowNum() == 0){
						Iterator<Cell> cellIterator = row.cellIterator();
						int col = 0;
						while(cellIterator.hasNext()){
							try{
								logger.info("col "+col+" val  "+row.getCell(col).toString());
								if(row.getCell(col).toString().equalsIgnoreCase("Time Entry Type")){
									leaveTypeCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Hours")){
									hoursCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Resource Number")){
									empIdCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Type")){
									contractorLeaveTypeCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Detail Comments")){
									commentCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Resource Name")){
									titleCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Month")){
									monthCol = col;
								} else if(row.getCell(col).toString().equalsIgnoreCase("Cell Date")){
									dateCol = col;
								}
								col++;
							} catch (NullPointerException ne) {
								break;
							}
						}
					}
					
					int hours = 0;
					try {
						hours = Math.round(Float.parseFloat(row.getCell(hoursCol).toString()));
					} catch (NumberFormatException e) {
						logger.info("Invalid Hours logged!");
					}
					String empId = row.getCell(empIdCol).toString();
					Employee employee = employeeDAO.loadByEmployeeId(empId, session);
					if (employee != null && hours != 0) {
						count++;
						EmployeeLeaves employeeLeaves = new EmployeeLeaves();
						String leaveType = row.getCell(leaveTypeCol).toString().toUpperCase().replace(" ", "_");
						if (leaveType.equalsIgnoreCase("Personal_Leave")) {
							employeeLeaves.setRequestType(LeaveType.LOSS_OF_PAY.getLeaveType());
						} else if (leaveType.equalsIgnoreCase("Maternity_Paid")) {
							employeeLeaves.setRequestType(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType());
						} else if (leaveType.equalsIgnoreCase("Maternity_Unpaid")) {
							employeeLeaves.setRequestType(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType());
						} else if(leaveType.length() == 0 && 
	                			(row.getCell(contractorLeaveTypeCol).toString().equals("Labor-PTO Unplanned") 
	                					|| row.getCell(contractorLeaveTypeCol).toString().equals("Labor-PTO"))){
	                		employeeLeaves.setRequestType(LeaveType.LOSS_OF_PAY.getLeaveType());
	                	} else
							employeeLeaves.setRequestType(leaveType);
						
						if(!(employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
	                			||employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType())
	                			||employeeLeaves.getRequestType().equals(LeaveType.LOSS_OF_PAY.getLeaveType())
	                			||employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
	                			||employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType()))){
	                		continue;
	                	}
						employeeLeaves.setEmployeeId(employee.getPk());
						employeeLeaves.setAppliedById(employee.getPk());
						String comments = row.getCell(commentCol).toString();
						if (comments.length() >= 100)
							comments = comments.substring(0, 99);
						employeeLeaves.setComments(comments);
						String title = employeeLeaves.getRequestType();
						if(employeeLeaves.getRequestType().equalsIgnoreCase(LeaveType.LOSS_OF_PAY.getLeaveType()))
							title = "PERSONAL LEAVE(LOP)";
						employeeLeaves.setTitle(row.getCell(titleCol).toString() + " - " + title);
						Date dt = sdf.parse("01-" + row.getCell(monthCol).toString());
						cal.setTime(dt);
						logger.info(row.getCell(monthCol).toString() + " Date " + row.getCell(dateCol).toString() + " month "
								+ (cal.get(Calendar.MONTH) + 1));
						Date leaveDate = DateUtils.getDate(row.getCell(dateCol).toString(), (cal.get(Calendar.MONTH) + 1));
						cal.setTime(leaveDate);
						cal.set(Calendar.HOUR, 9);
						cal.set(Calendar.MINUTE, 30);
						cal.set(Calendar.AM_PM, Calendar.AM);
						employeeLeaves.setStartsAt(cal.getTime());
						
						employeeLeaves.setHours((hours <= 4) ? 4 : 8);
						employeeLeaves.setDtFromHalf(PerfHrConstants.FIRST_HALF);
						if (employeeLeaves.getHours() == 4) {
							cal.set(Calendar.HOUR, 1);
							employeeLeaves.setDtEndHalf(PerfHrConstants.FIRST_HALF);
						} else {
							cal.set(Calendar.HOUR, 5);
							employeeLeaves.setDtEndHalf(PerfHrConstants.SECOND_HALF);
						}
						cal.set(Calendar.AM_PM, Calendar.PM);
						employeeLeaves.setEndsAt(cal.getTime());
						employeeLeaves.setDtCreated(new Date());
						employeeLeaves.setDtModified(new Date());
						employeeLeaves.setCreatedBy(employee.getPk());
						employeeLeaves.setModifiedBy(employee.getPk());
						applyLeave(employeeLeaves, employee.getPk().toString(), true);
					} else {
						empNotFound++;
						logger.info("empNotFound empId "+empId);
					}
				}
			}
			logger.info("count "+count+" empNotFound "+empNotFound);
			fis.close();
			tx.commit();
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to import PTO document.", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to import PTO document ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}

	@Override
	public Object importOfficeEntry(String fileName, String employeeId) {
		LoggerUtil.infoLog(logger, "Document Parsing. File :" + fileName);
		Session session = null;
		Transaction tx = null;
		try (FileInputStream fis = new FileInputStream(fileName);) {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Employee loggedEmployee = employeeDAO.loadById(employeeId, session);
			
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if(row.getRowNum() >= 5){
						String empId = row.getCell(1).toString();
						if(empId.startsWith("000"))
							empId = empId.substring(3, empId.length());
						Employee employee = employeeDAO.loadByEmployeeId(empId, session);
						if(employee != null){
							EmployeeOfficeEntry empOfficeEntry = new EmployeeOfficeEntry();
							empOfficeEntry.setEmployeeId(employee.getPk());
							Date dt = sdf.parse(row.getCell(2).toString());
							empOfficeEntry.setInDate(dt);
							empOfficeEntry.setLocation(row.getCell(4).toString());
							empOfficeEntry.setDtCreated(new Date());
							empOfficeEntry.setDtModified(new Date());
							empOfficeEntry.setCreatedBy(loggedEmployee.getPk());
							empOfficeEntry.setModifiedBy(loggedEmployee.getPk());
							employeeLeavesDAO.saveOfficeEntry(empOfficeEntry, session);	
						}
					}
				}
			}
			fis.close();
			tx.commit();
		} catch (ConstraintViolationException e) {
			LoggerUtil.errorLog(logger, "Unable to import Office Entry document.", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Duplicate Entry found", e);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to import Office Entry document.", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to import Office Entry document ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}
	
	@Override
	public Object parseOpeningBalDocument(String fileName) {
		LoggerUtil.infoLog(logger, "Document Parsing. File :" + fileName);
		Session session = null;
		Transaction tx = null;
		try (FileInputStream fis = new FileInputStream(fileName);) {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				int count = 0;
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (count >= 1) {
						String employeeId = row.getCell(3).toString();
						String joinDate = row.getCell(2).toString();
						cal.setTime(sdf.parse(joinDate));
						if (employeeId.indexOf(".") != -1) {
							employeeId = employeeId.substring(0, employeeId.indexOf("."));
						}
						if (cal.get(Calendar.YEAR) < 2017) {
							saveLeaveBalance(employeeId, Double.parseDouble(row.getCell(1).toString()), 2017, null,
									session);
						} else {
							Employee employee = employeeDAO.loadByEmployeeId(employeeId, session);
							if (employee != null) {
								if(employee.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR)
										|| employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING)
										|| employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)){
									saveLeaveBalance(employeeId, 0, 2017, null,	session);
								} else {
									saveEmployeeLeaveBalance(2017, employee.getPk().toString(), session);	
								}
							} else {
								LoggerUtil.infoLog(logger,
										"------Employee Not found on leave balance import.  EmployeeID." + employeeId);
							}
						}
					}
					count++;
				}
			}
			fis.close();
			tx.commit();
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to import PTO document.", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to import PTO document ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object importWfhCalInfoFromIcs(String fileName) {
		Session session = null;
		try (FileInputStream fis = new FileInputStream(fileName);) {
			session = sessionFactory.openSession();
			net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
			// Relaxing the parsing of CalenderBuilder to ignore special characters in Attendee's list
			CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);
			CalendarBuilder builder = new CalendarBuilder();
			calendar = builder.build(fis);
			int count = 1;
			HashMap<Integer, HashMap<String, String>> calendarMap = new HashMap<Integer, HashMap<String, String>>();
			for (Iterator componentIterator = calendar.getComponents().iterator(); componentIterator.hasNext();) {
				HashMap<String, String> calendarInfo = new HashMap<String, String>();
				Component component = (Component) componentIterator.next();
				for (Iterator attributeIterator = component.getProperties().iterator(); attributeIterator.hasNext();) {
					String startDate = null, endDate = null, summary = null, description = null, organizer = null,
							sender = null, startTime = "09:00 AM", endTime = "05:30 PM";
					Property property = (Property) attributeIterator.next();

					/**
					 * Map values:
					 * 
					 * actualStartDate - Exact start date from the file. startDate -
					 * Start date in dd-MM-yyyy startTime - Defaulted to 09:00 AM
					 * 
					 * actualEndDate - Exact end date from the file. endDate - End
					 * date in dd-MM-yyyy endTime - Defaulted to 05:30 PM
					 * 
					 * summary - Summary of the WFH event description - Description
					 * of the WFH event organizer / sender- Creator of the WFH event
					 * (From WFM / Outlook)
					 * 
					 */

					if ("DTSTART".equals(property.getName())) {
						startDate = property.getValue();
						calendarInfo.put("actualStartDate", startDate.substring(0, 8));
						calendarInfo.put("startDate", modifyDateLayout(startDate));
						calendarInfo.put("startTime", modifyStartTime(startDate, startTime));

						endDate = calendarInfo.get("endDate");
						calendarInfo.put("endDate",
								getEndDate(calendarInfo.get("actualStartDate"), calendarInfo.get("actualEndDate")));
					}
					if ("SUMMARY".equals(property.getName())) {
						summary = property.getValue();
						calendarInfo.put("summary", summary);
					}
					if ("DESCRIPTION".equals(property.getName())) {
						description = property.getValue();
						description = description.replace("\n", "");
						calendarInfo.put("description", description);
					}
					if ("ORGANIZER".equals(property.getName())) {
						organizer = property.getValue();
						calendarInfo.put("organizer", organizer.substring(7));
					}
					if ("X-MS-OLK-SENDER".equals(property.getName())) {
						sender = property.getValue();
						calendarInfo.put("organizer", sender.substring(7));
					}
					if ("DTEND".equals(property.getName())) {
						endDate = property.getValue();
						calendarInfo.put("actualEndDate", endDate.substring(0, 8));
						calendarInfo.put("endDate", modifyDateLayout(endDate));
						calendarInfo.put("endTime", modifyStartTime(endDate, endTime));
					}
				}
				calendarMap.put(count, calendarInfo);
				count++;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			for(HashMap<String, String> map : calendarMap.values()){
				if(map.keySet().size() == 0){
					continue;
				}
				
				Date stDt = sdf.parse(map.get("startDate"));
				Date endDt = sdf.parse(map.get("endDate"));
				final LocalDate start = new LocalDate(stDt.getTime());
			    final LocalDate end = new LocalDate(endDt.getTime());
			    LocalDate dateFromCal = start;
			    while (dateFromCal.isBefore(end) || dateFromCal.isEqual(end)) {
			    	if(dateFromCal.getDayOfWeek() != DateTimeConstants.SATURDAY 
			    			&& dateFromCal.getDayOfWeek() != DateTimeConstants.SUNDAY){
			    		try{
				    		Date wfhDate = dateFromCal.toDate();
							Employee employee = employeeDAO.loadByEmail(map.get("organizer"), session);
							EmployeeLeaves employeeLeaves = new EmployeeLeaves();
							employeeLeaves.setRequestType(LeaveType.WFH.getLeaveType());
							employeeLeaves.setEmployeeId(employee.getPk());
							employeeLeaves.setAppliedById(employee.getPk());
							String comments = map.get("description");
							if(comments.length() > 500)
								comments = comments.substring(0, 496)+"..";
							employeeLeaves.setComments(comments);
							employeeLeaves.setTitle(employee.getLastName()+", "+employee.getFirstName() + " - " + employeeLeaves.getRequestType());
							
							Calendar cal = Calendar.getInstance();
							cal.setTime(wfhDate);
							cal.set(Calendar.HOUR, 9);
							cal.set(Calendar.MINUTE, 30);
							cal.set(Calendar.AM_PM, Calendar.AM);
							employeeLeaves.setStartsAt(cal.getTime());
							employeeLeaves.setDtFromHalf(PerfHrConstants.FIRST_HALF);
							employeeLeaves.setDtEndHalf(PerfHrConstants.SECOND_HALF);
							cal.set(Calendar.HOUR, 5);
							cal.set(Calendar.MINUTE, 30);
							cal.set(Calendar.AM_PM, Calendar.PM);
							employeeLeaves.setEndsAt(cal.getTime());
							employeeLeaves.setDtCreated(new Date());
							employeeLeaves.setDtModified(new Date());
							employeeLeaves.setCreatedBy(employee.getPk());
							employeeLeaves.setModifiedBy(employee.getPk());
							applyLeave(employeeLeaves, employee.getPk().toString(), true);
						} catch (NullPointerException e) {
							LoggerUtil.errorLog(logger, "Unable to Process WFH data. NullPointerException: "+map.get("organizer"), e);
						}
			    	}
			    	dateFromCal = dateFromCal.plusDays(1);
			    }
			}
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Process WFH data. ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Process WFH data.", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return null;
	}

	private String getEndDate(String startDate, String endDate) throws ParseException {
		Integer actualStartDate = Integer.parseInt(startDate);
		Integer actualEndDate = Integer.parseInt(endDate);
		Integer updatedEndDate = actualEndDate - actualStartDate;
		Integer modifiedEndDate = actualEndDate;
		if (updatedEndDate != 0) {
			String updatedDate = modifyDateLayout(modifiedEndDate.toString());
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date date = df.parse(updatedDate);
			java.util.Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(date);
			cal.add(GregorianCalendar.DATE, -1);
			return df.format(cal.getTime()).toString();
		} else {
			return modifyDateLayout(modifiedEndDate.toString());
		}
	}

	@SuppressWarnings("deprecation")
	private String modifyStartTime(String startDate, String startOrEndTime) throws ParseException {
		SimpleDateFormat inputTimeFormat = new SimpleDateFormat("KK:mm aa");
		Date time = inputTimeFormat.parse(startOrEndTime);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.HOUR_OF_DAY, time.getHours());
		cal.set(java.util.Calendar.MINUTE, time.getMinutes());
		return inputTimeFormat.format(cal.getTime());
	}

	private String modifyDateLayout(String inputDate) {
		try {
			SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date fromDate = inputDateFormat.parse(inputDate);
			SimpleDateFormat outputDateForm = new SimpleDateFormat("dd-MM-yyyy");
			return outputDateForm.format(fromDate);
		} catch (Exception e) {
			return inputDate;
		}
	}

	@Override
	public Object loadLeaves(String leaveType, String calYear, String employeeId, String startDate, String endDate) {
		LoggerUtil.infoLog(logger, "Load All Leave Details");
		List<Object> list = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			list = employeeLeavesDAO.loadLeaves(leaveType, employeeId, startDate, endDate, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load leaves. ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load all leaves. ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadLeavesByYear(String leaveType, String calYear) {
		return loadLeaves(leaveType, calYear, null, calYear + startDate, calYear + "-12-31");
	}

	@Override
	public Object loadLeavesByMonth(String leaveType, String calYear, String calMonth) {
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.MONTH, Integer.parseInt(calMonth));
		gc.set(Calendar.YEAR, Integer.parseInt(calYear));
		gc.set(Calendar.DAY_OF_MONTH, 0);
		Date monthEnd = gc.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return loadLeaves(leaveType, calYear, null, calYear + "-" + calMonth + "-01", sdf.format(monthEnd));
	}

	@Override
	public Object loadMyLeaves(String leaveType, String calYear, String employeeId) {
		return loadLeaves(leaveType, calYear, employeeId, calYear + startDate, calYear + "-12-31");
	}

	@Override
	public Object loadLeaveReport(EmployeeLeaves employeeLeaves) {
		LoggerUtil.infoLog(logger, "Load Employee Leave Report for employee: " + employeeLeaves.getEmployeeId());
		Session session = null;
		try {
			session = sessionFactory.openSession();
			employeeLeaves.setEmployeeId(employeeDAO.loadByEmployeeId(employeeLeaves.getEmpId(), session).getPk());
			return employeeLeavesDAO.loadLeaveReport(employeeLeaves, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load leave report for employee: '" + employeeLeaves.getEmployeeId(),
					e);
			return ExceptionHandlingUtil.returnErrorObject(
					"Unable to load leave report for employee: '" + employeeLeaves.getEmployeeId(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadAllLeaveReport(EmployeeLeaves employeeLeaves) {
		LoggerUtil.infoLog(logger, "Load Employee Leave Report for employee: " + employeeLeaves.getEmployeeId());
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Object[]> leaveReportList = employeeLeavesDAO.loadLeaveReportByEmpId(employeeLeaves, session);
			//If size is not equal then there is no leave applied by the employee for this year
			if(employeeLeaves.getEmployeeReportList().size() != leaveReportList.size()){
				Set<Long> empReportIds = new HashSet<Long>(employeeLeaves.getEmployeeReportList());
				Set<Long> leaveEmpIds = new HashSet<Long>();
				for(Object[] obj : leaveReportList){
					if (employeeLeaves.getRequestType().equals(LeaveType.ALL_PTO.getLeaveType())) {
						leaveEmpIds.add((Long) obj[14]);
					} else {
						leaveEmpIds.add((Long) obj[7]);
					}
				}
				empReportIds.removeAll(leaveEmpIds);
				if(employeeLeaves.getRequestType().equals(LeaveType.ALL_WFH.getLeaveType())){
					List<Employee> empList = employeeDAO.loadEmployeesByPk(empReportIds, session);
					for(Employee emp : empList){
						Object[] obj = new Object[8];
						obj[0] = emp.getEmployeeId();
						obj[1] =  emp.getFirstName();
						obj[2] = emp.getLastName();
						obj[3] = 0;
						obj[4] = 0;
						obj[5] = 0;
						obj[6] = 0;
						obj[7] = 0;
						leaveReportList.add(obj);
					}
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(employeeLeaves.getEndsAt());
					String month = new SimpleDateFormat("MMM").format(cal.getTime()).toString().toLowerCase();
					if(month.equalsIgnoreCase("DEC")){
						month = "decem";
					}
					//If no leaves applied by Leave balance record is available then the record should be loaded by this method
					List<Object[]> empLeaveList = employeeLeavesDAO.getEmpLeaveBalanceList(cal.get(Calendar.YEAR), month, empReportIds, session);
					for(Object[] leaveData: empLeaveList){
						Object[] obj = new Object[14];
						obj[0] = leaveData[0];
						obj[1] = leaveData[1];
						obj[2] = leaveData[2];
						if(employeeLeaves.getRequestType().equals(LeaveType.ALL_PTO.getLeaveType())){
							obj[9] = leaveData[3];
							obj[10] = leaveData[4];
						}
						leaveReportList.add(obj);
					}
					//No Employee Leave balance record for the employees in this year
					if(empLeaveList.size() != empReportIds.size()){
						
					}
				}
			}
			return leaveReportList;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load leave report for employee: '" + employeeLeaves.getEmployeeId(), e);
			return ExceptionHandlingUtil.returnErrorObject(
					"Unable to load leave report for employee: '" + employeeLeaves.getEmployeeId(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getEmpLeaveBalance(String calYear, String employeeId) {
		LoggerUtil.infoLog(logger, "Load Leave Balance for employee: " + employeeId);
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			EmployeeLeaveBalance employeeLeaveBalance = employeeLeavesDAO.getEmpLeaveBalance(calYear, employeeId,
					session);
			logger.info("employeeLeaveBalance " + employeeLeaveBalance);
			if (employeeLeaveBalance == null) {
				tx = session.beginTransaction();
				employeeLeaveBalance = (EmployeeLeaveBalance) saveEmployeeLeaveBalance(Integer.parseInt(calYear),
						employeeId, session);
				tx.commit();
			}
			return employeeLeaveBalance;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to get leave balance for employee: " + employeeId, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get leave balance for employee: '" + employeeId,
					e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	private EmployeeLeaveBalance setEmployeeLeaveBalanceByMonth(EmployeeLeaveBalance employeeLeaveBalance,
			int[] LEAVE_EARNED) {
		employeeLeaveBalance.setJan((int) (LEAVE_EARNED[0]));
		employeeLeaveBalance.setFeb((int) (LEAVE_EARNED[1]));
		employeeLeaveBalance.setMar((int) (LEAVE_EARNED[2]));
		employeeLeaveBalance.setApr((int) (LEAVE_EARNED[3]));
		employeeLeaveBalance.setMay((int) (LEAVE_EARNED[4]));
		employeeLeaveBalance.setJun((int) (LEAVE_EARNED[5]));
		employeeLeaveBalance.setJul((int) (LEAVE_EARNED[6]));
		employeeLeaveBalance.setAug((int) (LEAVE_EARNED[7]));
		employeeLeaveBalance.setSep((int) (LEAVE_EARNED[8]));
		employeeLeaveBalance.setOct((int) (LEAVE_EARNED[9]));
		employeeLeaveBalance.setNov((int) (LEAVE_EARNED[10]));
		employeeLeaveBalance.setDec((int) (LEAVE_EARNED[11]));
		return employeeLeaveBalance;
	}

	private int[] setLeaveEarned(EmployeeLeaveBalance employeeLeaveBalance) {
		int[] LEAVE_EARNED = new int[12];
		LEAVE_EARNED[0] = employeeLeaveBalance.getJan();
		LEAVE_EARNED[1] = employeeLeaveBalance.getFeb();
		LEAVE_EARNED[2] = employeeLeaveBalance.getMar();
		LEAVE_EARNED[3] = employeeLeaveBalance.getApr();
		LEAVE_EARNED[4] = employeeLeaveBalance.getMay();
		LEAVE_EARNED[5] = employeeLeaveBalance.getJun();
		LEAVE_EARNED[6] = employeeLeaveBalance.getJul();
		LEAVE_EARNED[7] = employeeLeaveBalance.getAug();
		LEAVE_EARNED[8] = employeeLeaveBalance.getSep();
		LEAVE_EARNED[9] = employeeLeaveBalance.getOct();
		LEAVE_EARNED[10] = employeeLeaveBalance.getNov();
		LEAVE_EARNED[11] = employeeLeaveBalance.getDec();
		return LEAVE_EARNED;
	}

	private Object saveLeaveBalance(String employeeId, double openingBal, int calYear, int[] leavesEarned,
			Session session) throws ParseException {
		LoggerUtil.infoLog(logger, "Apply Employee Leave Service Started.");
		EmployeeLeaveBalance employeeLeaveBalance = new EmployeeLeaveBalance();
		Employee employee = employeeDAO.loadByEmployeeId(employeeId, session);
		if (employee != null) { 
			employeeLeaveBalance.setEmployeeId(employee.getPk());
			employeeLeaveBalance.setYear(calYear);
			// Leaves balance set on migration
			int[] LEAVE_EARNED = Arrays.copyOf(LEAVE_EARNED_MONTH, LEAVE_EARNED_MONTH.length);
			// leave balance set for employees who joined after migration
			if (leavesEarned != null) {
				LEAVE_EARNED = Arrays.copyOf(leavesEarned, leavesEarned.length);
			} else {
				// set leave balance based on opening balance
				for (int i = 0; i <= 11; i++) {
					LEAVE_EARNED[i] = (int) (LEAVE_EARNED_MONTH[i] + (openingBal * 8));
				}
			}
			if((employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING)
					||employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
					||employee.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR))) {
				for (int i = 0; i <= 11; i++) {
					LEAVE_EARNED[i] = 0;
				}
				employeeLeaveBalance.setOp_bal(0);
			} else {
				employeeLeaveBalance.setOp_bal((int)(openingBal*8) > 80 ? 80: (int)(openingBal*8));
			}
			employeeLeaveBalance = setEmployeeLeaveBalanceByMonth(employeeLeaveBalance, LEAVE_EARNED);
			employeeLeaveBalance.setDtCreated(new Date());
			employeeLeaveBalance.setDtModified(new Date());
			employeeLeaveBalance.setCreatedBy(employee.getPk());
			employeeLeaveBalance.setModifiedBy(employee.getPk());
			employeeLeavesDAO.saveEmpLeaveBalance(employeeLeaveBalance, session);
			
			int closingBal = LEAVE_EARNED[11];
			// save for next year - add 12 hours to december
			int prevOpeningBal = LEAVE_EARNED[11] + 12 >= 80 ? 80 : LEAVE_EARNED[11] + 12;
			LEAVE_EARNED = Arrays.copyOf(LEAVE_EARNED_MONTH, LEAVE_EARNED_MONTH.length);
			for (int i = 0; i <= 11; i++) {
				LEAVE_EARNED[i] = LEAVE_EARNED[i] + prevOpeningBal;
			}
			EmployeeLeaveBalance empBalance = new EmployeeLeaveBalance();
			if((employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING)
					||employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
					||employee.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR))) {
				for (int i = 0; i <= 11; i++) {
					LEAVE_EARNED[i] = 0;
				}
				empBalance.setOp_bal(0);
			} else {
				empBalance.setOp_bal(closingBal > 80? 80:closingBal);	
			}
			empBalance = setEmployeeLeaveBalanceByMonth(empBalance, LEAVE_EARNED);
			empBalance.setEmployeeId(employeeLeaveBalance.getEmployeeId());
			empBalance.setYear(calYear + 1);
			empBalance.setDtCreated(new Date());
			empBalance.setDtModified(new Date());
			empBalance.setCreatedBy(employee.getPk());
			empBalance.setModifiedBy(employee.getPk());
			employeeLeavesDAO.saveEmpLeaveBalance(empBalance, session);
		}
		return employeeLeaveBalance;
	}

	@Override
	public Object saveEmployeeLeaveBalance(int calYear, String id, Session session) throws ParseException {
		Employee employee = employeeDAO.loadById(id, session);
		// Don't create balance record for past year or in case of Sub Contractors
		if (Integer.parseInt(perfProperties.getStartYear()) > calYear || 
				employee.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR)
				|| employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
				|| employee.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING)){
			return new EmployeeLeaveBalance();
		}
		Calendar joinYear = Calendar.getInstance();
		joinYear.setTime(employee.getJoinDate());

		int mon = joinYear.get(Calendar.MONTH);
		int minusVal = 0;
		int[] LEAVE_EARNED = Arrays.copyOf(LEAVE_EARNED_MONTH, LEAVE_EARNED_MONTH.length);
		// Employee joined in present year
		if (joinYear.get(Calendar.YEAR) == calYear) {
			for (int i = 0; i <= 11; i++) {
				if(i < mon){
					LEAVE_EARNED[i] = 0;
					minusVal = LEAVE_EARNED_MONTH[i];
				} else if (i == mon) {
					// Minus half of the month val if the join date is greater than 15
					if(joinYear.get(Calendar.DATE) > 15){
						LEAVE_EARNED[i] = 4;
						minusVal = LEAVE_EARNED_MONTH[i]-4;
					} else {
						LEAVE_EARNED[i] = LEAVE_EARNED_MONTH[i]-minusVal;
					}
				} else {
					LEAVE_EARNED[i] = LEAVE_EARNED_MONTH[i] - minusVal;
				}
			}
		} else if (joinYear.get(Calendar.YEAR) == (calYear - 1)) {
			// Employee joined in previous year
			int openingBal = (LEAVE_EARNED_MONTH[11] + 12) - LEAVE_EARNED_MONTH[mon] - minusVal;
			openingBal = openingBal >= 80 ? 80 : openingBal;
			for (int i = 0; i <= 11; i++) {
				LEAVE_EARNED[i] = LEAVE_EARNED[i] + openingBal;
			}
		} else if(joinYear.get(Calendar.YEAR) < calYear){
			//Leave balance for upcoming years
			EmployeeLeaveBalance elb = employeeLeavesDAO.getEmpLeaveBalance(String.valueOf(calYear-1), employee.getPk().toString(), session);
			int openingBal = 80;
			if(elb != null)
				openingBal = elb.getDec() > 80 ? 80: elb.getDec();
			for (int i = 0; i <= 11; i++) {
				LEAVE_EARNED[i] = LEAVE_EARNED[i] + openingBal;
			}
		}
		return (EmployeeLeaveBalance) saveLeaveBalance(employee.getEmployeeId(), LEAVE_EARNED[0], calYear, LEAVE_EARNED,
				session);
	}

	private Object updateEmployeeLeaveBalance(int calYear, String employeeId, Map<Integer, Integer> leaveHoursMonth,
			Session session) throws ParseException, PrftException {
		Employee employeeOnLeave = employeeDAO.loadById(employeeId, session);
		if(employeeOnLeave.getDesignations().getDesignation().equalsIgnoreCase(PerfHrConstants.SUBCONTRACTOR)
				|| employeeOnLeave.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
				|| employeeOnLeave.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING)){
			return new EmployeeLeaveBalance();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		EmployeeLeaveBalance employeeLeaveBalance = employeeLeavesDAO.getEmpLeaveBalance(String.valueOf(calYear),
				employeeId, session);
		// save leave balance if the record is not found
		if (employeeLeaveBalance == null) {
			employeeLeaveBalance = (EmployeeLeaveBalance) saveEmployeeLeaveBalance(calYear, employeeId, session);
		}
		int[] LEAVE_EARNED = setLeaveEarned(employeeLeaveBalance);
		int leaveNotCredited = 0, lopLeaves = 0;
		for (int i = 0; i <= 11; i++) {
			if (leaveHoursMonth.containsKey(i + 1)) {
				leaveNotCredited += leaveHoursMonth.get(i + 1);
			}
			// deduct the leaves which is not credited for the following months
			LEAVE_EARNED[i] = LEAVE_EARNED[i] - leaveNotCredited;
			if (LEAVE_EARNED[i] < LEAVE_LIMIT)
				throw new PrftException("Leaves Exceeding the permissible limit in "+new DateFormatSymbols().getMonths()[i]+"-"+calYear+". Please Apply as Personal Leave(LOP).");
		}

		employeeLeaveBalance = setEmployeeLeaveBalanceByMonth(employeeLeaveBalance, LEAVE_EARNED);
		employeeLeaveBalance.setDtModified(new Date());
		employeeLeavesDAO.updateEmpLeaveBalance(employeeLeaveBalance, session);

		int leaveBalOnDec = LEAVE_EARNED[11];
		
		//update leave balance for next two years
		for (int i = 1; i <= 2; i++) {
			int year = calYear+i, bal=0, diff=0;
			long leavesTakenOnJan = 0;
			EmployeeLeaveBalance nxtYrEmpLeaveBalance = employeeLeavesDAO.getEmpLeaveBalance(String.valueOf(year),
					employeeId, session);
			if (nxtYrEmpLeaveBalance == null) {
				nxtYrEmpLeaveBalance = (EmployeeLeaveBalance) saveEmployeeLeaveBalance(year, employeeId, session);
			} else {
				// On else check for leaves applied in month of January(if
				// nxtYrEmpLeaveBalance is null then no leaves were applied)
				leavesTakenOnJan = employeeLeavesDAO.getLeavesTakenByDate(LeaveType.PTO.getLeaveType(),
						sdf.parse((year) + "-01-01"), sdf.parse((year) + "-01-31"),
						Long.parseLong(employeeId), session);
			}
			LEAVE_EARNED = setLeaveEarned(nxtYrEmpLeaveBalance);
			
			// Calculation for opening balance
			// Set employee and employeeleaves object to get the lop/maternity
			// leaves applied for month of december,
			// which shouldn't be credited for opening balance
			EmployeeLeaves empLeaves = new EmployeeLeaves();
			empLeaves.setRequestType(LeaveType.LOSS_OF_PAY.getLeaveType());
			empLeaves.setStartsAt(sdf.parse((year-1) + "-01-01"));
			lopLeaves = composeNonEarnedLeaveByMonth(getNonEarnedLeaveDetails(employeeOnLeave, empLeaves, session),
					(year-1), employeeOnLeave.getJoinDate(), session).get(12);
			
			//Minus the non credit leaves from December if lopLeaves are available
			bal = (leaveBalOnDec - lopLeaves);
			bal = bal > 80? 80: bal;
			//int openingBal = bal;
			//check the difference between the present year January leave balance and value after update
			bal = ((int)(bal - leavesTakenOnJan))+12;
			diff = bal - LEAVE_EARNED[0];
			//Set leave balance for all months accordingly based on the diff found
			for (int j = 0; j <= 11; j++) {
				LEAVE_EARNED[j] = LEAVE_EARNED[j] + diff;
				if (LEAVE_EARNED[0] > 92)
					throw new PrftException("Invalid Opening Balance. Please reach out to System Admin.");
				if (LEAVE_EARNED[j] < LEAVE_LIMIT)
					throw new PrftException("Leaves Exceeding the permissible limit in "+new DateFormatSymbols().getMonths()[j]+"-"+year+". Please Apply as Personal Leave(LOP).");
			}
			nxtYrEmpLeaveBalance = setEmployeeLeaveBalanceByMonth(nxtYrEmpLeaveBalance, LEAVE_EARNED);
			nxtYrEmpLeaveBalance.setOp_bal(leaveBalOnDec > 80?80:leaveBalOnDec);
			employeeLeavesDAO.updateEmpLeaveBalance(nxtYrEmpLeaveBalance, session);
			//set leave balance of december to counter it for next year
			leaveBalOnDec = LEAVE_EARNED[11];
		}
		return employeeLeaveBalance;
	}

	private boolean updateLeaveBalByNonEarnedLeaves(Employee employeeOnLeave, EmployeeLeaves employeeLeaves,
			int[] nonEarnedLeaves, Session session) throws ParseException, PrftException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(employeeLeaves.getStartsAt());
		Map<Integer, Integer> leavesBefore = composeNonEarnedLeaveByMonth(nonEarnedLeaves, cal.get(Calendar.YEAR), employeeOnLeave.getJoinDate(), session);
		Map<Integer, Integer> leavesAfter = composeNonEarnedLeaveByMonth(
				getNonEarnedLeaveDetails(employeeOnLeave, employeeLeaves, session), cal.get(Calendar.YEAR), employeeOnLeave.getJoinDate(), session);
		Map<Integer, Integer> empLeaveHoursMonth = new HashMap<>();
		for (int i = 1; i <= 12; i++) {
			empLeaveHoursMonth.put(i + 1, leavesAfter.get(i) - leavesBefore.get(i));
		}
		updateEmployeeLeaveBalance(cal.get(Calendar.YEAR), employeeOnLeave.getPk().toString(), empLeaveHoursMonth,
				session);
		return true;
	}

	private Map<Integer, Integer> composeNonEarnedLeaveByMonth(int[] leaves, int year, Date employeeJoinDate, Session session) {
		Map<Integer, Integer> leaveByMonth = new HashMap<Integer, Integer>();
		double totalLeaves = 0, totalDays = 0, leavesToDeduct = 0, leavesDeducted = 0, prevDeduct = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(employeeJoinDate);
		int monthInr = 0, joinYear = cal.get(Calendar.YEAR);
		List<Holidays> holidays = holidayDAO.getHolidaysByYear(String.valueOf(year), session);
		Map<Integer, Integer> holidayMap = new HashMap<>();
		for(Holidays holiday: holidays){
			Calendar holidayCal = Calendar.getInstance();
			holidayCal.setTime(holiday.getHolidayDate());
			int month = holidayCal.get(Calendar.MONTH);
			if(holidayMap.containsKey(month)){
				holidayMap.put(month, holidayMap.get(month)+month);
			} else
				holidayMap.put(month, 1);
		}
		for (int i = 0; i < 12; i++) {
			// Get the number of working days in that month
			int daysInMonth = 0;
			if(year == joinYear && cal.get(Calendar.MONTH) > i){
				continue;
			} if(year == joinYear && cal.get(Calendar.MONTH) == i){
				daysInMonth = getWorkingDays(year, i, cal.get(Calendar.DATE));
			} else {
				daysInMonth = getWorkingDays(year, i, 1);
			}
			//Minus holiday count from working days if there's any holiday in the month
			if(holidayMap.containsKey(i))
				daysInMonth = daysInMonth - holidayMap.get(i);
			
			monthInr++;
			totalDays = totalDays + daysInMonth;
			totalLeaves = (totalLeaves + leaves[i]);
			double daysConsidered = Math.round(((totalDays / (monthInr)) * 10)) / 10.0;
			leavesToDeduct = (Math.round((((totalLeaves / 8) / daysConsidered) * 1.67) * 2) / 2.0);
			leavesToDeduct = leavesToDeduct < prevDeduct ? prevDeduct : leavesToDeduct;
			leavesDeducted = leavesToDeduct - leavesDeducted;
			double finalDeduction = leavesToDeduct-prevDeduct;
			leaveByMonth.put((i + 1), (int) ((finalDeduction) * 8));
			prevDeduct = leavesToDeduct;
		}
		return leaveByMonth;
	}

	private static int getWorkingDays(int year, int month, int joinDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DATE, joinDate);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		LocalDate startDate = new LocalDate(cal.getTimeInMillis());
		cal.set(Calendar.DATE, 0);
		cal.set(Calendar.MONTH, month + 1);
		LocalDate endDate = new LocalDate(cal.getTimeInMillis());
		int totalDays = 0;
		while ((startDate.isBefore(endDate) || startDate.isEqual(endDate))) {
			if ((startDate.getDayOfWeek() != DateTimeConstants.SATURDAY
					&& startDate.getDayOfWeek() != DateTimeConstants.SUNDAY)) {
				totalDays++;
			}
			startDate = startDate.plusDays(1);
		}
		return totalDays;
	}

	private int[] getNonEarnedLeaveDetails(Employee employeeOnLeave, EmployeeLeaves employeeLeaves, Session session)
			throws ParseException {
		int[] leaveByMonth = new int[12];
		List<String> reqTypeList = new ArrayList<>();
		reqTypeList.add(LeaveType.LOSS_OF_PAY.getLeaveType());
//		reqTypeList.add(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType());
		reqTypeList.add(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType());
		if (reqTypeList.contains(employeeLeaves.getRequestType())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(employeeLeaves.getStartsAt());
			sdf.format(sdf.parse(cal.get(Calendar.YEAR) + "-01-01"));
			Map<Date, Integer> getLeavesBtwDateMap = employeeLeavesDAO.getLeavesBtwDate(reqTypeList,
					sdf.parse(cal.get(Calendar.YEAR) + "-01-01"), sdf.parse(cal.get(Calendar.YEAR) + "-12-31"),
					employeeOnLeave.getPk(), session);
			Map<Integer, Integer> leaveCountByMonth = new HashMap<>();
			for (Date dt : getLeavesBtwDateMap.keySet()) {
				int month = new LocalDate(dt).getMonthOfYear();
				if (leaveCountByMonth.containsKey(month)) {
					leaveCountByMonth.put(month, leaveCountByMonth.get(month) + getLeavesBtwDateMap.get(dt));
				} else {
					leaveCountByMonth.put(month, getLeavesBtwDateMap.get(dt));
				}
			}
			for (int month : leaveCountByMonth.keySet()) {
				leaveByMonth[month - 1] = leaveCountByMonth.get(month);
			}
		}
		return leaveByMonth;
	}

	private Calendar getCurrentDate() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		TimeZone tz = TimeZone.getTimeZone("IST");
		now.setTimeZone(tz);
		return now;
	}

	private Calendar getLeaveRange(Employee employeeOnLeave, boolean isStart) {
		Calendar cal = Calendar.getInstance();
		if (isStart) {
			cal.set(Calendar.MONTH, getCurrentDate().get(Calendar.MONTH) - 1);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 1);
			cal.set(Calendar.AM_PM, Calendar.AM);
		} else {
			cal.set(Calendar.MONTH, getCurrentDate().get(Calendar.MONTH) + 11);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.HOUR, 11);
			cal.set(Calendar.AM_PM, Calendar.PM);
		}
		return cal;
	}

	private boolean validateLeave(Employee employeeOnLeave, EmployeeLeaves employeeLeaves, Session session)
			throws PrftException, RecordExistsException, ParseException {
		if (employeeLeaves.getStartsAt() == null) {
			throw new PrftException("Start Date cannot be Empty.");
		} else if (employeeLeaves.getEndsAt() == null) {
			throw new PrftException("End Date cannot be Empty.");
		}

		Calendar leaveStDate = Calendar.getInstance();
		leaveStDate.setTime(employeeLeaves.getStartsAt());
		Calendar leaveEndDate = Calendar.getInstance();
		leaveEndDate.setTime(employeeLeaves.getEndsAt());

		if (leaveStDate.get(Calendar.YEAR) != leaveEndDate.get(Calendar.YEAR)) {
			throw new PrftException("Leaves across years is not allowed.  Please try to apply within same year.");
		}

		if (employeeLeaves.getStartsAt().getTime() < getLeaveRange(employeeOnLeave, true).getTime().getTime()) {
			throw new PrftException("Start Date is not within permissable date range.");
		} else if (employeeLeaves.getEndsAt().getTime() > getLeaveRange(employeeOnLeave, false).getTime().getTime()) {
			throw new PrftException("End Date is not within permissable date range.");
		} else if (employeeLeaves.getStartsAt().getTime() > employeeLeaves.getEndsAt().getTime()) {
			throw new PrftException("End Date must be Greater than Start Date.");
		} else if (employeeLeaves.getStartsAt().getTime() < employeeOnLeave.getJoinDate().getTime()) {
			throw new PrftException("Leaves can't be applied before Joining date.");
		} /*else if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
				&& employeeLeaves.getPk() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date stDt = sdf.parse(getCurrentDate().get(Calendar.DATE) + "/"
					+ ((int) getCurrentDate().get(Calendar.MONTH) + 1) + "/" + getCurrentDate().get(Calendar.YEAR));
			Calendar empLeaveStDt = Calendar.getInstance();
			empLeaveStDt.setTime(employeeLeaves.getStartsAt());
			SimpleDateFormat sdfTime = new SimpleDateFormat("dd/MM/yyyy");
			Date leaveStDt = sdfTime.parse(empLeaveStDt.get(Calendar.DATE) + "/"
					+ ((int) empLeaveStDt.get(Calendar.MONTH) + 1) + "/" + empLeaveStDt.get(Calendar.YEAR));

			if (((leaveStDt.getTime() - stDt.getTime()) / (1000 * 60 * 60)) < 48) {
				throw new PrftException(
						employeeLeaves.getRequestType() + " should be applied atleast two days in advance.");
			}
		}*/ else if((employeeOnLeave.getDesignations().getDesignation().equals(PerfHrConstants.SUBCONTRACTOR)
				|| employeeOnLeave.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_ADMIN)
				|| employeeOnLeave.getDesignations().getDesignation().equals(PerfHrConstants.INTERN_CONSULTING))
				&& (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
						||employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType()))){
			throw new PrftException("Please apply leaves as "+LeaveType.LOSS_OF_PAY.getLeaveType());
		}

		// Check for gender on leaves
		validateGender(employeeOnLeave, employeeLeaves);
		// check for duplicate leave..
		if (!isLeaveAppliedOnSameDate(employeeLeaves, session))
			throw new RecordExistsException();
		return true;
	}

	private boolean validateGender(Employee employeeOnLeave, EmployeeLeaves employeeLeaves) throws PrftException {
		if (employeeOnLeave.getGender().equalsIgnoreCase("Female")
				&& employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())) {
			throw new PrftException(LeaveType.PATERNITY_WFH.getLeaveType() + " can't be applied.");
		} else if (employeeOnLeave.getGender().equalsIgnoreCase("Male")
				&& (employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType()))) {
			throw new PrftException(employeeLeaves.getRequestType() + " can't be applied.");
		}
		return true;
	}

	private boolean isLeaveAppliedOnSameDate(EmployeeLeaves employeeLeaves, Session session)
			throws RecordExistsException, ParseException {
		List<Object[]> employeeLeavesList = employeeLeavesDAO.isLeaveApplied(employeeLeaves, session);
		boolean returnVal = true;
		if(employeeLeavesList.size() > 0) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Object[] obj : employeeLeavesList) {
				Date dt = sdf.parse(obj[0].toString());
				cal.setTime(dt);
				if(obj[2].toString().equals(PerfHrConstants.FIRST_HALF)){
					cal.set(Calendar.HOUR, 9);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.AM_PM, Calendar.AM);
				} else {
					cal.set(Calendar.HOUR, 1);
					cal.set(Calendar.MINUTE, 30);
					cal.set(Calendar.AM_PM, Calendar.PM);
				}
				Date startDt = cal.getTime();
				if(obj[3].toString().equals(PerfHrConstants.FIRST_HALF)){
					cal.set(Calendar.HOUR, 1);
					cal.set(Calendar.MINUTE, 30);
					cal.set(Calendar.AM_PM, Calendar.PM);
				} else {
					cal.set(Calendar.HOUR, 5);
					cal.set(Calendar.MINUTE, 30);
					cal.set(Calendar.AM_PM, Calendar.PM);
				}
				Date endDt = cal.getTime();
				if (sdf.format(obj[0]).equals(sdf.format(employeeLeaves.getStartsAt())) && ((int) (obj[1]) == 8
						|| ((int) (obj[1]) == 4 && (obj[2].toString().equals(employeeLeaves.getDtFromHalf()))))) {
					returnVal = false;
				} else if (sdf.format(obj[0]).equals(sdf.format(employeeLeaves.getEndsAt())) && ((int) (obj[1]) == 8
						|| ((int) (obj[1]) == 4 && (obj[2].toString().equals(employeeLeaves.getDtEndHalf()))))) {
					returnVal = false;
				} else if (startDt.getTime() > employeeLeaves.getStartsAt().getTime()
						&& endDt.getTime() < employeeLeaves.getEndsAt().getTime()) {
					returnVal = false;
				}
			}
		}
		return returnVal;
	}

	private boolean validateMaternityLeaves(Employee employeeOnLeave, EmployeeLeaves employeeLeaves, Session session)
			throws ParseException, PrftException {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// check for leave balance in months to verify its approval
		if (employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
				|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
			cal.setTime(employeeLeaves.getStartsAt());
			cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) - 1));
			Date prevYearStDt = sdf.parse(sdf.format(cal.getTime()));
			cal.setTime(employeeLeaves.getEndsAt());
			cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + 1));
			Date nextYearEndDt = sdf.parse(sdf.format(cal.getTime()));
			Object[] obj = (Object[]) employeeLeavesDAO.getMinMaxLeaveByDate(employeeLeaves.getRequestType(), prevYearStDt,
					nextYearEndDt, employeeOnLeave.getPk(), session);
			LocalDate startDate = new LocalDate(sdf.parse(obj[0].toString()));
			LocalDate endDate = new LocalDate(sdf.parse(obj[1].toString()));
			int totalDays = 0, maternityDays = 0;
			while ((startDate.isBefore(endDate) || startDate.isEqual(endDate))) {
				if ((startDate.getDayOfWeek() != DateTimeConstants.SATURDAY && startDate.getDayOfWeek() != DateTimeConstants.SUNDAY)){
					maternityDays++;
				}
				totalDays++;
				startDate = startDate.plusDays(1);
			}
			Long leaveTaken = employeeLeavesDAO.getLeavesTakenByDate(employeeLeaves.getRequestType(), prevYearStDt,
					nextYearEndDt, employeeOnLeave.getPk(), session);
			if((maternityDays) != (leaveTaken) / 8) {
				throw new PrftException(employeeLeaves.getRequestType() + " should be continuous working days.");
			}
			if(totalDays > Integer.parseInt(perfProperties.getMaternityCount())
					&& employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())) {
				throw new PrftException(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()
						+ " Exceeding the permissible limit. Please Apply as "
						+ LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType() + ".");
			}
		}
		return true;
	}
	
	private boolean validateWfh(Employee employeeOnLeave, EmployeeLeaves employeeLeaves, Session session)
			throws ParseException, PrftException {
		boolean returnVal = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// check for wfh balance in months to verify its approval
		Calendar cal = Calendar.getInstance();
		cal.setTime(employeeLeaves.getStartsAt());
		if(employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())){
			if(employeeLeaves.getHours() > (Integer.parseInt(perfProperties.getWfhContinuousLimit())*8)){
				returnVal =  false;
			} else {
				int startMonth = cal.get(Calendar.MONTH);
				Date endDt = employeeLeaves.getEndsAt();
				cal.setTime(endDt);
				int endMonth = cal.get(Calendar.MONTH);
				Long leaveTaken;
				leaveTaken = checkWfhBalanceByQuarter(employeeLeaves.getStartsAt(), employeeLeaves, employeeOnLeave, session);
				//check for leave count based on end date
				if(startMonth != endMonth && (leaveTaken < (Integer.parseInt(perfProperties.getWfhLimit())*8))){
					leaveTaken = checkWfhBalanceByQuarter(employeeLeaves.getEndsAt(), employeeLeaves, employeeOnLeave, session);
				}
				if(leaveTaken > (Integer.parseInt(perfProperties.getWfhLimit())*8)){
					returnVal = false;
				}
			}
			if(!returnVal){
				//Add GM as the notifier if the limit exceeds and make him the approver
				EmployeeNamesView empNameView = employeeDAO.loadUserNameViewByEmail(perfProperties.getNotifyAsApprover(), session);
				employeeLeaves.getNotificationToList().add(empNameView);
				employeeLeaves.setChangeApprover(true);
			}
			return returnVal;
		} else {
			Date startDt = sdf.parse(cal.get(Calendar.YEAR)+"-01-01");
			Date endDt = sdf.parse(cal.get(Calendar.YEAR)+"-12-31");
			Long leaveTaken = employeeLeavesDAO.getLeavesTakenByDate(employeeLeaves.getRequestType(), startDt,
					endDt, employeeOnLeave.getPk(), session);
			if(employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
				&& (leaveTaken > (Integer.parseInt(perfProperties.getMaternityWfhCount())*8))){
				throw new PrftException(employeeLeaves.getRequestType() + " exceeding the limit.");
			} else if(employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())
				&& (leaveTaken > (Integer.parseInt(perfProperties.getPaternityWfhCount())*8))){
				throw new PrftException(employeeLeaves.getRequestType() + " exceeding the limit.");
			}	
		}
		return returnVal;
	}

	private Long checkWfhBalanceByQuarter(Date leaveDate, EmployeeLeaves employeeLeaves, Employee employeeOnLeave, Session session) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(leaveDate);
		int month = cal.get(Calendar.MONTH);
		Date startDt = employeeLeaves.getStartsAt();
		Date endDt = employeeLeaves.getEndsAt();
		if(month <= 2){
			//Jan-Mar
			startDt = sdf.parse(cal.get(Calendar.YEAR)+"-01-01");
			endDt = sdf.parse(cal.get(Calendar.YEAR)+"-03-31");
		} else if(month <= 5){
			//Apr-Jun
			startDt = sdf.parse(cal.get(Calendar.YEAR)+"-04-01");
			endDt = sdf.parse(cal.get(Calendar.YEAR)+"-06-30");
		} else if(month <= 8){
			//Jul-Sep
			startDt = sdf.parse(cal.get(Calendar.YEAR)+"-07-01");
			endDt = sdf.parse(cal.get(Calendar.YEAR)+"-09-30");
		} else {
			//Oct-Dec
			startDt = sdf.parse(cal.get(Calendar.YEAR)+"-10-01");
			endDt = sdf.parse(cal.get(Calendar.YEAR)+"-12-31");
		}
		return employeeLeavesDAO.getLeavesTakenByDate(employeeLeaves.getRequestType(), startDt,
				endDt, employeeOnLeave.getPk(), session);
	}
	
	@Override
	public Object applyLeave(EmployeeLeaves employeeLeaves, String userId, boolean isSystem) {
		LoggerUtil.infoLog(logger, "Apply Employee Leave Service Started.");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			Employee employeeOnLeave = employeeDAO.loadById(employeeLeaves.getEmployeeId().toString(), session);

			/*if (!isSystem && !employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType()))
				validateLeave(employeeOnLeave, employeeLeaves, session);
			else if(isSystem && employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())){
				try{
					validateLeave(employeeOnLeave, employeeLeaves, session);
				} catch(RecordExistsException re){
					return null;
				}
			}*/
			validateLeave(employeeOnLeave, employeeLeaves, session);

			employeeLeaves.setMailSequence(1);
			employeeLeaves.setAppliedById(employee.getPk());
			employeeLeaves.setDtCreated(new Date());
			employeeLeaves.setDtModified(new Date());
			employeeLeaves.setCreatedBy(employee.getPk());
			employeeLeaves.setModifiedBy(employee.getPk());
			employeeLeaves.setEmployeeNamesView(
					employeeDAO.loadUserNameById(employeeLeaves.getEmployeeId().toString(), session));
			List<EmployeeLeaveDetails> empLeaveDetailsList = getEmployeeLeaveDetails(employeeLeaves, employeeOnLeave,
					session);
			employeeLeaves = employeeLeavesDAO.saveLeave(employeeLeaves, session);

			// load non earned leave details before update
			int[] nonEarnedLeaves = getNonEarnedLeaveDetails(employeeOnLeave, employeeLeaves, session);

			for (EmployeeLeaveDetails empLeaveDetails : empLeaveDetailsList) {
				// set employee leaves pk for reference
				empLeaveDetails.setEmployeeLeavesId(employeeLeaves.getPk());
				empLeaveDetails.setCreatedBy(employee.getPk());
				empLeaveDetails.setModifiedBy(employee.getPk());
				employeeLeavesDAO.saveLeaveDetails(empLeaveDetails, session);
			}
			if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType())) {
				final LocalDate start = new LocalDate(employeeLeaves.getStartsAt().getTime());
				updateEmployeeLeaveBalance(start.getYear(), employeeOnLeave.getPk().toString(),
						employeeLeaves.getLeaveHoursMonth(), session);
			} else if (employeeLeaves.getRequestType().equals(LeaveType.LOSS_OF_PAY.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
				//skip Matenrity paid leave for leaves credit
				if(!employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()))
					updateLeaveBalByNonEarnedLeaves(employeeOnLeave, employeeLeaves, nonEarnedLeaves, session);
				if (!isSystem)
					validateMaternityLeaves(employeeOnLeave, employeeLeaves, session);
			} else if(employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())){
				if (!isSystem)
					validateWfh(employeeOnLeave, employeeLeaves, session);
			}
			if (!isSystem) {
				for (EmployeeNamesView notify : employeeLeaves.getNotificationToList()) {
					notificationDAO.saveNotification(setNotification(employeeLeaves, employeeOnLeave, employee, notify),
							session);
				}
				emailTrackDAO.saveEmailTrack(setEmailTrack(employeeLeaves, employeeOnLeave), session);
			} else {
				EmployeeNamesView notify = new EmployeeNamesView();
				notify.setPk(employeeOnLeave.getPk());
				Notification notification = setNotification(employeeLeaves, employeeOnLeave, employee, notify);
				notification.setNotificationStatus(NotificationStatusType.APPROVED.getNotificationStatusType());
				notificationDAO.saveNotification(notification, session);
			}
			tx.commit();
		} catch (PrftException e) {
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch (RecordExistsException e) {
			LoggerUtil.errorLog(logger, LeaveType.PTO.getLeaveType() + "/" + LeaveType.WFH.getLeaveType()
					+ " already applied on this date(s) " + employeeLeaves.getTitle(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(
					LeaveType.PTO.getLeaveType() + "/" + LeaveType.WFH.getLeaveType()
							+ " already applied on this date(s) " + employeeLeaves.getTitle(),
					HttpStatus.CONFLICT.value());
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to apply Leave: " + employeeLeaves.getTitle(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to apply Leave: " + employeeLeaves.getTitle(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employeeLeaves;
	}

	private List<EmployeeLeaveDetails> getEmployeeLeaveDetails(EmployeeLeaves employeeLeaves, Employee employee,
			Session session) throws PrftException, ParseException {
		List<EmployeeLeaveDetails> empLeaveDetailsList = new ArrayList<>();
		final LocalDate start = new LocalDate(employeeLeaves.getStartsAt().getTime());
		final LocalDate end = new LocalDate(employeeLeaves.getEndsAt().getTime());
		List<Holidays> holidaysList = holidayDAO.getHolidaysByYear(String.valueOf(start.getYear()), session);
		List<LocalDate> holidayDate = new ArrayList<>();
		for (Holidays holiday : holidaysList) {
			holidayDate.add(new LocalDate(holiday.getHolidayDate().getTime()));
		}
		List<LocalDate> dateList = getAllDays(employeeLeaves.getStartsAt().getTime(),
				employeeLeaves.getEndsAt().getTime());
		int i = 0, totalHours = 0;
		Map<Integer, Integer> leaveHoursMonth = new LinkedHashMap<>();
		for (LocalDate date : dateList) {
			// validation for holiday
			if (holidayDate.contains(date)
					&& !employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
					&& !employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
				throw new PrftException(employeeLeaves.getRequestType()+" can't be applied on Holidays.");
			}
			EmployeeLeaveDetails empLeaveDetails = new EmployeeLeaveDetails();
			empLeaveDetails.setLeaveDate(java.sql.Date.valueOf(date.toString()));
			int hours = 8;
			if (date.getDayOfWeek() == DateTimeConstants.SATURDAY || date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
				hours = 0;
			} else if ((i == 0 && employeeLeaves.getDtFromHalf().equals(PerfHrConstants.SECOND_HALF)
					&& date.equals(start))
					|| (i == (dateList.size() - 1) && employeeLeaves.getDtEndHalf().equals(PerfHrConstants.FIRST_HALF)
							&& date.equals(end))) {
				hours = 4;
			}
			if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.LOSS_OF_PAY.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
				if (leaveHoursMonth.containsKey(date.getMonthOfYear())) {
					leaveHoursMonth.put(date.getMonthOfYear(), leaveHoursMonth.get(date.getMonthOfYear()) + hours);
				} else {
					leaveHoursMonth.put(date.getMonthOfYear(), hours);
				}
			}
			totalHours += hours;
			empLeaveDetails.setHours(hours);
			empLeaveDetails.setDtCreated(new Date());
			empLeaveDetails.setDtModified(new Date());
			empLeaveDetailsList.add(empLeaveDetails);
			i++;
		}
		employeeLeaves.setLeaveHoursMonth(leaveHoursMonth);
		employeeLeaves.setHours(totalHours);
		return empLeaveDetailsList;
	}

	@Override
	public Object updateLeave(EmployeeLeaves employeeLeaves, String userId) {
		LoggerUtil.infoLog(logger, "Update Employee Leave Service Started.");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			EmployeeLeaves empLeaves = employeeLeavesDAO.loadLeaveById(employeeLeaves.getPk().toString(), session);
			if (empLeaves.isActive()) {
				throw new PrftException("Unable to update as the record is no longer available.");
			}
			Employee employeeOnLeave = employeeDAO.loadById(employeeLeaves.getEmployeeId().toString(), session);
			/*if (!employeeOnLeave.getPk().toString().equals(userId)) {
				throw new PrftException("UnAuthorized access to update Leave Record.");
			}*/
			validateLeave(employeeOnLeave, employeeLeaves, session);
			Long orgStartsAt = empLeaves.getStartsAt().getTime();
			Long orgEndsAt = empLeaves.getEndsAt().getTime();
			String reqType = empLeaves.getRequestType();
			employeeLeaves.setModifiedBy(employeeOnLeave.getPk());
			employeeLeaves.setDtModified(new Date());
			List<EmployeeLeaveDetails> empLeaveDetailsList = getEmployeeLeaveDetails(employeeLeaves, employeeOnLeave,
					session);
			employeeLeaves.setMailSequence((employeeLeaves.getMailSequence() + 1));
			employeeLeavesDAO.updateLeave(employeeLeaves, session);
			if (orgStartsAt != employeeLeaves.getStartsAt().getTime()
					|| orgEndsAt != employeeLeaves.getEndsAt().getTime()
					|| !empLeaves.getRequestType().equals(reqType)) {
				// validate PTO on update when date's were changed
				if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date stDt = sdf.parse(
							getCurrentDate().get(Calendar.DATE) + "/" + ((int) getCurrentDate().get(Calendar.MONTH) + 1)
									+ "/" + getCurrentDate().get(Calendar.YEAR));
					Calendar empLeaveStDt = Calendar.getInstance();
					empLeaveStDt.setTime(employeeLeaves.getStartsAt());
					SimpleDateFormat sdfTime = new SimpleDateFormat("dd/MM/yyyy");
					Date leaveStDt = sdfTime.parse(empLeaveStDt.get(Calendar.DATE) + "/"
							+ ((int) empLeaveStDt.get(Calendar.MONTH) + 1) + "/" + empLeaveStDt.get(Calendar.YEAR));
					if (((leaveStDt.getTime() - stDt.getTime()) / (1000 * 60 * 60)) < 48) {
						throw new PrftException(
								employeeLeaves.getRequestType() + " should be applied atleast two days in advance.");
					}
				}

				Map<Integer, Integer> leaveHoursByMonth = new HashMap<>();
				if (!employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
						&& !employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())
						&& !employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())) {
					// Load load hours by month before update for changing the
					// leave balance
					List<Object[]> loadEmpLeaveDetails = employeeLeavesDAO.getLeaveDetailsById(employeeLeaves.getPk(),
							session);
					for (Object[] empLeaveDetail : loadEmpLeaveDetails) {
						Date dt = (Date) empLeaveDetail[0];
						int month = new LocalDate(dt.getTime()).getMonthOfYear();
						if (leaveHoursByMonth.containsKey(month)) {
							leaveHoursByMonth.put(month, leaveHoursByMonth.get(month) + (int) empLeaveDetail[1]);
						} else {
							leaveHoursByMonth.put(month, (int) empLeaveDetail[1]);
						}
					}
				}
				// load non earned leave details before update
				int[] nonEarnedLeaves = getNonEarnedLeaveDetails(employeeOnLeave, employeeLeaves, session);
				employeeLeavesDAO.updateLeaveDetails(empLeaves.getPk(), PerfHrConstants.INACTIVE, session);
				for (EmployeeLeaveDetails empLeaveDetails : empLeaveDetailsList) {
					empLeaveDetails.setEmployeeLeavesId(employeeLeaves.getPk());
					empLeaveDetails.setCreatedBy(employeeOnLeave.getPk());
					empLeaveDetails.setModifiedBy(employeeOnLeave.getPk());
					employeeLeavesDAO.saveLeaveDetails(empLeaveDetails, session);
				}

				if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType())) {
					final LocalDate start = new LocalDate(employeeLeaves.getStartsAt().getTime());
					// construct updates for leave balance
					for (int month : leaveHoursByMonth.keySet()) {
						if (employeeLeaves.getLeaveHoursMonth().containsKey(month)) {
							int updHours = employeeLeaves.getLeaveHoursMonth().get(month)
									- leaveHoursByMonth.get(month);
							employeeLeaves.getLeaveHoursMonth().put(month, updHours);
						} else {
							employeeLeaves.getLeaveHoursMonth().put(month, -leaveHoursByMonth.get(month));
						}
					}
					updateEmployeeLeaveBalance(start.getYear(), employeeOnLeave.getPk().toString(),
							employeeLeaves.getLeaveHoursMonth(), session);
				} else if (employeeLeaves.getRequestType().equals(LeaveType.LOSS_OF_PAY.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
					if(!employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()))
						updateLeaveBalByNonEarnedLeaves(employeeOnLeave, employeeLeaves, nonEarnedLeaves, session);
					validateMaternityLeaves(employeeOnLeave, employeeLeaves, session);
				} else if(employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
						|| employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())){
					validateWfh(employeeOnLeave, employeeLeaves, session);
				}
			}

			List<Long> employeesDB = notificationService.loadNotificationsToByGenericId(employeeLeaves.getPk(),
					session);
			List<String> dbList = new ArrayList<>();
			for (Long emp : employeesDB) {
				dbList.add(emp.toString());
			}
			List<String> tempList = new ArrayList<>();
			tempList.addAll(dbList);
			List<String> updatedList = new ArrayList<>();
			for (EmployeeNamesView emp : employeeLeaves.getNotificationToList()) {
				updatedList.add(emp.getPk().toString());
			}
			// Deleted Notifiers
			dbList.removeAll(updatedList);
			for (String empPk : dbList) {
				Notification notification = notificationService.loadByGenericAndEmployeeId(employeeLeaves.getPk(),
						Long.parseLong(empPk), PerfHrConstants.ACTIVE, session);
				notification.setActive(PerfHrConstants.INACTIVE);
				notification.setFlag(PerfHrConstants.READ);
				notification.setModifiedBy(employeeOnLeave.getPk());
				notification.setDtModified(new Date());
				notificationService.updateNotification(notification, session);
			}
			// New Notifiers
			updatedList.removeAll(tempList);
			for (String newPk : updatedList) {
				EmployeeNamesView emp = employeeDAO.loadUserNameById(newPk, session);
				notificationService.saveNotification(setNotification(employeeLeaves, employeeOnLeave, employeeOnLeave, emp),
						session);
			}
			// update existing notifiers
			tempList.removeAll(dbList);
			for (String empPk : tempList) {
				Notification notification = notificationService.loadByGenericAndEmployeeId(employeeLeaves.getPk(),
						Long.parseLong(empPk), PerfHrConstants.ACTIVE, session);
				notification.setActive(PerfHrConstants.ACTIVE);
				/*if (empPk.equals(employeeOnLeave.getSupervisor().toString())) {
					notification.setFlag(PerfHrConstants.UNREAD);
				} else {
					notification.setFlag(PerfHrConstants.READ);
				}*/
				Employee notifyUser = employeeDAO.loadById(empPk, session);
				if(employeeLeaves.isChangeApprover() && notifyUser.getEmail().equalsIgnoreCase(perfProperties.getNotifyAsApprover())){
					notification.setFlag(PerfHrConstants.UNREAD);
				} else if(!employeeLeaves.isChangeApprover() && empPk.equals(employeeOnLeave.getSupervisor().toString())) {
					notification.setFlag(PerfHrConstants.UNREAD);
				} else {
					notification.setFlag(PerfHrConstants.READ);
				}
				notification.setNotificationStatus(NotificationStatusType.PENDING.getNotificationStatusType());
				notification.setModifiedBy(employeeOnLeave.getPk());
				notification.setDtModified(new Date());
				notificationService.updateNotification(notification, session);
//				EmployeeNamesView emp = employeeDAO.loadUserNameById(empPk, session);
			}
			emailTrackDAO.saveEmailTrack(setEmailTrack(employeeLeaves, employeeOnLeave), session);
			tx.commit();
		} catch (PrftException e) {
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch (RecordExistsException e) {
			LoggerUtil.errorLog(logger, "PTO/WFH already applied on this date(s) " + employeeLeaves.getTitle(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(
					employeeLeaves.getRequestType() + " already applied on this date(s) " + employeeLeaves.getTitle(),
					HttpStatus.CONFLICT.value());
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to update leave: " + employeeLeaves.getTitle(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update leave: " + employeeLeaves.getTitle(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return employeeLeaves;
	}

	private Notification setNotification(EmployeeLeaves empLeaves, Employee employeeOnLeave, Employee employee,
			EmployeeNamesView notifyUser) {
		Notification notification = new Notification();
		notification.setIdGeneric(empLeaves.getPk());
		notification.setNotificationTo(notifyUser.getPk());
		if(empLeaves.isChangeApprover() && notifyUser.getEmail().equalsIgnoreCase(perfProperties.getNotifyAsApprover())){
			notification.setFlag(PerfHrConstants.UNREAD);
		} else if(!empLeaves.isChangeApprover() && notifyUser.getPk().equals(employeeOnLeave.getSupervisor())) {
			notification.setFlag(PerfHrConstants.UNREAD);
		} else {
			notification.setFlag(PerfHrConstants.READ);
		}
		
		notification.setNotificationStatus(NotificationStatusType.PENDING.getNotificationStatusType());
		if (empLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
				|| empLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())
				|| empLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType()))
			notification.setNotificationType(LeaveType.WFH.getLeaveType());
		else
			notification.setNotificationType(LeaveType.PTO.getLeaveType());
		notification.setActive(PerfHrConstants.ACTIVE);
		notification.setCreatedBy(employee.getPk());
		notification.setModifiedBy(employee.getPk());
		notification.setDtCreated(new Date());
		notification.setDtModified(new Date());
		return notification;
	}

	private EmailTrack setEmailTrack(EmployeeLeaves empLeaves, Employee employee) {
		EmailTrack emailTrack = new EmailTrack();
		emailTrack.setIdGeneric(empLeaves.getPk());
		emailTrack.setMailType(MailType.PTO_WFH.getMailType());
		String leaveDistribution[];
		if(empLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
				|| empLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
				|| empLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())){
			leaveDistribution = perfProperties.getWfhEmailList().split(",");
		} else {
			leaveDistribution = perfProperties.getPtoEmailList().split(",");
		}
		Set<String> emailDistSet = new HashSet<>();
		for(String leaveDistEmail: leaveDistribution){
			emailDistSet.add(leaveDistEmail);
		}
		for(EmployeeNamesView empViewEmail: empLeaves.getNotificationToList()){
			emailDistSet.add(empViewEmail.getEmail());
		}
		emailTrack.setMailTo(emailDistSet.toString().replace("[","").replace("]",""));
		emailTrack.setMailFrom(employee.getEmail());
		emailTrack.setComments(empLeaves.getComments());
		emailTrack.setSubject(empLeaves.getTitle());
		emailTrack.setMediaType(MailMediaType.CALENDAR.getMailMediaType());
		if (!empLeaves.isActive())
			emailTrack.setRequestType(PerfHrConstants.REQUEST);
		else
			emailTrack.setRequestType(PerfHrConstants.CANCEL);
		emailTrack.setMailStatus(PerfHrConstants.PENDING);
		emailTrack.setPriority("5");
		emailTrack.setUid(employee.getEmployeeId().toString() +"-"+ empLeaves.getPk()+"-"+empLeaves.getDtCreated().getTime());
		emailTrack.setMailSequence(empLeaves.getMailSequence());
		emailTrack.setFlag(0);
		emailTrack.setFromDate(empLeaves.getStartsAt());
		emailTrack.setToDate(empLeaves.getEndsAt());
		emailTrack.setActive(PerfHrConstants.ACTIVE);
		emailTrack.setCreatedBy(empLeaves.getEmployeeId());
		emailTrack.setModifiedBy(empLeaves.getEmployeeId());
		emailTrack.setDtCreated(new Date());
		emailTrack.setDtModified(new Date());
		return emailTrack;
	}

	private List<LocalDate> getAllDays(long startDt, long endDt) {
		List<LocalDate> dateList = new ArrayList<>();
		LocalDate startDate = new LocalDate(startDt);
		LocalDate endDate = new LocalDate(endDt);
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			dateList.add(startDate);
			startDate = startDate.plusDays(1);
		}
		return dateList;
	}

	@Override
	public Object deleteLeave(EmployeeLeaves employeeLeaves, String userId) {
		LoggerUtil.infoLog(logger, "Delete Employee Leave Service Started. ");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			EmployeeLeaves empLeaves = employeeLeavesDAO.loadLeaveById(employeeLeaves.getPk().toString(), session);
			if (empLeaves.isActive()) {
				throw new PrftException("Unable to delete as the record is no longer available.");
			}
			Employee employeeOnLeave = employeeDAO.loadById(employeeLeaves.getEmployeeId().toString(), session);
			/*if (!employeeOnLeave.getPk().toString().equals(userId)) {
				throw new PrftException("UnAuthorized access to update Leave Record.");
			}*/
			// Load leave hours by month before delete to update the balance
			List<Object[]> loadEmpLeaveDetails = employeeLeavesDAO.getLeaveDetailsById(employeeLeaves.getPk(), session);
			// load non earned leave details before update
			int[] nonEarnedLeaves = getNonEarnedLeaveDetails(employeeOnLeave, employeeLeaves, session);
			tx = session.beginTransaction();
			employeeLeaves.setMailSequence((employeeLeaves.getMailSequence() + 1));
			employeeLeaves.setActive(PerfHrConstants.INACTIVE);
			employeeLeaves.setDtModified(new Date());
			employeeLeavesDAO.updateLeave(employeeLeaves, session);
			employeeLeavesDAO.updateLeaveDetails(employeeLeaves.getPk(), PerfHrConstants.INACTIVE, session);

			String notificationType = NotificationType.WFH.getNotificationType();
			if (employeeLeaves.getRequestType().equals(LeaveType.PTO.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.UNPLANNED_PTO.getLeaveType())) {
				notificationType = NotificationType.PTO.getNotificationType();
				Map<Integer, Integer> leaveHoursByMonth = new HashMap<>();
				for (Object[] empLeaveDetail : loadEmpLeaveDetails) {
					Date dt = (Date) empLeaveDetail[0];
					int month = new LocalDate(dt.getTime()).getMonthOfYear();
					if (leaveHoursByMonth.containsKey(month)) {
						leaveHoursByMonth.put(month, leaveHoursByMonth.get(month) - (int) empLeaveDetail[1]);
					} else {
						leaveHoursByMonth.put(month, -(int) empLeaveDetail[1]);
					}
				}
				final LocalDate start = new LocalDate(employeeLeaves.getStartsAt().getTime());
				updateEmployeeLeaveBalance(start.getYear(), employeeOnLeave.getPk().toString(), leaveHoursByMonth,
						session);
			} else if (employeeLeaves.getRequestType().equals(LeaveType.LOSS_OF_PAY.getLeaveType())
//					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType())
					|| employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType())) {
				notificationType = NotificationType.PTO.getNotificationType();
				updateLeaveBalByNonEarnedLeaves(employeeOnLeave, employeeLeaves, nonEarnedLeaves, session);
			}
			notificationDAO.deleteNotificationByGenericIdAndType(employeeLeaves.getPk(),
					 notificationType, session);
			//for (EmployeeNamesView notify : employeeLeaves.getNotificationToList()) {
				/*notificationDAO.saveNotification(
						setNotification(employeeLeaves, employeeOnLeave, employeeOnLeave, notify), session);*/
				emailTrackDAO.saveEmailTrack(setEmailTrack(employeeLeaves, employeeOnLeave), session);
			//}
			tx.commit();
		} catch (PrftException e) {
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to delete employee Leaves: " + employeeLeaves.getTitle(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil
					.returnErrorObject("Unable to delete employee Leaves: " + employeeLeaves.getTitle(), e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}

	@Override
	public Object loadLeaveById(String leaveId) {
		LoggerUtil.infoLog(logger, "Delete Employee Leave Service Started. ");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return employeeLeavesDAO.loadLeaveById(leaveId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load leave Id:" + leaveId, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load leave Id:" + leaveId, e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getLeaveBalanceByMonth(String leaveType, String calYear, String employeeId) {
		LoggerUtil.infoLog(logger, "Load Leave Balance for employee: " + employeeId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return employeeLeavesDAO.getLeaveBalanceByMonth(leaveType, calYear, employeeId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to get leave balance for employee: " + employeeId, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get leave balance for employee: '" + employeeId,
					e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getLeaveBalanceByType(String leaveType, String calYear, String employeeId) {
		LoggerUtil.infoLog(logger, "Load Leave Balance for employee: " + employeeId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return employeeLeavesDAO.getLeaveBalanceByType(leaveType, calYear, employeeId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to get leave balance for employee: " + employeeId, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get leave balance for employee: '" + employeeId,
					e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadEmployeesByLeaveDate(Date leaveDate) {
		LoggerUtil.infoLog(logger, "Load employee by date : " + leaveDate);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return employeeLeavesDAO.loadEmployeesByLeaveDate(leaveDate, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load employee by date : " + leaveDate, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load employee by date : " + leaveDate, e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object updateWfh(EmployeeLeaves employeeLeaves, String userId) {
		return updateLeave(employeeLeaves, userId);
	}

	@Override
	public Object deleteWfh(EmployeeLeaves employeeLeaves, String userId) {
		return deleteLeave(employeeLeaves, userId);
	}

	@Override
	public Object updateEmpLeaveBalanceOnResignation(Date lastWorkingDate, String employeeId, Session session) throws ParseException {
		LoggerUtil.infoLog(logger, "updateEmpLeaveBalanceOnResignation for  : " + employeeId);
		session = sessionFactory.openSession();
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastWorkingDate);
		EmployeeLeaveBalance empLeaveBal = employeeLeavesDAO.getEmpLeaveBalance(String.valueOf(cal.get(Calendar.YEAR)), employeeId, session);
		Employee emp = employeeDAO.loadById(employeeId, session);
		if(empLeaveBal != null){
			int[] LEAVE_EARNED = setLeaveEarned(empLeaveBal);
			int month = cal.get(Calendar.MONTH);
			int leaveBal = LEAVE_EARNED[month];
			if(cal.get(Calendar.DATE) < 15){
				leaveBal = leaveBal -8;
			}
			
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(emp.getJoinDate());
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(lastWorkingDate);
			//Check if the employee resigned within a year, if so set the leave balance as 0...
			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
			if(diffMonth <= 12 && leaveBal > 0){
				leaveBal = 0;
			}
			for(int i=month; i<12;i++){
				LEAVE_EARNED[i] = leaveBal;
			}
			empLeaveBal = setEmployeeLeaveBalanceByMonth(empLeaveBal, LEAVE_EARNED);	
		}
		return empLeaveBal;
	}

}