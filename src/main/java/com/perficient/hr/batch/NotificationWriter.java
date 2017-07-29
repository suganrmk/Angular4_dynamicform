package com.perficient.hr.batch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.service.EmailTrackService;
import com.perficient.hr.service.MailService;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;

public class NotificationWriter implements ItemWriter<EmailTrack> {

	protected Logger logger = LoggerFactory.getLogger(NotificationWriter.class);
	
	@Autowired
	EmailTrackService emailTrackService;
	
	@Autowired
    MailService mailService;
	
	@Autowired
	public PerfProperties perfProperties;
	
	private Session configureMailSession() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.trust", perfProperties.getHost());
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.user", perfProperties.getUsername());
		props.put("mail.smtp.password", perfProperties.getPassword());
		props.put("mail.smtp.host", perfProperties.getHost());
		props.put("mail.smtp.port", perfProperties.getPort());
		props.setProperty("mail.debug", "true");
		return Session.getDefaultInstance(props, null);
	}
	
	@Override
	public void write(List<? extends EmailTrack> notifications) throws Exception {
		EmailTrack emailTrack = null;
		Session session = configureMailSession();
		if(notifications != null){
			for (Iterator<? extends EmailTrack> iterator = notifications.iterator(); iterator.hasNext();) {
				try {
					emailTrack = (EmailTrack) iterator.next();
					logger.info("In writer "+emailTrack.getPk());
					mailService.sendMail(emailTrack, session);	
					emailTrack.setMailStatus(PerfHrConstants.SUCCESS);
				} catch (Exception e) {
					emailTrack.setMailStatus(PerfHrConstants.FAILURE);
					logger.info("Mail service failed: Exception is "+e.getMessage());
				}
				emailTrackService.updateEmailTrack(emailTrack);
			}
		}
	}
}