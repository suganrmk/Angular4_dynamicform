package com.perficient.hr.form;

import java.util.Date;
import java.util.List;

public class Reports {

	private Date startsAt;
	
	private Date endsAt;
	
	private List<Long> employeeReportList;
	
	private String type;

	/**
	 * @return the startsAt
	 */
	public Date getStartsAt() {
		return startsAt;
	}

	/**
	 * @param startsAt the startsAt to set
	 */
	public void setStartsAt(Date startsAt) {
		this.startsAt = startsAt;
	}

	/**
	 * @return the endsAt
	 */
	public Date getEndsAt() {
		return endsAt;
	}

	/**
	 * @param endsAt the endsAt to set
	 */
	public void setEndsAt(Date endsAt) {
		this.endsAt = endsAt;
	}

	/**
	 * @return the employeeReportList
	 */
	public List<Long> getEmployeeReportList() {
		return employeeReportList;
	}

	/**
	 * @param employeeReportList the employeeReportList to set
	 */
	public void setEmployeeReportList(List<Long> employeeReportList) {
		this.employeeReportList = employeeReportList;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
