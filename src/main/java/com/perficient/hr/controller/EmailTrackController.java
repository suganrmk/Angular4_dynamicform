package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.service.impl.EmailTrackServiceImpl;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-emailservice")
public class EmailTrackController {

	@Autowired
	private EmailTrackServiceImpl emailTrackService; 
	
	@RequestMapping(value="/saveemailinfo", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response saveEmailTrack(@RequestBody EmailTrack emailTrack, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(emailTrackService.saveEmailTrack(emailTrack));
	}
    
    @RequestMapping(value="/updateemailinfo", method=RequestMethod.PUT)
    @Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Response updateEmailTrack(@RequestBody EmailTrack emailTrack, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(emailTrackService.updateEmailTrack(emailTrack));
	}
    
    @RequestMapping(value="/getEmailByStatusAndType", method=RequestMethod.GET)
    @Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Response getEmailByStatusAndType(@QueryParam("status") String status,  @QueryParam("mailType") String mailType, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(emailTrackService.getEmailListByTypeAndStatus(status, mailType));
	}
}
