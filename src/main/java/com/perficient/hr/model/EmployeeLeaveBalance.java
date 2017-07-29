package com.perficient.hr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_leave_balance")
@SuppressWarnings("serial")
public class EmployeeLeaveBalance extends AbstractModel implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "op_bal")
	private int op_bal;
	
	@Column(name = "jan")
	private int jan;
	
	@Column(name = "feb")
	private int feb;
	
	@Column(name = "mar")
	private int mar;
	
	@Column(name = "apr")
	private int apr;
	
	@Column(name = "may")
	private int may;
	
	@Column(name = "jun")
	private int jun;
	
	@Column(name = "jul")
	private int jul;
	
	@Column(name = "aug")
	private int aug;
	
	@Column(name = "sep")
	private int sep;
	
	@Column(name = "oct")
	private int oct;
	
	@Column(name = "nov")
	private int nov;
	
	//Dec is keyword in mysql
	@Column(name = "decem")
	private int dec;

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
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the jan
	 */
	public int getJan() {
		return jan;
	}

	/**
	 * @param jan the jan to set
	 */
	public void setJan(int jan) {
		this.jan = jan;
	}

	/**
	 * @return the feb
	 */
	public int getFeb() {
		return feb;
	}

	/**
	 * @param feb the feb to set
	 */
	public void setFeb(int feb) {
		this.feb = feb;
	}

	/**
	 * @return the mar
	 */
	public int getMar() {
		return mar;
	}

	/**
	 * @param mar the mar to set
	 */
	public void setMar(int mar) {
		this.mar = mar;
	}

	/**
	 * @return the apr
	 */
	public int getApr() {
		return apr;
	}

	/**
	 * @param apr the apr to set
	 */
	public void setApr(int apr) {
		this.apr = apr;
	}

	/**
	 * @return the may
	 */
	public int getMay() {
		return may;
	}

	/**
	 * @param may the may to set
	 */
	public void setMay(int may) {
		this.may = may;
	}

	/**
	 * @return the jun
	 */
	public int getJun() {
		return jun;
	}

	/**
	 * @param jun the jun to set
	 */
	public void setJun(int jun) {
		this.jun = jun;
	}

	/**
	 * @return the jul
	 */
	public int getJul() {
		return jul;
	}

	/**
	 * @param jul the jul to set
	 */
	public void setJul(int jul) {
		this.jul = jul;
	}

	/**
	 * @return the aug
	 */
	public int getAug() {
		return aug;
	}

	/**
	 * @param aug the aug to set
	 */
	public void setAug(int aug) {
		this.aug = aug;
	}

	/**
	 * @return the sep
	 */
	public int getSep() {
		return sep;
	}

	/**
	 * @param sep the sep to set
	 */
	public void setSep(int sep) {
		this.sep = sep;
	}

	/**
	 * @return the oct
	 */
	public int getOct() {
		return oct;
	}

	/**
	 * @param oct the oct to set
	 */
	public void setOct(int oct) {
		this.oct = oct;
	}

	/**
	 * @return the nov
	 */
	public int getNov() {
		return nov;
	}

	/**
	 * @param nov the nov to set
	 */
	public void setNov(int nov) {
		this.nov = nov;
	}

	/**
	 * @return the dec
	 */
	public int getDec() {
		return dec;
	}

	/**
	 * @param dec the dec to set
	 */
	public void setDec(int dec) {
		this.dec = dec;
	}

	/**
	 * @return the op_bal
	 */
	public int getOp_bal() {
		return op_bal;
	}

	/**
	 * @param op_bal the op_bal to set
	 */
	public void setOp_bal(int op_bal) {
		this.op_bal = op_bal;
	}

}
