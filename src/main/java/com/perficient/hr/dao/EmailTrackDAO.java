package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.EmailTrack;

public interface EmailTrackDAO {

	public boolean saveEmailTrack(EmailTrack emailTrack, Session session) throws GenericException;
	
	public boolean updateEmailTrack(EmailTrack emailTrack, Session session);

	public List<EmailTrack> getEmailList(Session session);
	
	public Object getEmailListByTypeAndStatus(String status, String mailType, Session session);
	
}
