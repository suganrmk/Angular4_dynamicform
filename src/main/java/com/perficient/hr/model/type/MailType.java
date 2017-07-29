package com.perficient.hr.model.type;

public enum MailType {

	PTO_WFH("PTO_WFH"),
	INTERVIEW("INTERVIEW"),
	FINANCE("FINANCE"),
	TICKET("TICKET"),
	SYSTEM("SYSTEM"),
	TRAINING("TRAINING");
	
	private String mailType;
	
	MailType(String mailType){
		this.mailType = mailType;
	}
	
	public String getMailType() {
        return mailType;
    }
	
}
