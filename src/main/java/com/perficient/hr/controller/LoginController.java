package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.perficient.hr.form.MobileResponse;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
public class LoginController extends AbstractController {

	protected Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	public LoginService loginService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute(PerfHrConstants.USER_ID) == null)
			return new ModelAndView(PerfHrConstants.LOGIN_MODEL);
		else
			return new ModelAndView("redirect:/home");
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView doLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute(PerfHrConstants.USER_ID) == null)
			return new ModelAndView(PerfHrConstants.LOGIN_MODEL);
		else
			return new ModelAndView("redirect:/home");
	}
	
	@RequestMapping(value="/mobileLogin",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Object authenticateUser(HttpServletRequest request,
			@RequestHeader(value="username") String username, @RequestHeader(value="passphrase") String passphrase, 
			@RequestHeader(value="salt") String salt, @RequestHeader(value="ciphertext") String ciphertext, 
			@RequestHeader(value="iv") String iv){
		String returnVal="failed";
		logger.info("Authenticating User :"+username);
		Long empPk = null;
		try {
			if(!username.contains("@perficient.com")){
				username += "@perficient.com";
			}
			empPk = loginService.checkLogin(username, salt, iv, passphrase, ciphertext);
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to Authenticate User: "+username , e);
			return returnVal; 
		}
		if(empPk != null){
			HttpSession session = request.getSession();
			session.setAttribute(PerfHrConstants.USER_ID, empPk);
			MobileResponse mLogin = new MobileResponse();
			mLogin.setMobileResponse(session.getId());
			return mLogin;
		} else {
			logger.info("Invalid Credentials provided for User: "+username);
		}
		return returnVal;
	}
	
	/*@RequestMapping(value="/generatePwd",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public void loadEmployee(HttpServletRequest request) throws Exception{
		loginService.createPasswordForEmployees();
	}*/
	
	@RequestMapping(value="/forgotPwdTicket",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Object forgotPwd(@QueryParam("email") String email, HttpServletRequest request) throws Exception{
		return ResponseHandlingUtil.prepareResponse(loginService.forgotPwdTicket(email));
	}
	
}
