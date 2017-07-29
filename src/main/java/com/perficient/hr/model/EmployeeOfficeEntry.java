package com.perficient.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_office_entry")
@SuppressWarnings("serial")
public class EmployeeOfficeEntry extends AbstractModel implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;

	@Column(name = "in_date")
	private Date inDate;

	
	@Column(name = "location")
	private String location;


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
	 * @return the inDate
	 */
	public Date getInDate() {
		return inDate;
	}


	/**
	 * @param inDate the inDate to set
	 */
	public void setInDate(Date inDate) {
		this.inDate = inDate;
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

}
