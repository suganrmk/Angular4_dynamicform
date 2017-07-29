package com.perficient.hr.schedulerServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.batch.NotificationReader;
import com.perficient.hr.schedulerService.SchedulerService;
import com.perficient.hr.utils.LoggerUtil;

@Repository("schedulerServiceImpl")
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private NotificationReader reader;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job loadJob;

	protected Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	private String thisClassName = SchedulerServiceImpl.class.getSimpleName();

	@Override
	public void getEmailDetails() {
		try {
//			LoggerUtil.infoLog(logger, " getNotificationDetails method starting");
			reader.initialize();
//			LoggerUtil.infoLog(logger, " getNotificationDetails method existing");
		} catch (Exception e) {
			LoggerUtil.errorLog(logger,
					" Error occured in " + thisClassName + " while retriving the notification detail and exception is ",e);
		}
	}

	@Override
	public void sendEmail() {
		try {
//			LoggerUtil.infoLog(logger, " Job is going to call to send email");
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(loadJob, jobParameters);
//			LoggerUtil.infoLog(logger, "Exit Status : " + execution.getStatus());
//			LoggerUtil.infoLog(logger, " Job has been successfully called and send mail to all users");
		} catch (Exception e) {
			LoggerUtil.errorLog(logger,
					" Error occured in " + thisClassName + " while calling the job to send email and exception is ", e);
		}
	}
}