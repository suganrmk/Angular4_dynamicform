package com.perficient.hr.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.schedulerService.SchedulerService;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfProperties;

public class Scheduler {

	protected Logger logger = LoggerFactory.getLogger(Scheduler.class);

	@Autowired
	private SchedulerService iSchedulerService;

	@Autowired
	public PerfProperties perfProperties;
	
	public void sendNotification() {
		if(perfProperties.getSendMail().equals("true")){
			LoggerUtil.infoLog(logger, " Send mail scheduler started");
			iSchedulerService.getEmailDetails();
			iSchedulerService.sendEmail();	
		}		
	}
}