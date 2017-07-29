package com.perficient.hr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.service.EmailTrackService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository("emailTrackService")
public class EmailTrackServiceImpl implements EmailTrackService{

protected Logger logger = LoggerFactory.getLogger(EmailTrackServiceImpl.class);
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
	
	@Autowired
	EmailTrackDAO emailTrackDAO;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
    @Override
	public Object saveEmailTrack(EmailTrack emailTrack) {
    	LoggerUtil.infoLog(logger, "Service to save Notification is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			if(emailTrack.getMediaType().equals(MailMediaType.CALENDAR.getMailMediaType()) &&
				(emailTrack.getPriority() == null || emailTrack.getFromDate() == null
						|| emailTrack.getToDate() == null)){
				throw new PrftException("PRECONDITION FAILED");
			}
			emailTrack.setDtCreated(new Date());
			emailTrack.setDtModified(new Date());
			
			Object returnVal = saveEmailTrack(emailTrack, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to save Notification is completed");
			return returnVal;
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, e.getMessage(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject( e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to save notification: "+emailTrack.getPk() , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save Notification Count for the Employee ", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object saveEmailTrack(EmailTrack notification, Session session) throws GenericException {
		return emailTrackDAO.saveEmailTrack(notification, session);
	}

	@Override
	public Object updateEmailTrack(EmailTrack emailTrack) {
		LoggerUtil.infoLog(logger, "Service to update Notification is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object returnVal = updateEmailTrack(emailTrack, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to update Notification is completed");
			return returnVal;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update notification: "+emailTrack.getPk() , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update Notification Count for the Employee ", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object updateEmailTrack(EmailTrack emailTrack, Session session) {
		return emailTrackDAO.updateEmailTrack(emailTrack, session);
	}

	@Override
	public Object getEmailList() {
		LoggerUtil.infoLog(logger, "Loading all emails to be sent");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return  emailTrackDAO.getEmailList(session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load emails to be sent. Exception is ",e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load emails to be sent. Exception is", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getEmailListByTypeAndStatus(String status, String mailType) {
		LoggerUtil.infoLog(logger, "Loading all emails to be sent");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return  emailTrackDAO.getEmailListByTypeAndStatus(status, mailType, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load emails to be sent. Exception is ",e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load emails to be sent. Exception is", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
}
