package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_workexperience")
@SuppressWarnings("serial")
public class EmployeeWorkExperience extends AbstractModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;

	@Column(name = "organization_name")
	private String organizationName;

	@Column(name = "organization_address")
	private String organizationAddress;
	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "startDate")
	private Date startDate;
	
	@Column(name = "endDate")
	private Date endDate;
	
	@Column(name = "leavingReason")
	private String leavingReason;
	
	@Column(name = "contactPerson")
	private String contactPerson;
	
	@Column(name = "contactNumber")
	private String contactNumber;
	
	@Column(name = "gapDays")
	private int gapDays;
	
	@Column(name = "gapReason")
	private String gapReason;

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
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the organizationAddress
	 */
	public String getOrganizationAddress() {
		return organizationAddress;
	}

	/**
	 * @param organizationAddress the organizationAddress to set
	 */
	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	 * @return the leavingReason
	 */
	public String getLeavingReason() {
		return leavingReason;
	}

	/**
	 * @param leavingReason the leavingReason to set
	 */
	public void setLeavingReason(String leavingReason) {
		this.leavingReason = leavingReason;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the gapDays
	 */
	public int getGapDays() {
		return gapDays;
	}

	/**
	 * @param gapDays the gapDays to set
	 */
	public void setGapDays(int gapDays) {
		this.gapDays = gapDays;
	}

	/**
	 * @return the gapReason
	 */
	public String getGapReason() {
		return gapReason;
	}

	/**
	 * @param gapReason the gapReason to set
	 */
	public void setGapReason(String gapReason) {
		this.gapReason = gapReason;
	}
	
}
