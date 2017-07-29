package com.perficient.hr.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.ResponseHandlingUtil;

@ControllerAdvice(basePackages = {"com.perficient.hr.controller"})
public class AdviceController {

	@ExceptionHandler(AccessDeniedException.class)
	@Produces("application/json")
	@ResponseBody
	public Response myError(Exception exception) {
		return ResponseHandlingUtil.prepareResponse(ExceptionHandlingUtil.returnErrorObject("UnAuthorized Access. Please Contact System Administrator.", 
				HttpStatus.FORBIDDEN.value()));
	}
}
