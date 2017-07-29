package com.perficient.hr.service;

import org.springframework.web.multipart.MultipartFile;

import com.perficient.hr.form.Reports;
import com.perficient.hr.model.Tickets;

public interface TicketsService {

	public Object loadTickets(String userId);
	
	public Object loadAssignedTickets(String userId);
	
	public Object loadTicketsById(String ticketId);
	
	public Object loadTicketByTicketId(String ticketId);
	
	public Object addTicket(Tickets ticket, MultipartFile file, String userId);
	
	public Object updateTicket(Tickets ticket, MultipartFile file, String userId);
	
	public Object deleteTicket(Tickets ticket, String userId);
	
	public Object getFileName(String ticketId, String attachmentId, String userId);
	
	public Object deleteAttachment(String id, String userId);
	
	public Object deleteComment(String id, String userId);
	
	public Object loadTicketsReport(Reports reports, String userId);
}
