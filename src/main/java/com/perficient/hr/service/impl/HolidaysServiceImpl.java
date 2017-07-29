package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.dao.HolidaysDAO;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.Holidays;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.EmployeeLeavesService;
import com.perficient.hr.service.HolidaysService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;

@Repository("holidayService")
public class HolidaysServiceImpl  extends PrftDbObjectManager<Holidays> implements HolidaysService{

protected Logger logger = LoggerFactory.getLogger(HolidaysServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    HolidaysDAO holidaysDAO;
	
	@Autowired
	EmployeeLeavesService empLeaveService;
	
	@Autowired
	EmployeeLeavesDAO empLeaveDAO;
	
    @Override
	public Object loadHolidays() {
    	LoggerUtil.infoLog(logger, "Load Holidays List Service Started");
	    List<Holidays> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			list = holidaysDAO.loadHolidays(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Holidays List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Holidays List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object addHolidays(Holidays holidays, String userId) {
		LoggerUtil.infoLog(logger, "Add Holidays Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			holidays.setHolidayName(PerfUtils.capitalizeFully(holidays.getHolidayName()));
			holidays.setDtCreated(new Date());
			holidays.setDtModified(new Date());
			holidays.setCreatedBy(employee.getPk());
			holidays.setModifiedBy(employee.getPk());
			if(exists(holidays, "holidayDate", holidays.getHolidayDate(), null)){
				throw new RecordExistsException();
			} else if(empLeaveDAO.isLeaveApplied(holidays.getHolidayDate(), session)){
				throw new PrftException("Holiday can't be applied.");
			} else {
				holidaysDAO.addHoliday(holidays, session);
				tx.commit();
				return holidays;	
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Duplicate Holiday Date: "+holidays.getHolidayDate(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Holiday Date already exists: "+holidays.getHolidayDate(), HttpStatus.CONFLICT.value());
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(),HttpStatus.PRECONDITION_FAILED.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add Holidays: "+holidays.getHolidayName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add Holidays: "+holidays.getHolidayName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object loadHolidaysById(String HolidaysId) {
		LoggerUtil.infoLog(logger, "Load Holidays By Id Service Started. HolidaysId: " + HolidaysId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return session.get(Holidays.class, Long.parseLong(HolidaysId));
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Holidays By Id : " + HolidaysId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Holidays By Id : " + HolidaysId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object updateHolidays(Holidays holidays, String userId) {
		LoggerUtil.infoLog(logger, "Update Holidays Service Started");
		Session session = null ;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			holidays.setHolidayName(PerfUtils.capitalizeFully(holidays.getHolidayName()));
			if(exists(holidays, "holidayDate", holidays.getHolidayDate(), holidays.getPk())){
				throw new RecordExistsException();
			} else if(empLeaveDAO.isLeaveApplied(holidays.getHolidayDate(), session)){
				throw new PrftException("Holiday can't be applied.");
			} else {
				updateHolidays(holidays, userId, session);
				//updateCarriedLeavesByHoliday(holidays, session);
				tx.commit();
				return true;	
			}
		} catch(RecordExistsException e){
			LoggerUtil.errorLog(logger, "Duplicate Holiday Date: "+holidays.getHolidayDate(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Holiday Date already exists for the same year!. ", HttpStatus.CONFLICT.value());
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(),HttpStatus.PRECONDITION_FAILED.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update Holidays: "+holidays.getHolidayName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update Holidays: "+holidays.getHolidayName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private boolean updateHolidays(Holidays holidays, String userId, Session session){
		holidays.setDtModified(new Date());
		holidays.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
		return holidaysDAO.updateHoliday(holidays, session);
	} 
	
	@Override
	public Object deleteHolidays(Holidays holidays, String userId) {
		LoggerUtil.infoLog(logger, "Delete Holidays Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			holidays.setActive(PerfHrConstants.INACTIVE);
			updateHolidays(holidays, userId, session);
			//updateCarriedLeavesByHoliday(holidays, session);
			tx.commit();
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delete Holidays: "+holidays.getHolidayName(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete Holidays: "+holidays.getHolidayName(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object getHolidaysByYear(String year) {
		LoggerUtil.infoLog(logger, "Load Holidays by year Service Started");
	    List<Holidays> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			list = holidaysDAO.getHolidaysByYear(year, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Holidays by year" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Holidays by year" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}
}