package com.perficient.hr.batch;

import org.springframework.batch.item.ItemProcessor;

import com.perficient.hr.model.EmailTrack;

public class NotificationProcessor  implements ItemProcessor<EmailTrack, EmailTrack>{

	@Override
	public EmailTrack process(EmailTrack notification) throws Exception {
	    return notification;
	}

}