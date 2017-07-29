package com.perficient.hr.model.type;

public enum LeaveType {

	PTO("PTO"),
	UNPLANNED_PTO("UNPLANNED_PTO"),
	WFH("WFH"),
	PATERNITY_WFH("PATERNITY_WFH"),
	MATERNITY_WFH("MATERNITY_WFH"),
	SABATICAL("SABATICAL"),
	LOSS_OF_PAY("LOSS_OF_PAY"),
	MATERNITY_PAID_LEAVE("MATERNITY_PAID_LEAVE"),
	MATERNITY_UNPAID_LEAVE("MATERNITY_UNPAID_LEAVE"),
	COMPENSATORY_OFF("COMPENSATORY_OFF"),
	ALL_PTO("ALL_PTO"),
	ALL_WFH("ALL_WFH"),
	ONLY_PTO("ONLY_PTO"),
	EARNED_LEAVES("EARNED_LEAVES"),
	NONEARNED_LEAVES("NONEARNED_LEAVES"),
	LOP_ADJUSTMENTS("LOP_ADJUSTMENTS");
	
	private String leavesType;
	
	LeaveType(String leavesType){
		this.leavesType = leavesType;
	}
	
	public String getLeaveType() {
        return leavesType;
    }
	
}
