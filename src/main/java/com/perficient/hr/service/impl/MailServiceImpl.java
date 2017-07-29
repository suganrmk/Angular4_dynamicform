package com.perficient.hr.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.orm.PrftDbObjectManager;
import com.perficient.hr.service.MailService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;

@Service("mailService")
public class MailServiceImpl extends PrftDbObjectManager<MailServiceImpl> implements MailService {

	protected Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	public PerfProperties perfProperties;
	
	@Autowired
	public EmployeeDAO employeeDAO;
	
	@Override
	public void sendMail(EmailTrack email, Session mailSession) throws MessagingException, IOException {
		Message message = new MimeMessage(mailSession);
		String recipients = email.getMailTo();
		String[] recipientList = recipients.split(",");
        InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
        int counter = 0;
        
        org.hibernate.Session session = null;
		try{
			session = sessionFactory.openSession();
	        for (String recipient : recipientList) {
	        	if(email.getMailType().equals(MailType.TRAINING.getMailType())){
	        		recipientAddress[counter] = new InternetAddress(employeeDAO.loadById(recipient, session).getEmail());
	        	} else {
	        		recipientAddress[counter] = new InternetAddress(recipient.trim());	
	        	}
	            counter++;
	        }
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Employee in Mail Service" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		
        // Set From: header field of the header.
	    message.setFrom(new InternetAddress(perfProperties.getUsername()));
	    // Set To: header field of the header.
	    message.setRecipients(Message.RecipientType.TO, recipientAddress);
	    // Set CC: header field of the header.
        if(email.getMailCC() !=null ){
        	String[] ccList = email.getMailCC().split(",");
	        InternetAddress[] ccAddress = new InternetAddress[ccList.length];
	        counter = 0;
	        for (String ccMail : ccList) {
	        	ccAddress[counter] = new InternetAddress(ccMail.trim());
	            counter++;
	        }
	        if(ccAddress.length != 0){
		    	message.setRecipients(Message.RecipientType.CC, ccAddress);	
		    }
        }
	    
	    // Set Subject: header field
	    message.setSubject(email.getSubject());
        
		if(email.getMediaType().equals(MailMediaType.HTML.getMailMediaType())){
		    // Send the actual HTML message, as big as you like
			MimeBodyPart  bodyPart = new MimeBodyPart();  
			bodyPart.setContent(email.getComments(), "text/html");  
		      
			Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(bodyPart);  
		    
			//Set attachment
		    if(email.getAttachment() != null){
		    	MimeBodyPart attachmentPart = new MimeBodyPart();  
		    	String attachment = email.getAttachment();
		        DataSource source = new FileDataSource(attachment);
		        attachmentPart.setDataHandler(new DataHandler(source));
		        attachmentPart.setFileName(attachment.substring(attachment.lastIndexOf("\\")+1, attachment.length()));
		        multipart.addBodyPart(attachmentPart);
		    }
		     
		    // Send the actual HTML message, as big as you like
		    message.setContent(multipart);
		} else {
			// register the text/calendar mime type
			MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap) MimetypesFileTypeMap.getDefaultFileTypeMap();
			mimetypes.addMimeTypes("text/calendar ics ICS");

			// register the handling of text/calendar mime type
			MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
			mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");

			BodyPart messageBodyPart = new MimeBodyPart();

			// part 1, html text
			// Note: even if the content is specified as being text/html, outlook
			// won't read correctly tables at all
			// and only some properties from div:s. Thus, try to avoid too fancy
			// content
			String content = "<font size=\"2\">" + email.getComments() + "</font>";
			messageBodyPart.setContent(content, "text/html; charset=utf-8");

			// Create a multipart message
	        Multipart multipart = new MimeMultipart();

	        // Set text message part
	        multipart.addBodyPart(messageBodyPart);
			
			//Set attachment
		    if(email.getAttachment() != null){
		    	messageBodyPart = new MimeBodyPart();
		    	String attachment = email.getAttachment();
		        DataSource source = new FileDataSource(attachment);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(attachment.substring(attachment.lastIndexOf("\\")+1, attachment.length()));
		        multipart.addBodyPart(messageBodyPart);
		    }
			
			// Add part two, the calendar
			BodyPart calendarPart = buildCalendarPart(email);
			multipart.addBodyPart(calendarPart);

			// Put the multipart in message
			message.setContent(multipart);
		}
		// send the message
		Transport transport = mailSession.getTransport("smtp");
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	private  BodyPart buildCalendarPart(EmailTrack email) throws MessagingException, IOException {
		BodyPart calendarPart = new MimeBodyPart();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	    StringBuilder sb = new StringBuilder();
	    String emailType = email.getRequestType().equals(PerfHrConstants.REQUEST)?PerfHrConstants.REQUEST:PerfHrConstants.CANCEL;
        //check the icalendar spec in order to build a more complicated meeting request
        StringBuilder builder = sb.append("BEGIN:VCALENDAR\n" +
    		   "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
               "VERSION:2.0\n" +
               "METHOD:"+emailType+"\n" +
               "BEGIN:VTIMEZONE\n"+
               "TZID:Asia/Kolkata\n"+
			   "BEGIN:STANDARD\n"+
			   "TZOFFSETFROM:+0530\n"+
			   "TZOFFSETTO:+0530\n"+
			   "TZNAME:IST\n"+
			   "END:STANDARD\n"+
               "END:VTIMEZONE\n"+
               "BEGIN:VEVENT\n" +
               "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+email.getMailTo()+"\n" +
               "ORGANIZER:MAILTO:"+email.getMailFrom()+"\n" +
               "DTSTART;TZID=Asia/Kolkata:"+sdf.format(email.getFromDate())+"\n" +
               "DTEND;TZID=Asia/Kolkata:"+sdf.format(email.getToDate())+"\n" +
               "TRANSP:TRANSPARENT\n" +
               "SEQUENCE:"+email.getMailSequence()+"\n" +
               "UID:"+email.getUid()+"\n" +
               "DTSTAMP:"+sdf.format(new Date().getTime())+"\n" +
               "DESCRIPTION:"+email.getComments()+"\n\n" +
               "SUMMARY:"+email.getSubject()+"\n" +
               "PRIORITY:5\n" +
               "X-MICROSOFT-CDO-BUSYSTATUS:FREE\n"+
               "X-MICROSOFT-CDO-INTENDEDSTATUS:FREE\n"+
               "CLASS:PUBLIC\n" +
               "BEGIN:VALARM\n" +
               "TRIGGER:NONE\n" +
               "ACTION:DISPLAY\n" +
               "DESCRIPTION:Reminder\n" +
               "END:VALARM\n" +
               "END:VEVENT\n" +
               "END:VCALENDAR");

		calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
		if(email.getRequestType().equals(PerfHrConstants.CANCEL)){
			 builder.append("STATUS:CANCELLED");
		}
        calendarPart.setDataHandler(new DataHandler(
               new ByteArrayDataSource(builder.toString(), "text/calendar;method='"+emailType+"'")));// very important
       return calendarPart;
   }
}
