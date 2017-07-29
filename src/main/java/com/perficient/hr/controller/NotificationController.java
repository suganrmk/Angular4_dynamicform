package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.model.Notification;
import com.perficient.hr.service.NotificationService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value="/loadNotificationCount",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadNotificationCount(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(notificationService.getNotificationCount(PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadNotificationByType/{type}/{status}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadNotificationByType(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("status") String status){
		return ResponseHandlingUtil.prepareResponse(notificationService.loadNotificationsByType(type, status, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/loadNotificationMsgs",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadNotificationMsgs(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse(notificationService.loadNotificationMsgs(PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/saveNotification", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response applyLeave(@RequestBody Notification notification, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(notificationService.saveNotification(notification));
	}
    
    @RequestMapping(value="/updateNotification", method=RequestMethod.PUT)
    @Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Response updateLeave(@RequestBody Notification notification, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(notificationService.updateNotification(notification));
	}
    
    @RequestMapping(value="/approveRejectNotification/{id}/{status}", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response approveRejectNotification(@RequestBody String notifyComments, @PathVariable("id") String id,@PathVariable("status") String status, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(notificationService.approveRejectNotification(id, status, notifyComments, PerfUtils.getUserId(request.getSession())));
	}
    
    @RequestMapping(value="/markReadNotification/{id}", method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response markReadNotification(@PathVariable("id") String id, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(notificationService.markReadNotification(id, PerfUtils.getUserId(request.getSession())));
	}
    
    @RequestMapping(value="/delegateNotification/{id}/{empId}", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response delegateNotification(@PathVariable("id") String id, @PathVariable("empId") String empId, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(notificationService.delegateNotification(id, empId, PerfUtils.getUserId(request.getSession())));
	}
}
