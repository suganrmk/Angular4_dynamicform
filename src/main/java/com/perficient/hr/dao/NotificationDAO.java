package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;

public interface NotificationDAO {

	public Notification loadNotificationId(String pk, Session session) throws GenericException;
	
	public Object getNotificationCount(String employeeId, Session session) throws GenericException;
	
	public List<Notification> loadNotificationsByGenericId(long genericId, Session session);
	
	public List<Notification> loadNotificationsByGenericIdAndType(long genericId,  String type, Session session);
	
	public List<Long> loadNotificationsToByGenericId(long genericId, Session session) throws GenericException;
	
	public boolean saveNotification(Notification notification, Session session) throws GenericException;
	
	public boolean updateNotification(Notification notification, Session session);
	
	public boolean deleteNotificationByGenericIdAndType(Long genericId, String type, Session session);
	
	public boolean updateNotificationByGenericIdAndType(Long genericId, String type, String status, Session session);
	
	public Notification loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active, Session session);
	
	public Notification loadByIdGenericTypeAndEmployeeId(long genericId, long employeeId, String notificationType, Session session);
	
	public Notification loadByIdGenericTypeAndStatus(long genericId, String notificationStatus, String notificationType, Session session);
	
	public List<EmployeeLeaves> loadNotificationsByType(String type, String status, String employeeId, Session session);
	
	public List<Notification> loadNotificationMsgs(String employeeId, Session session);
	
}
