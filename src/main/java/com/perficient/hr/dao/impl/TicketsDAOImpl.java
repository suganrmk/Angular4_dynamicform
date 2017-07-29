package com.perficient.hr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.TicketsDAO;
import com.perficient.hr.form.Reports;
import com.perficient.hr.model.TicketAttachments;
import com.perficient.hr.model.TicketComments;
import com.perficient.hr.model.Tickets;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("ticketsDAO")
public class TicketsDAOImpl implements TicketsDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Tickets> loadTickets(Long pk, Session session) {
		String sqlQuery = " from Tickets d where d.active=:active and d.createdBy=:pk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("pk", pk);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tickets> loadAssignedTickets(Long pk, Session session) {
		String sqlQuery = " from Tickets d where d.active=:active and d.assignedTo=:pk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("pk", pk);
		return query.list();
	}

	@Override
	public Tickets loadTicketsById(Long ticketId, Session session) {
		return (Tickets)session.get(Tickets.class, ticketId);
	}

	@Override
	public Tickets loadTicketByTicketId(String ticketId, Session session) {
		String sqlQuery = " from Tickets d where d.active=:active and d.ticketNo=:ticketId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("ticketId", ticketId);
		return (Tickets) query.uniqueResult();
	}
	
	@Override
	public Tickets addTickets(Tickets ticket, Session session) {
		session.save(ticket);
		return ticket;
	}

	@Override
	public boolean updateTickets(Tickets ticket, Session session) {
		session.merge(ticket);
		return false;
	}

	@Override
	public TicketAttachments addAttachments(TicketAttachments ticketAttachments, Session session) {
		session.save(ticketAttachments);
		return ticketAttachments;
	}

	@Override
	public TicketAttachments loadTicketAttachment(String ticketId, String attachmentId, Session session) {
		String sqlQuery = " from TicketAttachments t where t.active=:active and t.ticketId=:ticketId and t.pk=:attachmentId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("ticketId", Long.parseLong(ticketId));
		query.setParameter("attachmentId", Long.parseLong(attachmentId));
		return (TicketAttachments) query.uniqueResult();
	}

	@Override
	public TicketComments addComments(TicketComments ticketComments, Session session) {
		session.save(ticketComments);
		return ticketComments;
	}

	@Override
	public TicketAttachments loadTicketAttachmentsById(Long id, Session session) {
		return (TicketAttachments)session.get(TicketAttachments.class, id);
	}

	@Override
	public TicketComments loadTicketCommentsById(Long id, Session session) {
		return (TicketComments)session.get(TicketComments.class, id);
	}

	@Override
	public boolean deleteTicketAttachmentsById(TicketAttachments ticketAttachments, Session session) {
		session.delete(ticketAttachments);
		return false;
	}

	@Override
	public boolean deleteTicketCommentsById(TicketComments ticketComments, Session session) {
		session.delete(ticketComments);
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tickets> loadReportTickets(Reports reports, Session session) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery = " from Tickets d where d.active=:active and d.createdBy in ("  +  reports.getEmployeeReportList().toString().replace("[", "").replace("]", "") +")"
				+ " and d.dtCreated between '"+sf.format(reports.getStartsAt())+"' and '"+sf.format(reports.getEndsAt())+" 23:59'";
		if(!reports.getType().equalsIgnoreCase("All")){
			sqlQuery += " and d.status=:status";
		}
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		if(!reports.getType().equalsIgnoreCase("All")){
			query.setParameter("status", reports.getType());
		}
		return query.list();
	}
	
}