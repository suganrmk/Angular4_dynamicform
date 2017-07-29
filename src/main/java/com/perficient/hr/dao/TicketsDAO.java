package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.form.Reports;
import com.perficient.hr.model.TicketAttachments;
import com.perficient.hr.model.TicketComments;
import com.perficient.hr.model.Tickets;

public interface TicketsDAO {

	public List<Tickets> loadTickets(Long pk, Session session);
	
	public List<Tickets> loadAssignedTickets(Long pk, Session session);
	
	public Tickets loadTicketsById(Long id, Session session);
	
	public Tickets loadTicketByTicketId(String id, Session session);
	
	public TicketAttachments loadTicketAttachmentsById(Long id, Session session);
	
	public TicketComments loadTicketCommentsById(Long ticketId, Session session);
	
	public Tickets addTickets(Tickets ticket , Session session);
	
	public boolean updateTickets(Tickets ticket, Session session);
	
	public TicketAttachments addAttachments(TicketAttachments ticketAttachments, Session session);
	
	public TicketComments addComments(TicketComments ticketComments, Session session);
	
	public TicketAttachments loadTicketAttachment(String ticketId, String attachmentId, Session session);
	
	public boolean deleteTicketAttachmentsById(TicketAttachments ticketAttachments, Session session);
	
	public boolean deleteTicketCommentsById(TicketComments ticketComments, Session session);
	
	public List<Tickets> loadReportTickets(Reports reports, Session session);
	
}
