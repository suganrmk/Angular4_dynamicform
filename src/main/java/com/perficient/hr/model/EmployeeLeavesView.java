package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_employee_leaves_cosolidated")
public class EmployeeLeavesView {
	
	@Id
	@GeneratedValue
	@Column(name = "employee_leaves_pk", length = 11 )
	private Long employeeLeavesId;
	
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "hours")
	private int hours;
	
	@Column(name = "days_count")
	private int daysCount;

	/**
	 * @return the employeeLeavesId
	 */
	public Long getEmployeeLeavesId() {
		return employeeLeavesId;
	}

	/**
	 * @param employeeLeavesId the employeeLeavesId to set
	 */
	public void setEmployeeLeavesId(Long employeeLeavesId) {
		this.employeeLeavesId = employeeLeavesId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the daysCount
	 */
	public int getDaysCount() {
		return daysCount;
	}

	/**
	 * @param daysCount the daysCount to set
	 */
	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}
	
}
