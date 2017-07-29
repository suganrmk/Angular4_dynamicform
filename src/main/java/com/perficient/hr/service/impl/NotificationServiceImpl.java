package com.perficient.hr.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.EmployeeLeavesDAO;
import com.perficient.hr.dao.NotificationDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.model.type.NotificationStatusType;
import com.perficient.hr.service.NotificationService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;

@Repository("notificationService")
public class NotificationServiceImpl implements NotificationService{

protected Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
	
	@Autowired
	NotificationDAO notificationDAO;
	
	@Autowired
	EmployeeLeavesDAO employeeLeavesDAO;
	
	@Autowired
	EmailTrackDAO emailTrackDAO;
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Autowired
	public PerfProperties perfProperties;

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
   
    protected Session getSession(){
       return sessionFactory.openSession();
    }
	
    @Override
	public Object getNotificationCount(String employeeId) {
    	LoggerUtil.infoLog(logger, "Service to get Notification Count for the Employee " + employeeId);
    	Session session = null;
		try{
			session = sessionFactory.openSession();
			return notificationDAO.getNotificationCount(employeeId, session);
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to get Notification Count for the Employee " + employeeId, e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get Notification Count for the Employee " + employeeId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
    
    @Override
	public Object saveNotification(Notification notification) {
    	LoggerUtil.infoLog(logger, "Service to save Notification is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object returnVal = saveNotification(notification, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to save Notification is completed");
			return returnVal;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to save notification: "+notification.getPk() , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save Notification Count for the Employee ", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object saveNotification(Notification notification, Session session) throws GenericException {
		notificationDAO.saveNotification(notification, session);
		return true;
	}

	@Override
	public Object updateNotification(Notification notification) {
		LoggerUtil.infoLog(logger, "Service to update Notification is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object returnVal = updateNotification(notification, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to update Notification is completed");
			return returnVal;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update notification: "+notification.getPk() , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update Notification Count for the Employee ", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object updateNotification(Notification notification, Session session) {
		return notificationDAO.updateNotification(notification, session);
	}
	
	@Override
	public Object loadNotificationsByGenericId(long genericId) {
		LoggerUtil.infoLog(logger, "Service to Load Notification by Generic ID is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object notificationList = notificationDAO.loadNotificationsByGenericId(genericId, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to Load Notification by Generic ID is completed");
			return notificationList;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to load notification By Generic ID : "+ genericId, e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load notification By Generic ID : "+ genericId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadNotificationsToByGenericId(long genericId) {
		LoggerUtil.infoLog(logger, "Service to Load Notifications To by Generic ID is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object notificationList = loadNotificationsToByGenericId(genericId, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to Load Notifications To by Generic ID is completed");
			return notificationList;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to load notification By Generic ID : "+ genericId, e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load notification By Generic ID : "+ genericId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public List<Long> loadNotificationsToByGenericId(long genericId, Session session) throws GenericException {
		return notificationDAO.loadNotificationsToByGenericId(genericId, session);
	}

	@Override
	public Object loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active) {
		LoggerUtil.infoLog(logger, "Service to Load Notifications by Generic & Employee ID is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object notificationList = loadByGenericAndEmployeeId(genericId, employeeId, active, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to Load Notifications by Generic & Employee ID is completed");
			return notificationList;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to load notification By Generic ID : " + genericId +" & Employee ID :"+ employeeId, e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load notification By Generic ID : " + genericId +" & Employee ID :"+ employeeId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	public Notification loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active, Session session){
		return notificationDAO.loadByGenericAndEmployeeId(genericId, employeeId, active, session);
	}

	@Override
	public Object loadNotificationsByType(String type, String status, String employeeId) {
		LoggerUtil.infoLog(logger, "Load loadNotificationsByType");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			if(type.equals(LeaveType.PTO.getLeaveType()))
				type = LeaveType.PTO.getLeaveType();
			else if(type.equals(LeaveType.WFH.getLeaveType()))
				type = LeaveType.WFH.getLeaveType();
			return  notificationDAO.loadNotificationsByType(type, status, employeeId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to load loadNotificationsByType and the exception is ",e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load loadNotificationsByType", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object loadNotificationMsgs(String employeeId) {
		LoggerUtil.infoLog(logger, "Start loadNotificationMsgs");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return  notificationDAO.loadNotificationMsgs(employeeId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to process loadNotificationMsgs and the exception is ",e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to process loadNotificationMsgs", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object approveRejectNotification(String pk, String status, String comments,  String employeeId) {
		LoggerUtil.infoLog(logger, "Service to approve Notification is started");
		Session session = null;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			/*Update notification by Supervisor*/
			Notification notification = notificationDAO.loadNotificationId(pk, session);
			notification.setNotificationStatus(status);
			notification.setDtModified(new Date());
			notification.setFlag(PerfHrConstants.READ);
			notification.setComments(comments);
			notification.setModifiedBy(Long.parseLong(employeeId));
			updateNotification(notification, session);
			
			/*Load owner of the Notification*/
			EmployeeLeaves employeeLeaves = employeeLeavesDAO.loadLeaveById(notification.getIdGeneric().toString(), session);
			
			/*Load all notifications to update the status*/
			List<Notification> notificationList = notificationDAO.loadNotificationsByGenericIdAndType(notification.getIdGeneric(), notification.getNotificationType(), session);
			for(Notification notify : notificationList){
				//Skip supervisor as the record is updated already
				if(!notify.getNotificationTo().equals(Long.parseLong(employeeId))){
					//Check for owner record
					if(employeeLeaves.getEmployeeId().equals(notify.getNotificationTo())
							&& status.equalsIgnoreCase(NotificationStatusType.REJECTED.getNotificationStatusType())){
						notify.setFlag(PerfHrConstants.UNREAD);
					}
					notify.setComments(comments);
					notify.setNotificationStatus(status);
					notify.setDtModified(new Date());
					notify.setModifiedBy(Long.parseLong(employeeId));
					updateNotification(notify, session);
				}
			}
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to approve Notification is completed");
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to approve notification: "+pk , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to approve Notification", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object markReadNotification(String pk, String employeeId) {
		LoggerUtil.infoLog(logger, "Service to Mark Read Notification is started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Notification notification = notificationDAO.loadNotificationId(pk, session);
			notification.setFlag(PerfHrConstants.READ);
			notification.setDtModified(new Date());
			notification.setModifiedBy(Long.parseLong(employeeId));
			updateNotification(notification, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to Mark Read Notification is completed");
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Mark Read notification: "+pk , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Mark Read Notification", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object delegateNotification(String pk, String delegateEmpId, String employeeId) {
		LoggerUtil.infoLog(logger, "Service to Delegate Notification is started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Notification notification = notificationDAO.loadNotificationId(pk, session);
			if(!notification.getNotificationTo().equals(Long.parseLong(employeeId))){
				throw new PrftException();
			}
			//check if the delegate to employee is already exists
			Notification delegateTo = notificationDAO.loadByIdGenericTypeAndEmployeeId(notification.getIdGeneric(), 
					Long.parseLong(delegateEmpId), notification.getNotificationType(), session);
			if(delegateTo == null){
				//if the record not exists then update the current user record to delegate user
				notification.setNotificationTo(Long.parseLong(delegateEmpId));
				notification.setDtModified(new Date());
				notification.setModifiedBy(Long.parseLong(employeeId));
				updateNotification(notification, session);
			} else {
				// if the delegate user exists in notification
				notification.setActive(PerfHrConstants.INACTIVE);
				updateNotification(notification, session);
				delegateTo.setFlag(PerfHrConstants.UNREAD);
				delegateTo.setDtModified(new Date());
				delegateTo.setModifiedBy(Long.parseLong(employeeId));
				updateNotification(delegateTo, session);
			}
			
			Employee fromEmployee = employeeDAO.loadById(employeeId, session);
			Employee toEmployee = employeeDAO.loadById(delegateEmpId, session);
			
			EmailTrack emailTrack = new EmailTrack();
			emailTrack.setIdGeneric(notification.getPk());
			emailTrack.setMailType(MailType.PTO_WFH.getMailType());
			emailTrack.setMailTo(toEmployee.getEmail());
			emailTrack.setMailFrom(fromEmployee.getEmail());
			String notificationType = notification.getNotificationType();
			if(!notificationType.equals(LeaveType.WFH.getLeaveType()))
				notificationType = "Leave";
			String emailTemplate = perfProperties.getEmailTemplate();
			emailTrack.setSubject(fromEmployee.getLastName()+", "+fromEmployee.getFirstName()+" delegated a "+notificationType+" request!!!");
			String emailTitle = perfProperties.getEmailTitle().replace("{{title}}", "Hi, "+toEmployee.getLastName()+", "+toEmployee.getFirstName()+"<br/>");
			EmployeeLeaves empLeaves = employeeLeavesDAO.loadLeaveById(notification.getIdGeneric().toString(), session);
			String body = "<div>";
			body += "<strong>"+fromEmployee.getLastName()+", "+fromEmployee.getFirstName()+"</strong> delegated a "+notificationType+" request.<br/><br/>";
			body += "<strong>Leave Details</strong>: <br/>";
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			body += "<strong>Subject</strong>: "+empLeaves.getTitle()+"<br/>"+"<strong>From</strong>: "+sdf.format(empLeaves.getStartsAt())+"<br/>"+"<strong>To</strong>: "+sdf.format(empLeaves.getEndsAt())+"<br/>"+"<strong>Comments</strong>:"+empLeaves.getComments()+"<br/><br/>";
			body +="</div>";
			emailTrack.setComments(emailTemplate.replace("{{title}}", emailTitle).replace("{{body}}", body).replace("{{footer}}", "Regards, <br/>"+fromEmployee.getLastName()+", "+fromEmployee.getFirstName()));
			emailTrack.setMediaType(MailMediaType.HTML.getMailMediaType());
			emailTrack.setMailStatus(PerfHrConstants.PENDING);
			emailTrack.setFlag(0);
			emailTrack.setActive(PerfHrConstants.ACTIVE);
			emailTrack.setCreatedBy(fromEmployee.getPk());
			emailTrack.setModifiedBy(fromEmployee.getPk());
			emailTrack.setDtCreated(new Date());
			emailTrack.setDtModified(new Date());
			emailTrackDAO.saveEmailTrack(emailTrack, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to Delegate Notification is completed");
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delegate notification: "+pk , e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delegate Notification", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
}