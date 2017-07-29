package com.perficient.hr.model.type;

public enum MailMediaType {

	CALENDAR("CALENDAR"),
	PLAIN_TEXT("PLAIN_TEXT"),
	HTML("HTML");
	
	private String mailMediaType;
	
	MailMediaType(String mailMediaType){
		this.mailMediaType = mailMediaType;
	}
	
	public String getMailMediaType() {
        return mailMediaType;
    }
	
}
