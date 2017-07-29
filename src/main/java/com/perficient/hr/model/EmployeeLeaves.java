package com.perficient.hr.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "employee_leaves")
public class EmployeeLeaves extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "applied_by_pk")
	private Long appliedById;
	
	@Column(name = "request_type")
	private String requestType;
	
	@Column(name = "request_title")
	private String title;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "dt_from")
	private Date startsAt;
	
	@Column(name = "dt_from_half")
	private String dtFromHalf;
	
	@Column(name = "dt_End")
	private Date endsAt;
	
	@Column(name = "dt_end_half")
	private String dtEndHalf;
	
	@Column(name = "hours")
	private int hours;
	
	@Column(name = "mail_sequence")
	private int mailSequence;
	
	@Transient
	private String type;

	@Transient
	private String empId;
	
	@Transient
	private boolean isChangeApprover = false;
	
	@Transient
	private List<EmployeeNamesView> notificationToList;
	
	@Transient
	private List<Long> employeeReportList;

	@Transient
	private String status;
	
	@Transient
	private String cssClass;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "employee_pk", insertable=false, updatable=false)
	private EmployeeNamesView employeeNamesView;
	
	@JsonIgnore
	@Transient
	private Map<Integer, Integer> leaveHoursMonth;
	
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
	 * @return the employeeId
	 */
	public Long getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the startsAt
	 */
	public Date getStartsAt() {
		return startsAt;
	}

	/**
	 * @param startsAt the startsAt to set
	 */
	public void setStartsAt(Date startsAt) {
		this.startsAt = startsAt;
	}

	/**
	 * @return the dtFromHalf
	 */
	public String getDtFromHalf() {
		return dtFromHalf;
	}

	/**
	 * @param dtFromHalf the dtFromHalf to set
	 */
	public void setDtFromHalf(String dtFromHalf) {
		this.dtFromHalf = dtFromHalf;
	}

	/**
	 * @return the endsAt
	 */
	public Date getEndsAt() {
		return endsAt;
	}

	/**
	 * @param endsAt the endsAt to set
	 */
	public void setEndsAt(Date endsAt) {
		this.endsAt = endsAt;
	}

	/**
	 * @return the dtEndHalf
	 */
	public String getDtEndHalf() {
		return dtEndHalf;
	}

	/**
	 * @param dtEndHalf the dtEndHalf to set
	 */
	public void setDtEndHalf(String dtEndHalf) {
		this.dtEndHalf = dtEndHalf;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the appliedById
	 */
	public Long getAppliedById() {
		return appliedById;
	}

	/**
	 * @param appliedById the appliedById to set
	 */
	public void setAppliedById(Long appliedById) {
		this.appliedById = appliedById;
	}

	/**
	 * @return the notificationToList
	 */
	public List<EmployeeNamesView> getNotificationToList() {
		return notificationToList;
	}

	/**
	 * @param notificationToList the notificationToList to set
	 */
	public void setNotificationToList(List<EmployeeNamesView> notificationToList) {
		this.notificationToList = notificationToList;
	}

	/**
	 * @return the employeeReportList
	 */
	public List<Long> getEmployeeReportList() {
		return employeeReportList;
	}

	/**
	 * @param employeeReportList the employeeReportList to set
	 */
	public void setEmployeeReportList(List<Long> employeeReportList) {
		this.employeeReportList = employeeReportList;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		return cssClass;
	}

	/**
	 * @param cssClass the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @return the employeeNamesView
	 */
	public EmployeeNamesView getEmployeeNamesView() {
		return employeeNamesView;
	}

	/**
	 * @param employeeNamesView the employeeNamesView to set
	 */
	public void setEmployeeNamesView(EmployeeNamesView employeeNamesView) {
		this.employeeNamesView = employeeNamesView;
	}

	/**
	 * @return the leaveHoursMonth
	 */
	public Map<Integer, Integer> getLeaveHoursMonth() {
		return leaveHoursMonth;
	}

	/**
	 * @param leaveHoursMonth the leaveHoursMonth to set
	 */
	public void setLeaveHoursMonth(Map<Integer, Integer> leaveHoursMonth) {
		this.leaveHoursMonth = leaveHoursMonth;
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
	 * @return the isChangeApprover
	 */
	public boolean isChangeApprover() {
		return isChangeApprover;
	}

	/**
	 * @param isChangeApprover the isChangeApprover to set
	 */
	public void setChangeApprover(boolean isChangeApprover) {
		this.isChangeApprover = isChangeApprover;
	}

}
