package com.perficient.hr.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the finance_transaction database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "finance_transaction")
public class FinanceTransaction extends AbstractModel {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11)
	private Long pk;

	@Column(name = "employee_pk")
	private Long employeeId;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "financial_status")
	private String financialStatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_pk", insertable = false, updatable = false)
	private EmployeeNamesView employeeNamesView;

	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk
	 *            the pk to set
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
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the financialYear
	 */
	public String getFinancialYear() {
		return financialYear;
	}

	/**
	 * @param financialYear
	 *            the financialYear to set
	 */
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	/**
	 * @return the financialStatus
	 */
	public String getFinancialStatus() {
		return financialStatus;
	}

	/**
	 * @param financialStatus
	 *            the financialStatus to set
	 */
	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}

	/**
	 * @return the employeeNamesView
	 */
	public EmployeeNamesView getEmployeeNamesView() {
		return employeeNamesView;
	}

	/**
	 * @param employeeNamesView
	 *            the employeeNamesView to set
	 */
	public void setEmployeeNamesView(EmployeeNamesView employeeNamesView) {
		this.employeeNamesView = employeeNamesView;
	}

}