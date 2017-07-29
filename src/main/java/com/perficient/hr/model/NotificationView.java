package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification_view")
public class NotificationView {

	@Id
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "id_generic")
	private Long idGeneric;
	
	@Column(name = "notification_type")
	private String notificationType;
	
	@Column(name = "notification_to")
	private String notificationTo;

	/**
	 * @return the idGeneric
	 */
	public Long getIdGeneric() {
		return idGeneric;
	}

	/**
	 * @param idGeneric the idGeneric to set
	 */
	public void setIdGeneric(Long idGeneric) {
		this.idGeneric = idGeneric;
	}

	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the notificationTo
	 */
	public String getNotificationTo() {
		return notificationTo;
	}

	/**
	 * @param notificationTo the notificationTo to set
	 */
	public void setNotificationTo(String notificationTo) {
		this.notificationTo = notificationTo;
	}
	
}
