package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_backofficeinfo")
@SuppressWarnings("serial")
public class EmployeeBackOfficeInfo extends AbstractModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11)
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "offered_grosssalary")
	private Double offeredGrosssalary;
	
	@Column(name = "offeredloyalty")
	private Double offeredloyalty;
	
	@Column(name = "hired_for")
	private String hiredFor;
	
	@Column(name = "current_location")
	private String currentLocation;
	
	@Column(name = "work_location")
	private String workLocation;

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
	 * @return the offeredGrosssalary
	 */
	public Double getOfferedGrosssalary() {
		return offeredGrosssalary;
	}

	/**
	 * @param offeredGrosssalary the offeredGrosssalary to set
	 */
	public void setOfferedGrosssalary(Double offeredGrosssalary) {
		this.offeredGrosssalary = offeredGrosssalary;
	}

	/**
	 * @return the offeredloyalty
	 */
	public Double getOfferedloyalty() {
		return offeredloyalty;
	}

	/**
	 * @param offeredloyalty the offeredloyalty to set
	 */
	public void setOfferedloyalty(Double offeredloyalty) {
		this.offeredloyalty = offeredloyalty;
	}
	
	/**
	 * @return the hiredFor
	 */
	public String getHiredFor() {
		return hiredFor;
	}

	/**
	 * @param hiredFor the hiredFor to set
	 */
	public void setHiredFor(String hiredFor) {
		this.hiredFor = hiredFor;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the workLocation
	 */
	public String getWorkLocation() {
		return workLocation;
	}

	/**
	 * @param workLocation the workLocation to set
	 */
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	
	
	
}
