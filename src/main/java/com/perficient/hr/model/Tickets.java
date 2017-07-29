package com.perficient.hr.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@SuppressWarnings("serial")
public class Tickets extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11)
	private Long pk;
	
	@Column(name = "ticket_no")
	private String ticketNo;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "issueType")
	private String issueType;
	
	@Column(name = "description")
	private String description;

	@Column(name = "comments")
	private String comments;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "assigned_to")
	private Long assignedTo;
	
	@Column(name = "closed_on")
	private Date closedOn;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "ticketId")
    private Set<TicketAttachments> ticketAttachments;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "ticketId")
    private Set<TicketComments> ticketComments;
	
	@ManyToOne
	@JoinColumn(name = "assigned_to", insertable=false, updatable=false)
	private EmployeeNamesView employeeNamesView;
	
	@ManyToOne
	@JoinColumn(name = "created_by", insertable=false, updatable=false)
	private EmployeeNamesView createdByView;

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
	 * @return the ticketNo
	 */
	public String getTicketNo() {
		return ticketNo;
	}

	/**
	 * @param ticketNo the ticketNo to set
	 */
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the issueType
	 */
	public String getIssueType() {
		return issueType;
	}

	/**
	 * @param issueType the issueType to set
	 */
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
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
	 * @return the assignedTo
	 */
	public Long getAssignedTo() {
		return assignedTo;
	}

	/**
	 * @param assignedTo the assignedTo to set
	 */
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @return the closedOn
	 */
	public Date getClosedOn() {
		return closedOn;
	}

	/**
	 * @param closedOn the closedOn to set
	 */
	public void setClosedOn(Date closedOn) {
		this.closedOn = closedOn;
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
	 * @return the createdByView
	 */
	public EmployeeNamesView getCreatedByView() {
		return createdByView;
	}

	/**
	 * @param createdByView the createdByView to set
	 */
	public void setCreatedByView(EmployeeNamesView createdByView) {
		this.createdByView = createdByView;
	}
	
	/**
	 * @return the ticketAttachments
	 */
	public Set<TicketAttachments> getTicketAttachments() {
		return ticketAttachments;
	}

	/**
	 * @param ticketAttachments the ticketAttachments to set
	 */
	public void setTicketAttachments(Set<TicketAttachments> ticketAttachments) {
		this.ticketAttachments = ticketAttachments;
	}

	/**
	 * @return the ticketComments
	 */
	public Set<TicketComments> getTicketComments() {
		return ticketComments;
	}

	/**
	 * @param ticketComments the ticketComments to set
	 */
	public void setTicketComments(Set<TicketComments> ticketComments) {
		this.ticketComments = ticketComments;
	}

}
