package com.perficient.hr.service.impl;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EbsEmployeeLeavesDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.EbsEmployeeLeaves;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.service.EbsEmployeeLeaveService;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("ebsEmployeeLeavesService")
public class EbsEmployeeLeaveServiceImpl implements EbsEmployeeLeaveService{

	protected Logger logger = LoggerFactory.getLogger(EmployeeLeavesServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    EbsEmployeeLeavesDAO ebsEmployeeLeavesDAO;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
    
	@Override
	public Object parseDocument(String fileName, String startYear, String totalLeaves) {
		LoggerUtil.infoLog(logger, "Document Parsing. File :" + fileName );
		Session session = null;
		Transaction tx = null;
		try(FileInputStream fis = new FileInputStream(fileName);) {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fis);
	        int numberOfSheets = workbook.getNumberOfSheets();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	        int hoursCol=0, empIdCol=0, leaveTypeCol=0, contractorLeaveTypeCol=0, commentCol=0, titleCol=0, monthCol=0, dateCol = 0;
	        for(int i=0; i < numberOfSheets; i++){
	        	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
	        	Iterator<Row> rowIterator = sheet.iterator();
	        	Calendar cal = Calendar.getInstance();
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
	                try{
	                	hours = Math.round(Float.parseFloat(row.getCell(33).toString()));	
	                } catch (Exception e) {
						// TODO: handle exception
					}
	                String empId = row.getCell(empIdCol).toString();
	                Employee employee = employeeDAO.loadByEmployeeId(empId, session);
	                if(employee != null && hours != 0){
	                	EbsEmployeeLeaves employeeLeaves = new EbsEmployeeLeaves();
	                	String leaveType = row.getCell(leaveTypeCol).toString().toUpperCase().replace(" ", "_");
	                	if(leaveType.equalsIgnoreCase("Personal_Leave")){
	                		employeeLeaves.setRequestType(LeaveType.LOSS_OF_PAY.getLeaveType());
	                	} else if(leaveType.equalsIgnoreCase("Maternity_Paid")){
	                		employeeLeaves.setRequestType(LeaveType.MATERNITY_PAID_LEAVE.getLeaveType());
	                	} else if(leaveType.equalsIgnoreCase("Maternity_Unpaid")){
	                		employeeLeaves.setRequestType(LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType());
	                	} else if(leaveType.length() == 0 && 
	                			(row.getCell(contractorLeaveTypeCol).toString().equals("Labor-PTO Unplanned") 
	                					|| row.getCell(contractorLeaveTypeCol).toString().equals("Labor-PTO"))){
	                		employeeLeaves.setRequestType(LeaveType.LOSS_OF_PAY.getLeaveType());
	                	} else {
	                		employeeLeaves.setRequestType(leaveType);
	                	}
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
	        			if(comments.length() >= 100)
	        				comments = comments.substring(0, 99);
	            		employeeLeaves.setComments(comments);
	            		employeeLeaves.setTitle(row.getCell(titleCol).toString()+" - "+leaveType);
	            		Date dt = sdf.parse("01-"+row.getCell(monthCol).toString());
	            		cal.setTime(dt);
	            		logger.info(row.getCell(monthCol).toString() + " Date " + row.getCell(dateCol).toString() + " month "
								+ (cal.get(Calendar.MONTH) + 1));
	            		Date leaveDate = DateUtils.getDate(row.getCell(dateCol).toString(), (cal.get(Calendar.MONTH)+1));
	            		employeeLeaves.setLeaveDt(leaveDate);
	            		cal.setTime(leaveDate);
	            		cal.set(Calendar.HOUR, 9);
	            		cal.set(Calendar.MINUTE, 30);
	            		cal.set(Calendar.AM_PM, Calendar.AM);
	            		employeeLeaves.setStartsAt(cal.getTime());
	            		
	            		employeeLeaves.setHours((hours <= 4) ? 4:8);
	            		employeeLeaves.setDtFromHalf(PerfHrConstants.FIRST_HALF);
	            		if(employeeLeaves.getHours() == 4){
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
            			ebsEmployeeLeavesDAO.saveEbsLeave(employeeLeaves, session);
	                }
	            }
	        }
	        fis.close();
	        tx.commit();
		} catch(Exception e){
			logger.info("Duplicate Date entry: "+ e.getCause());
			LoggerUtil.errorLog(logger, "Unable to import PTO document." , e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to import PTO document ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}

	@Override
	public Object loadEbsLeaveReport(EmployeeLeaves employeeLeaves) {
		LoggerUtil.infoLog(logger, "Load loadEbsLeaveReport: "+employeeLeaves.getEmployeeId());
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return ebsEmployeeLeavesDAO.ebsLeaveReport(employeeLeaves, session);
		} catch (Exception e){
			LoggerUtil.errorLog(logger, "Unable to load leave report for employee: '"+employeeLeaves.getEmployeeId() , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load leave report for employee: '"+employeeLeaves.getEmployeeId() , e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

}
