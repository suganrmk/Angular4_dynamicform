package com.perficient.hr.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;
import com.perficient.hr.model.EmailTrack;

public interface MailService {

	void sendMail(EmailTrack email, Session session) throws MessagingException, IOException;
	
}
