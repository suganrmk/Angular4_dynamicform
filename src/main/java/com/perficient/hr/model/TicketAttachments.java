package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_attachments")
@SuppressWarnings("serial")
public class TicketAttachments extends AbstractModel{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "ticket_pk")
	private Long ticketId;
	
	@Column(name = "filename")
	private String fileName;
	
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
