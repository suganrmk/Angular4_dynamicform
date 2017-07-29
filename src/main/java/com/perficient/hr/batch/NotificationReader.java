package com.perficient.hr.batch;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.scheduler.Scheduler;
import com.perficient.hr.service.EmailTrackService;
import com.perficient.hr.utils.PerfHrConstants;

public class NotificationReader implements ItemReader<EmailTrack> {

	protected Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	public Queue<EmailTrack> emailTracksList = null;

	@Autowired
	private EmailTrackService emailTrackService;

	@Override
	public EmailTrack read() throws Exception, UnexpectedInputException,ParseException, NonTransientResourceException {
		if(emailTracksList != null && emailTracksList.size() > 0){
			EmailTrack emailTrack = emailTracksList.poll();
			logger.info("In poll ");
			return emailTrack;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		List<EmailTrack> emailAsList = (List<EmailTrack>) emailTrackService.getEmailList();
		logger.info("Email List size "+emailAsList.size());
		emailTracksList =  new LinkedList<EmailTrack>(emailAsList);
		for(EmailTrack emailTrack: emailTracksList){
			emailTrack.setMailStatus(PerfHrConstants.PROCESSING);
			emailTrackService.updateEmailTrack(emailTrack);
		}
	}

}
