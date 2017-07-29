package com.perficient.hr.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.perficient.hr.utils.PerfHrConstants;

public class CorsInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(CorsInterceptor.class);
	
	public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
	public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
	public static final String METHODS_NAME = "Access-Control-Allow-Methods";
	public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
	public static final String MAX_AGE_NAME = "Access-Control-Max-Age";

	public static final String REQUEST_ORIGIN_NAME = "Origin";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String origin = request.getHeader(REQUEST_ORIGIN_NAME);
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			response.setHeader(ORIGIN_NAME, origin);
			response.setHeader(METHODS_NAME, "GET,PUT,POST,DELETE");
			response.setHeader(CREDENTIALS_NAME, "true");
			response.setHeader(HEADERS_NAME, "Origin, X-Requested-With, If-Modified-Since, Cache-Control, "
					+ "Pragma, Content-Type, Accept, username, passphrase, newPwd, salt, ciphertext, iv");
			response.setHeader(MAX_AGE_NAME, "3600");
			return true;
		}

		response.setHeader(CREDENTIALS_NAME, "true");
		response.setHeader(ORIGIN_NAME, origin);
		response.setHeader(METHODS_NAME, "GET, OPTIONS, POST, PUT, DELETE");
		response.setHeader(HEADERS_NAME, "Origin, X-Requested-With, If-Modified-Since, Cache-Control, "
				+ "Pragma, Content-Type, Accept, username, passphrase, newPwd, salt, ciphertext, iv");
		response.setHeader(MAX_AGE_NAME, "3600");
		if ((request.getRequestURI().contains("v-") || request.getRequestURI().contains("/html"))
				&& request.getSession().getAttribute(PerfHrConstants.USER_ID) == null) {
			request.getSession().invalidate();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		return true;
	}

}
