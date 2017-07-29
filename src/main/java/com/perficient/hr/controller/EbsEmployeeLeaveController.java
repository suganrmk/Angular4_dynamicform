package com.perficient.hr.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.service.EbsEmployeeLeaveService;
import com.perficient.hr.utils.ResponseHandlingUtil;
import com.perficient.hr.utils.WriteFileUtils;

@Controller
@RequestMapping("/v-ebsleave")
public class EbsEmployeeLeaveController extends AbstractController {

	protected Logger logger = LoggerFactory.getLogger(EbsEmployeeLeaveController.class);
	
	@Autowired
	private EbsEmployeeLeaveService ebsEmployeeLeavesService;
	
	@RequestMapping(value="/uploadEbsLeaves", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	@Produces("application/json")
	@ResponseBody
	public Response uploadExcel(@RequestParam("uploadFiles") MultipartFile file) throws IOException{
		return  ResponseHandlingUtil.prepareResponse(ebsEmployeeLeavesService.parseDocument(WriteFileUtils.writeToFileServer(file.getInputStream(), 
				file.getName()),perfProperties.getStartYear(), perfProperties.getPtoCount()));
	}
    
    @RequestMapping(value="/loadEbsLeaveReport",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response loadEbsLeaveReport(@RequestBody EmployeeLeaves employeeLeaves){
		return  ResponseHandlingUtil.prepareResponse(ebsEmployeeLeavesService.loadEbsLeaveReport(employeeLeaves));
	}
}
