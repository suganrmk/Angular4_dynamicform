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

import com.perficient.hr.model.FinanceItrwindow;
import com.perficient.hr.model.FinanceTransaction;
import com.perficient.hr.service.FinanceITRService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;

@Controller
@RequestMapping("/v-finance-itr")
public class FinanceITRController {

	@Autowired
	FinanceITRService financeITRService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public Response saveFinanceTxn(@RequestBody FinanceTransaction financeTxn, HttpServletRequest request) {
		return ResponseHandlingUtil.prepareResponse(financeITRService.saveEmpFinanceTxn(financeTxn));
	}

	@RequestMapping(value = "/reports/{financialYear}", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getReports(@PathVariable("financialYear") String financialYear, HttpServletRequest request) {
		return ResponseHandlingUtil.prepareResponse(
				financeITRService.getEmpFinanceStatusReport(PerfUtils.getUserId(request.getSession()), financialYear));
	}

	@RequestMapping(value = "/status/{financialYear}", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getEmpItrStatus(@PathVariable("financialYear") String financialYear, HttpServletRequest request) {
		return ResponseHandlingUtil.prepareResponse(
				financeITRService.getUserFinanceStatus(PerfUtils.getUserId(request.getSession()), financialYear));
	}

	@RequestMapping(value = "/year/{financialYear}", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getYear(@PathVariable("financialYear") String financialYear, HttpServletRequest request) {
		return ResponseHandlingUtil.prepareResponse(financeITRService.getFinancialYearDetails(financialYear));
	}

	@RequestMapping(value = "/saveItrYear", method = RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public Response saveItrWindow(@RequestBody FinanceItrwindow itrwindow, HttpServletRequest request) {
		return ResponseHandlingUtil
				.prepareResponse(financeITRService.saveFinancialItrWindow(itrwindow, PerfUtils.getUserId(request.getSession())));
	}
}
