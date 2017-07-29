package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.to.PtdResponseTO;
import com.perficient.hr.service.PtdService;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;


@Controller
@RequestMapping("v-ptd")
public class PtdController {
	

	
	@Autowired
	private PtdService ptdService;

	@RequestMapping(value="/save",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response save(@RequestBody PtdResponseTO ptdResponseTO,HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse((ptdService.saveAuditDetail(PerfUtils.getUserId(request.getSession()),ptdResponseTO)));
	}
	
	@RequestMapping(value="/loadPtd",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadPtd(HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse((ptdService.loadPtd(request.getParameter(PerfHrConstants.PROJECT_ID),request.getParameter(PerfHrConstants.AUDIT_VERSION))));
	}
	
	@RequestMapping(value="/merge",method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response merge(@RequestBody PtdResponseTO ptdResponseTO,HttpServletRequest request){
		return ResponseHandlingUtil.prepareResponse((ptdService.merge(PerfUtils.getUserId(request.getSession()),request.getParameter(PerfHrConstants.PROJECT_ID),request.getParameter(PerfHrConstants.AUDIT_VERSION),ptdResponseTO)));
	}
	
	
}
