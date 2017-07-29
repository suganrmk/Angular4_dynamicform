package com.perficient.hr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.Holidays;
import com.perficient.hr.service.HolidaysService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-holidays")
public class HolidayController {

	protected Logger logger = LoggerFactory.getLogger(HolidayController.class);
	
	@Autowired
	private HolidaysService holidaysService;
	
	@RequestMapping(value="/loadHolidays",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadHolidays(){
		return ResponseHandlingUtil.prepareResponse(holidaysService.loadHolidays());
	}
	
	@RequestMapping(value="/getHolidaysByYear",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getHolidaysByYear(@RequestParam String year){
		return ResponseHandlingUtil.prepareResponse(holidaysService.getHolidaysByYear(year));
	}
	
	@RequestMapping(value="/addHoliday", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response addHoliday(@RequestBody Holidays holidays, HttpServletRequest request) throws RecordExistsException{
		return ResponseHandlingUtil.prepareResponse(holidaysService.addHolidays(holidays, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/updateHoliday", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response updateHoliday(@RequestBody Holidays holidays, HttpServletRequest request) throws RecordNotFoundException {
		return ResponseHandlingUtil.prepareResponse(holidaysService.updateHolidays(holidays, PerfUtils.getUserId(request.getSession())));
	}
	
	@RequestMapping(value="/deleteHoliday", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteHoliday(@RequestBody Holidays holidays, HttpServletRequest request) throws RecordNotFoundException{
		return ResponseHandlingUtil.prepareResponse(holidaysService.deleteHolidays(holidays, PerfUtils.getUserId(request.getSession())));
	}
	
}
