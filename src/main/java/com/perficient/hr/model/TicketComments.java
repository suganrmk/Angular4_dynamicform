package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_comments")
@SuppressWarnings("serial")
public class TicketComments extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "ticket_pk")
	private Long ticketId;
	
	@Column(name = "comment")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "created_by", insertable=false, updatable=false)
	private EmployeeNamesView employeeNamesView;

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
	 * @return the ticketId
	 */
	public Long getTicketId() {
		return ticketId;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	
}
