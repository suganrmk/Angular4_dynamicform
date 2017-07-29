package com.perficient.hr.form;

public class EmployeeCarriedLeaves {

	private Integer leavesTakenHours;
	
	private int employeeId;
	
	private Integer leaveYear;
	
	private Integer carriedLeaveHours;
	
	private Integer leaveBalanceHours;
	
	private Integer leaveMaintainanceId;

	/**
	 * @return the carriedLeaveHours
	 */
	public Integer getCarriedLeaveHours() {
		return carriedLeaveHours;
	}

	/**
	 * @param carriedLeaveHours the carriedLeaveHours to set
	 */
	public void setCarriedLeaveHours(Integer carriedLeaveHours) {
		this.carriedLeaveHours = carriedLeaveHours;
	}

	/**
	 * @return the leaveYear
	 */
	public Integer getLeaveYear() {
		return leaveYear;
	}

	/**
	 * @param leaveYear the leaveYear to set
	 */
	public void setLeaveYear(Integer leaveYear) {
		this.leaveYear = leaveYear;
	}

	/**
	 * @return the leaveBalanceHours
	 */
	public Integer getLeaveBalanceHours() {
		return leaveBalanceHours;
	}

	/**
	 * @param leaveBalanceHours the leaveBalanceHours to set
	 */
	public void setLeaveBalanceHours(Integer leaveBalanceHours) {
		this.leaveBalanceHours = leaveBalanceHours;
	}

	/**
	 * @return the leavesTakenHours
	 */
	public Integer getLeavesTakenHours() {
		return leavesTakenHours;
	}

	/**
	 * @param leavesTakenHours the leavesTakenHours to set
	 */
	public void setLeavesTakenHours(Integer leavesTakenHours) {
		this.leavesTakenHours = leavesTakenHours;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the leaveMaintainanceId
	 */
	public Integer getLeaveMaintainanceId() {
		return leaveMaintainanceId;
	}

	/**
	 * @param leaveMaintainanceId the leaveMaintainanceId to set
	 */
	public void setLeaveMaintainanceId(Integer leaveMaintainanceId) {
		this.leaveMaintainanceId = leaveMaintainanceId;
	}
	
}
