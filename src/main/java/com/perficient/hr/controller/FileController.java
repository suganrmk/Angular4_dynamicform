package com.perficient.hr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.service.TicketsService;
import com.perficient.hr.utils.PerfUtils;

@Controller
@RequestMapping("/v-file")
public class FileController extends AbstractController{

	@Autowired
	private TicketsService ticketsService;
	
	@RequestMapping(value = "/downloadTicketAttachment/{ticketId}/{attachmentId}", method = RequestMethod.GET)
	public @ResponseBody void downloadTicketAttachment(@PathVariable String ticketId, @PathVariable String attachmentId, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(perfProperties.getTicketsStoreLocation()+"\\"+ticketId+"\\"+ticketsService.getFileName(ticketId, attachmentId, PerfUtils.getUserId(request.getSession())));
	    InputStream in = new FileInputStream(file);
	    response.setContentType("application/json");
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    FileCopyUtils.copy(in, response.getOutputStream());
	}
	
}
