package com.perficient.hr.model.type;

public enum ReportStatusType {

	All("All"),
	Open("Open"),
	Closed("Closed"),
	InProgress("InProgress");
	
	private String reportStatusType;
	
	ReportStatusType(String reportStatusType){
		this.reportStatusType = reportStatusType;
	}
	
	public String getReportStatusType() {
        return reportStatusType;
    }
	
}
