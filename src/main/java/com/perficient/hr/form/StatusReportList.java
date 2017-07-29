/**
 * 
 */
package com.perficient.hr.form;

import java.util.List;

/**
 * @author praveen.muthusamy
 *
 */
public class StatusReportList {

	private List<StatusReport> reportList;
	
	private Status status;

	/**
	 * @return the reportList
	 */
	public List<StatusReport> getReportList() {
		return reportList;
	}

	/**
	 * @param reportList the reportList to set
	 */
	public void setReportList(List<StatusReport> reportList) {
		this.reportList = reportList;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
