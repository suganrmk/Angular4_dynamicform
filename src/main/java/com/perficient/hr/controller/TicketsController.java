package com.perficient.hr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.form.Reports;
import com.perficient.hr.model.Tickets;
import com.perficient.hr.service.TicketsService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-tickets")
public class TicketsController extends AbstractController{

	protected Logger logger = LoggerFactory.getLogger(TicketsController.class);
	
	@Autowired
	private TicketsService ticketsService;
	
	@RequestMapping(value="/loadTickets",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadTickets(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(ticketsService.loadTickets(PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadAssignedTickets",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadAssignedTickets(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(ticketsService.loadAssignedTickets(PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadTicketByTicketId",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadTicketByTicketId(@QueryParam("id") String id){
		return ResponseHandlingUtil.prepareResponse(ticketsService.loadTicketByTicketId(id));
	}
	
	@RequestMapping(value="/loadTicketsById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadTicketsById(@QueryParam("id") String id){
		return ResponseHandlingUtil.prepareResponse(ticketsService.loadTicketsById(id));
	}
	
	@RequestMapping(value="/addTicket", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addTickets(@RequestParam(value = "uploadTicketDoc", required = false) MultipartFile file, 
			@RequestParam("ticket") Object ticket, HttpServletRequest request) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		Tickets ticketVal = mapper.readValue(ticket.toString(), Tickets.class);
		return ResponseHandlingUtil.prepareResponse(ticketsService.addTicket(ticketVal, file, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateTicket", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response updateTickets(@RequestParam(value = "uploadTicketDoc", required = false) MultipartFile file, 
			@RequestParam("ticket") Object ticket, HttpServletRequest request) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Tickets ticketVal = mapper.readValue(ticket.toString(), Tickets.class);
		return ResponseHandlingUtil.prepareResponse(ticketsService.updateTicket(ticketVal, file, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteTicket", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteTickets(@RequestBody Tickets ticket, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(ticketsService.deleteTicket(ticket, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteAttachment", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteAttachment(@QueryParam("id") String id, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(ticketsService.deleteAttachment(id, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteComment", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteComment(@QueryParam("id") String id, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(ticketsService.deleteComment(id, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadTicketsReport",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response loadTicketsReport(@RequestBody Reports reports, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(ticketsService.loadTicketsReport(reports, PerfUtils.getUserId(request.getSession())));
	}
}
