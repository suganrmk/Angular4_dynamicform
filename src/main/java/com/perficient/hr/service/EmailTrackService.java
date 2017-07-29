package com.perficient.hr.service;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.EmailTrack;

public interface EmailTrackService {

	public Object saveEmailTrack(EmailTrack emailTrack);
	
	public Object saveEmailTrack(EmailTrack emailTrack, Session session) throws GenericException;
	
	public Object updateEmailTrack(EmailTrack emailTrack);
	
	public Object updateEmailTrack(EmailTrack emailTrack, Session session);
	
	public Object getEmailList();
	
	public Object getEmailListByTypeAndStatus(String status, String mailType);
}
