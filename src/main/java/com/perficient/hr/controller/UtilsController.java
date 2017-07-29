package com.perficient.hr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.form.VersionDetails;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-utils")
public class UtilsController extends AbstractController{

	protected Logger logger = LoggerFactory.getLogger(UtilsController.class);

	private Properties prop;
	
	@RequestMapping(value="/version",method=RequestMethod.GET)
	@ResponseBody
	public Response getVersion(){
		VersionDetails version = new VersionDetails();
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
        this.prop = new Properties();
        try {
            this.prop.load(resourceAsStream);
            version.setVersion(this.prop.getProperty("version").replace("-SNAPSHOT", ""));
            version.setMinSupportedVersion(perfProperties.getMinSupportedVersion());
            version.setMessage("");
        } catch(IOException e){
        	logger.info("Unable to load project version.");
        }
		return ResponseHandlingUtil.prepareResponse(version);
	}
	
	@RequestMapping(value="/versionCompatiblity",method=RequestMethod.GET)
	@ResponseBody
	public Response getVersionCompatiblity(){
		String version = null;
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties");
        this.prop = new Properties();
        try {
            this.prop.load(resourceAsStream);
            version = this.prop.getProperty("version").replace("-SNAPSHOT", "");
        } catch(IOException e){
        	logger.info("Unable to load project version.");
        }
		return ResponseHandlingUtil.prepareResponse(version);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Object doLogut(HttpServletRequest request){
		request.getSession().invalidate();
		logger.info("User logged out successfully");
		return true;
	}
	
}
