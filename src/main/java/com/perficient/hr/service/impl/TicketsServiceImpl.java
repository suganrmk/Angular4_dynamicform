package com.perficient.hr.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.TicketsDAO;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.form.Reports;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.TicketAttachments;
import com.perficient.hr.model.TicketComments;
import com.perficient.hr.model.Tickets;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.service.TicketsService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;
import com.perficient.hr.utils.WriteFileUtils;

@Service("ticketsService")
public class TicketsServiceImpl implements TicketsService{

	protected Logger logger = LoggerFactory.getLogger(TicketsServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
    TicketsDAO ticketsDAO;
	
	@Autowired
	public EmailTrackDAO emailTrackDAO;
	
	@Autowired
	public PerfProperties perfProperties;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
	
	@Override
	public Object loadTickets(String userId) {
		LoggerUtil.infoLog(logger, "Load Tickets List Service Started");
	    List<Tickets> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			Employee employee = employeeDAO.loadById(userId, session);
			list = ticketsDAO.loadTickets(employee.getPk(), session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Tickets List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Tickets List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}
	
	@Override
	public Object loadAssignedTickets(String userId) {
		LoggerUtil.infoLog(logger, "Load Tickets List Service Started");
	    List<Tickets> list = null;
	    Session session = null;
		try {
			session = sessionFactory.openSession();
			Employee employee = employeeDAO.loadById(userId, session);
			list = ticketsDAO.loadAssignedTickets(employee.getPk(), session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Tickets List" , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Tickets List" , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return list;
	}

	@Override
	public Object loadTicketsById(String ticketId) {
		LoggerUtil.infoLog(logger, "Load Tickets By Id Service Started. ticketId: " + ticketId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return ticketsDAO.loadTicketsById(Long.parseLong(ticketId), session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Tickets By Id : " + ticketId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Tickets By Id : " + ticketId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public Object loadTicketByTicketId(String ticketId) {
		LoggerUtil.infoLog(logger, "Load Tickets By Id Service Started. ticketId: " + ticketId);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return ticketsDAO.loadTicketByTicketId(ticketId, session);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Load Tickets By Id : " + ticketId , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Load Tickets By Id : " + ticketId , e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object addTicket(Tickets ticket, MultipartFile file, String userId) {
		LoggerUtil.infoLog(logger, "Add Tickets Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			ticket.setTicketNo(ticket.getLocation()+"-");
			Employee assignTo = employeeDAO.loadByEmail(perfProperties.getSupportEmail(), session);
			ticket.setAssignedTo(assignTo.getPk());
			ticket.setStatus(PerfHrConstants.OPEN);
			ticket.setDtCreated(new Date());
			ticket.setDtModified(new Date());
			ticket.setCreatedBy(employee.getPk());
			ticket.setModifiedBy(employee.getPk());
			ticketsDAO.addTickets(ticket, session);
			tx.commit();
			tx = session.beginTransaction();
			ticket.setTicketNo(ticket.getLocation()+"-"+ticket.getPk());
			updateTickets(ticket, userId, session);
			if(file != null){
				upLoadFile(ticket, file, employee, session);
			}
			emailTrackDAO.saveEmailTrack(setEmailTrack(ticket, employee, session, false), session);
			tx.commit();
			return ticket;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to add ticket: "+ticket.getLocation(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to add ticket: "+ticket.getLocation(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private void upLoadFile(Tickets ticket, MultipartFile file, Employee employee, Session session) throws IOException{
		String fileName = WriteFileUtils.writeToFileServer(file.getInputStream(), perfProperties.getTicketsStoreLocation()+"\\"+ticket.getPk(), file.getOriginalFilename());
		TicketAttachments ticketAttachments = new TicketAttachments();
		ticketAttachments.setFileName(fileName);
		ticketAttachments.setTicketId(ticket.getPk());
		ticketAttachments.setCreatedBy(employee.getPk());
		ticketAttachments.setModifiedBy(employee.getPk());
		ticketAttachments.setDtCreated(new Date());
		ticketAttachments.setDtModified(new Date());
		ticketsDAO.addAttachments(ticketAttachments, session);
	}

	@Override
	public Object updateTicket(Tickets ticket, MultipartFile file, String userId) {
		LoggerUtil.infoLog(logger, "Update Tickets Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String cmts  = ticket.getComments();
			ticket.setComments(null);
			updateTickets(ticket, userId, session);
			Employee employee = employeeDAO.loadById(userId, session);
			if(cmts != null){
				TicketComments ticketComments = new TicketComments();
				ticketComments.setComment(cmts);
				ticketComments.setTicketId(ticket.getPk());
				ticketComments.setCreatedBy(employee.getPk());
				ticketComments.setModifiedBy(employee.getPk());
				ticketComments.setDtCreated(new Date());
				ticketComments.setDtModified(new Date());
				ticketsDAO.addComments(ticketComments, session);	
			}
			if(file != null){
				upLoadFile(ticket, file, employee, session);
			}
			tx.commit();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			emailTrackDAO.saveEmailTrack(setEmailTrack(ticket, employee, session, true), session);
			tx.commit();
			return ticketsDAO.loadTicketsById(ticket.getPk(), session);
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update ticket: "+ticket.getTicketNo(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update ticket: "+ticket.getTicketNo(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private boolean updateTickets(Tickets ticket, String userId, Session session){
		ticket.setDtModified(new Date());
		ticket.setModifiedBy(employeeDAO.loadById(userId, session).getPk());
		return ticketsDAO.updateTickets(ticket, session);
	}

	@Override
	public Object deleteTicket(Tickets ticket, String userId) {
		LoggerUtil.infoLog(logger, "Delete ticket Service Started");
		Session session = null ;
		Transaction tx = null;
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			ticket.setActive(PerfHrConstants.INACTIVE);
			updateTickets(ticket, userId, session);
			tx.commit();
			return true;
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to delete ticket: "+ticket.getTicketNo(), e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to delete ticket: "+ticket.getTicketNo(), e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object getFileName(String ticketId, String attachmentId, String userId) {
		LoggerUtil.infoLog(logger, "getFileName Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			/*Employee employee = employeeDAO.loadById(userId, session);
			Tickets ticket = (Tickets) loadTicketsById(ticketId);*/
			TicketAttachments attachment = ticketsDAO.loadTicketAttachment(ticketId, attachmentId, session);
			return attachment.getFileName();
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to load document for ticket: "+ticketId, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to load document for ticket: "+ticketId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object deleteAttachment(String id, String userId) {
		LoggerUtil.infoLog(logger, "Delete attachemnt Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			TicketAttachments attachment = ticketsDAO.loadTicketAttachmentsById(Long.parseLong(id), session);
			if(!employee.getPk().equals(attachment.getCreatedBy())){
				throw new PrftException("Permission Denied");
			}
			attachment.setEmployeeNamesView(null);
			attachment.setActive(PerfHrConstants.INACTIVE);
			ticketsDAO.deleteTicketAttachmentsById(attachment, session);
			tx.commit();
			return true;
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, "Unable to Delete attachemnt: "+id, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delete attachemnt: "+id, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete attachemnt: "+id, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}

	@Override
	public Object deleteComment(String id, String userId) {
		LoggerUtil.infoLog(logger, "Delete comments Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(userId, session);
			TicketComments comments = ticketsDAO.loadTicketCommentsById(Long.parseLong(id), session);
			comments.setEmployeeNamesView(null);
			if(!employee.getPk().equals(comments.getCreatedBy())){
				throw new PrftException("Permission Denied");
			}
			comments.setActive(PerfHrConstants.INACTIVE);
			ticketsDAO.deleteTicketCommentsById(comments, session);
			tx.commit();
			return true;
		} catch(PrftException e){
			LoggerUtil.errorLog(logger, "Unable to Delete attachemnt: "+id, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
		} catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to Delete comments: "+id, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to Delete comments: "+id, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
	}
	
	private EmailTrack setEmailTrack(Tickets ticket, Employee employee, Session session, boolean isUpdate) {
		EmailTrack emailTrack = new EmailTrack();
		if(isUpdate)
			ticket = ticketsDAO.loadTicketsById(ticket.getPk(), session);
		Employee createdBy = employeeDAO.loadById(ticket.getCreatedBy().toString(), session);
		Employee assingedTo = employeeDAO.loadById(ticket.getAssignedTo().toString(), session);
		emailTrack.setIdGeneric(ticket.getPk());
		emailTrack.setMailType(MailType.TICKET.getMailType());
				
		Set<String> mailToSet = new HashSet<>();
		mailToSet.add(createdBy.getEmail());
		mailToSet.add(employee.getEmail());
		mailToSet.add(assingedTo.getEmail());
		emailTrack.setMailTo(mailToSet.toString().replace("[","").replace("]",""));
		
		if(!isUpdate)
			emailTrack.setSubject(ticket.getTicketNo()+" "+createdBy.getLastName()+", "+createdBy.getFirstName()+" created a ticket.");
		else {
			if(ticket.getStatus().equalsIgnoreCase("Closed"))
				emailTrack.setSubject(employee.getLastName()+", "+employee.getFirstName()+" closed ticket: "+ticket.getTicketNo());
			else
				emailTrack.setSubject(employee.getLastName()+", "+employee.getFirstName()+" updated ticket: "+ticket.getTicketNo());
		}
			
		String body = "<div>";
		body += "<b>Location:</b> "+ticket.getLocation()+"<br><br>";
		body += "<b>Type:</b> "+ticket.getIssueType()+"<br><br>";
		body += "<b>Description:</b> "+ticket.getDescription()+"<br><br>";
		if(ticket.getTicketAttachments() != null){
			for(TicketAttachments attachment: ticket.getTicketAttachments()){
				body += "<b>Attachment</b>: "+attachment.getFileName()+"<br><br>";
			}
		}
		
		if(isUpdate){
			body += "<b>Assign To:</b> "+assingedTo.getLastName()+", "+assingedTo.getFirstName()+"<br><br>";
			body += "<b>Status:</b> "+ticket.getStatus()+"<br><br>";
			body += "<b>Type:</b> "+ticket.getIssueType()+"<br><br>";
			if(ticket.getClosedOn() != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				body += "<b>Ticket Closed On:</b> "+sdf.format(ticket.getClosedOn())+"<br><br>";
			}
		}
		if(ticket.getTicketComments() != null){
			for(TicketComments comments: ticket.getTicketComments()){
				body += "<b>Comment</b>: "+comments.getComment()+"<br><br>";
			}	
		}
		body += "</div>";
		String emailTemplate = perfProperties.getEmailTemplate();
		String emailTitle = perfProperties.getEmailTitle().replace("{{title}}", "Ticket No: "+ticket.getTicketNo()+"<br>");
		emailTrack.setComments(emailTemplate.replace("{{title}}", emailTitle).replace("{{body}}", body).replace("{{footer}}", "Regards, <br>"+employee.getLastName()+", "+employee.getFirstName()));
		emailTrack.setMailFrom(employee.getEmail());
		emailTrack.setMediaType(MailMediaType.HTML.getMailMediaType());
		emailTrack.setPriority("5");
		emailTrack.setMailStatus(PerfHrConstants.PENDING);
		emailTrack.setUid(employee.getEmployeeId().toString() + ticket.getPk());
		emailTrack.setFlag(0);
		emailTrack.setActive(PerfHrConstants.ACTIVE);
		emailTrack.setCreatedBy(employee.getPk());
		emailTrack.setModifiedBy(employee.getPk());
		emailTrack.setDtCreated(new Date());
		emailTrack.setDtModified(new Date());
		return emailTrack;
	}
	
	@Override
	public Object loadTicketsReport(Reports reports, String userId) {
		LoggerUtil.infoLog(logger, "Load Tickets Report Started.");
		List<Tickets> list = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			list = ticketsDAO.loadReportTickets(reports, session);
			return list;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable Tickets Report Started." , e);
			return ExceptionHandlingUtil.returnErrorObject("Unable Tickets Report Started.", e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
}
