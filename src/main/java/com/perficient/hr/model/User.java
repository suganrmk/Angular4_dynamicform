package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_login")
@SuppressWarnings("serial")
public class User  extends AbstractModel {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeePk;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "emp_lock")
	private boolean empLock;

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
	 * @return the employeePk
	 */
	public Long getEmployeePk() {
		return employeePk;
	}

	/**
	 * @param employeePk the employeePk to set
	 */
	public void setEmployeePk(Long employeePk) {
		this.employeePk = employeePk;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the empLock
	 */
	public boolean isEmpLock() {
		return empLock;
	}

	/**
	 * @param empLock the empLock to set
	 */
	public void setEmpLock(boolean empLock) {
		this.empLock = empLock;
	}

}