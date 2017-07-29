package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/session")
public class SessionController {

	@RequestMapping(value="/validateSession",method=RequestMethod.GET)
	@ResponseBody
	public Response validateSession(HttpServletRequest request){
		boolean returnVal = true;
		if(PerfUtils.getUserId(request.getSession()) == null) {
			request.getSession().invalidate();
			returnVal = false;
		}
		return ResponseHandlingUtil.prepareResponse(returnVal);
	}
	
}
