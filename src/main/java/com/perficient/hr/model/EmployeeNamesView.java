package com.perficient.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vw_employee_by_name")
@SuppressWarnings("serial")
public class EmployeeNamesView implements Serializable{

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "email")
	private String email;

	@Column(name = "supervisor")
	private Long supervisor;

	@JsonIgnore
	@Column(name = "flag")
	private boolean flag;
	
	@Column(name = "joindate")
	private Date joinDate;
	
	@JsonIgnore
	@Column(name = "last_working_Date")
	private Date lastWorkDate;
	
	@Column(name = "designation")
	private Long designation;
	
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
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the supervisor
	 */
	public Long getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(Long supervisor) {
		this.supervisor = supervisor;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/**
	 * @return the lastWorkDate
	 */
	public Date getLastWorkDate() {
		return lastWorkDate;
	}

	/**
	 * @param lastWorkDate the lastWorkDate to set
	 */
	public void setLastWorkDate(Date lastWorkDate) {
		this.lastWorkDate = lastWorkDate;
	}

	/**
	 * @return the designation
	 */
	public Long getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(Long designation) {
		this.designation = designation;
	}

}
