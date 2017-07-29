package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.perficient.hr.utils.PerfHrConstants;

@Entity
@Table(name = "email_track")
@SuppressWarnings("serial")
public class EmailTrack extends AbstractModel{
	
	public EmailTrack() {
		this.mailFrom = PerfHrConstants.EMAIL_FROM;
	}

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "id_generic")
	private Long idGeneric;
	
	@Column(name = "mail_type")
	private String mailType;
		
	@Column(name = "mail_to")
	private String mailTo;
	
	@Column(name = "mail_from")
	private String mailFrom;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "mail_cc")
	private String mailCC;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "media_type")
	private String mediaType;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "flag")
	private int flag;
	
	@Column(name = "mail_status")
	private String mailStatus;
	
	@Column(name = "from_date")
	private Date fromDate;
	
	@Column(name = "to_date")
	private Date toDate;
	
	@Column(name = "uid")
	private String uid;
	
	@Column(name = "mail_sequence")
	private int mailSequence;
	
	@Column(name = "attachment")
	private String attachment;
	
	@Column(name = "request_type")
	private String requestType;
	
	
	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

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

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMailCC() {
		return mailCC;
	}

	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the mailSequence
	 */
	public int getMailSequence() {
		return mailSequence;
	}

	/**
	 * @param mailSequence the mailSequence to set
	 */
	public void setMailSequence(int mailSequence) {
		this.mailSequence = mailSequence;
	}

	/**
	 * @return the attachment
	 */
	public String getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
}
