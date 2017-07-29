package com.perficient.hr.service;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.Notification;

public interface NotificationService {

	public Object getNotificationCount(String employeeId);
	
	public Object saveNotification(Notification notification);
	
	public Object saveNotification(Notification notification, Session session) throws GenericException;
	
	public Object updateNotification(Notification notification);
	
	public Object updateNotification(Notification notification, Session session);
	
	public Object loadNotificationsByGenericId(long genericId);
	
	public Object loadNotificationsToByGenericId(long genericId);
	
	public List<Long> loadNotificationsToByGenericId(long genericId, Session session) throws GenericException;
	
	public Object loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active) ;
	
	public Notification loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active, Session session) ;
	
	public Object loadNotificationsByType(String type, String status, String employeeId);
	
	public Object loadNotificationMsgs(String employeeId);
	
	public Object approveRejectNotification(String pk, String status, String comments, String employeeId);
	
	public Object markReadNotification(String pk, String employeeId);
	
	public Object delegateNotification(String pk, String delegateEmpId, String employeeId);
}
