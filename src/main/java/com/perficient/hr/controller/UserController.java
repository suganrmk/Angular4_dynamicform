package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.service.UserService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-user")
public class UserController {

	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value="/updatePwd",method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response loadEmployeesBySupervisor(HttpServletRequest request, 
			@RequestHeader(value="passphrase") String passphrase, 
			@RequestHeader(value="salt") String salt, @RequestHeader(value="ciphertext") String ciphertext, 
			@RequestHeader(value="iv") String iv, 
			@RequestHeader(value="newPwd") String newPwd){
		return ResponseHandlingUtil.prepareResponse(userService.updatePwd(newPwd, PerfUtils.getUserId(request.getSession()),
				salt, iv,  passphrase, ciphertext));
	}
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response forgotPass(@QueryParam(value="employeePk") String employeePk, HttpServletRequest request) throws Exception{
		return ResponseHandlingUtil.prepareResponse(userService.resetPwdByEmpId(employeePk, PerfUtils.getUserId(request.getSession())));
	}
	
}