package com.perficient.hr.model.type;

public enum NotificationType {

	PTO("PTO"),
	UNPLANNED_PTO("Unplanned PTO"),
	WFH("WFH"),
	MESSAGE("MESSAGE");
	
	private String notificationType;
	
	NotificationType(String notificationType){
		this.notificationType = notificationType;
	}
	
	public String getNotificationType() {
        return notificationType;
    }
	
}
