package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.NotificationDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.Notification;
import com.perficient.hr.model.type.NotificationType;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("notificationDAO")
public class NotificationDAOImpl implements NotificationDAO{

protected Logger logger = LoggerFactory.getLogger(NotificationDAOImpl.class);
	
	@Override
	public Notification loadNotificationId(String pk, Session session) throws GenericException {
		return (Notification)session.get(Notification.class, Long.parseLong(pk));
	}

	@Override
	public boolean saveNotification(Notification notification, Session session) throws GenericException {
		session.save(notification);
		return true;
	}

	@Override
	public boolean updateNotification(Notification notification, Session session)  {
		session.merge(notification);
		return true;
	}

	@Override
	public Object getNotificationCount(String employeeId, Session session) throws GenericException {
		String sqlQuery = "Select count(*), n.notificationType, n.notificationStatus from Notification n where n.active=:active AND n.flag=:flag and "
				+ " n.notificationTo=:notificationTo group by n.notificationStatus, n.notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.UNREAD);
//		query.setParameter("notificationStatus", PerfHrConstants.PENDING);
		query.setParameter("notificationTo", Long.parseLong(employeeId));
		return  query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> loadNotificationsByGenericId(long genericId, Session session) {
		String sqlQuery = " from Notification n WHERE n.active=:active AND n.idGeneric=:genericId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		List<Notification> notificationList = query.list();
		return notificationList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> loadNotificationsByGenericIdAndType(long genericId, String type, Session session) {
		String sqlQuery = " from Notification n WHERE n.active=:active AND n.idGeneric=:genericId and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("notificationType", type);
		List<Notification> notificationList = query.list();
		return notificationList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> loadNotificationsToByGenericId(long genericId, Session session) throws GenericException{
		String sqlQuery = "SELECT n.notificationTo from Notification n WHERE n.active=:active AND n.idGeneric=:genericId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		return query.list();
	}

	@Override
	public Notification loadByGenericAndEmployeeId(long genericId, long employeeId, boolean active, Session session) {
		String sqlQuery = "from Notification n WHERE n.active=:active AND n.idGeneric=:genericId AND n.notificationTo=:employeeId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, active);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("employeeId", employeeId);
		Notification notification = (Notification) query.uniqueResult();
		return notification;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeLeaves> loadNotificationsByType(String notificationType, String status, String employeeId, Session session) {
		String sqlQuery = "SELECT el, n from EmployeeLeaves el, Notification n where n.active=el.active and n.active=:active "
				+ " AND n.notificationTo=:notificationTo and n.notificationType=:notificationType  AND n.flag=:flag"
				+ " AND n.idGeneric=el.pk";
		if(!status.equalsIgnoreCase("SUMMARY")){
			sqlQuery += " and n.notificationStatus=:notificationStatus ";
		}
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		
		if(!status.equalsIgnoreCase("SUMMARY")){
			query.setParameter("notificationStatus", status);
			query.setParameter("flag", PerfHrConstants.UNREAD);
		} else {
			query.setParameter("flag", PerfHrConstants.READ);
		}
			
		query.setParameter("notificationTo", Long.parseLong(employeeId));
		query.setParameter("notificationType", notificationType);
		return  query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> loadNotificationMsgs(String employeeId, Session session) {
		String sqlQuery = "from Notification n where n.active=:active AND n.flag=:flag and "
				+ " n.notificationStatus=:notificationStatus AND n.notificationTo=:notificationTo and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.UNREAD);
		query.setParameter("notificationStatus", PerfHrConstants.PENDING);
		query.setParameter("notificationTo", Long.parseLong(employeeId));
		query.setParameter("notificationType", NotificationType.MESSAGE.getNotificationType());
		return  query.list();
	}
	
	@Override
	public Notification loadByIdGenericTypeAndEmployeeId(long genericId, long employeeId, String notificationType,
			Session session) {
		String sqlQuery = "from Notification n WHERE n.active=:active AND n.idGeneric=:genericId AND n.notificationTo=:employeeId and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("employeeId", employeeId);
		query.setParameter("notificationType", notificationType);
		Notification notification = (Notification) query.uniqueResult();
		return notification;
	}

	@Override
	public Notification loadByIdGenericTypeAndStatus(long genericId, String notificationStatus, String notificationType,
			Session session) {
		String sqlQuery = "from Notification n WHERE n.active=:active AND n.idGeneric=:genericId AND n.notificationStatus=:notificationStatus and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("notificationStatus", notificationStatus);
		query.setParameter("notificationType", notificationType);
		Notification notification = (Notification) query.uniqueResult();
		return notification;
	}

	@Override
	public boolean updateNotificationByGenericIdAndType(Long genericId, String type, String status, Session session) {
		String sqlQuery = "Update Notification n set n.notificationStatus=:notificationStatus, n.flag=:updFlag where n.active=:active AND n.idGeneric=:genericId and n.flag=:flag"
				+ " and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("notificationType", type);
		query.setParameter("notificationStatus", status);
		query.setParameter("flag", PerfHrConstants.READ);
		query.setParameter("updFlag", PerfHrConstants.UNREAD);
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean deleteNotificationByGenericIdAndType(Long genericId, String type, Session session) {
		String sqlQuery = "Update Notification n set n.active=:active, n.flag=:updFlag where n.idGeneric=:genericId"
				+ " and n.notificationType=:notificationType";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.INACTIVE);
		query.setParameter(PerfHrConstants.GENERIC_ID_COLUMN, genericId);
		query.setParameter("notificationType", type);
//		query.setParameter("flag", PerfHrConstants.READ);
		query.setParameter("updFlag", PerfHrConstants.READ);
		query.executeUpdate();
		return true;
	}

	
	
}