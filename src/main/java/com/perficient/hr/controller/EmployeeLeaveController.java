package com.perficient.hr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.perficient.hr.exception.RecordExistsException;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.service.EmployeeLeavesService;
import com.perficient.hr.service.EmployeeService;
import com.perficient.hr.utils.PerfUtils;
import com.perficient.hr.utils.ResponseHandlingUtil;
import com.perficient.hr.utils.WriteFileUtils;

@Controller
@RequestMapping("/v-leave")
public class EmployeeLeaveController extends AbstractController {
		
	protected Logger logger = LoggerFactory.getLogger(EmployeeLeaveController.class);
	
	@Autowired
	private EmployeeLeavesService employeeLeavesService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/fetchExcel", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	@Produces("application/json")
	@ResponseBody
	public Response uploadExcel(@RequestParam("uploadFiles") MultipartFile file) throws IOException{
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.parseDocument(WriteFileUtils.writeToFileServer(file.getInputStream(), 
				file.getName()), perfProperties.getStartYear(), perfProperties.getPtoCount()));
	}
	
	@RequestMapping(value="/uploadOpeningBalance", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	@Produces("application/json")
	@ResponseBody
	public Response uploadOpeningBalance(@RequestParam("uploadOpeningBalance") MultipartFile file) throws IOException{
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.parseOpeningBalDocument(WriteFileUtils.writeToFileServer(file.getInputStream(), file.getName())));
	}
	
	@RequestMapping(value="/importWfhCalInfoFromIcs", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	@Produces("application/json")
	@ResponseBody
	public Response importWfhCalInfoFromIcs(@RequestParam("importWfhCalInfoFromIcs") MultipartFile file) throws IOException{
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.importWfhCalInfoFromIcs(WriteFileUtils.writeToFileServer(file.getInputStream(), file.getName())));
	}
	
	@RequestMapping(value="/importOfficeEntry", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	@Produces("application/json")
	@ResponseBody
	public Response importOfficeEntry(@RequestParam("importOfficeEntry") MultipartFile file, HttpServletRequest request) throws IOException{
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.importOfficeEntry(WriteFileUtils.writeToFileServer(file.getInputStream(), file.getName()), 
				PerfUtils.getUserId(request.getSession())));
	}
	
    @RequestMapping(value="/loadLeavesByYear/{leaveType}/{calYear}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadLeavesByYear(@PathVariable("leaveType") String leaveType, @PathVariable("calYear") String calYear){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadLeavesByYear(leaveType, calYear));
	}
    
    @RequestMapping(value="/loadLeavesByMonth/{leaveType}/{calYear}/{calMonth}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadLeavesByMonth(@PathVariable("leaveType") String leaveType, @PathVariable("calYear") String calYear,
			@PathVariable("calMonth") String calMonth){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadLeavesByMonth(leaveType, calYear, calMonth));
	}
    
    @RequestMapping(value="/loadMyLeaves/{leaveType}/{calYear}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadMyLeaves(@PathVariable("leaveType") String leaveType, @PathVariable("calYear") String calYear, HttpServletRequest request){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadMyLeaves(leaveType, calYear, PerfUtils.getUserId(request.getSession())));
	}
    
    @RequestMapping(value="/getEmpLeaveBalance/{calYear}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getEmpLeaveBalance(@PathVariable("calYear") String calYear, @QueryParam("empId") String empId, HttpServletRequest request){
    	String employeeId = PerfUtils.getUserId(request.getSession());
    	if(empId != null && empId.trim().length() != 0){
    		Employee emp = (Employee) employeeService.loadByEmployeeId(empId);
    		employeeId = emp.getPk().toString();
    	}
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.getEmpLeaveBalance(calYear, employeeId));
	}
    
    @RequestMapping(value="/getLeaveBalanceByMonth/{leaveType}/{calYear}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getLeaveBalanceByMonth(@PathVariable("leaveType") String leaveType, @PathVariable("calYear") String calYear,
			@QueryParam("empId") String empId, HttpServletRequest request){
    	String employeeId = PerfUtils.getUserId(request.getSession());
    	if(empId != null && empId.trim().length() != 0){
    		Employee emp = (Employee) employeeService.loadByEmployeeId(empId);
    		employeeId = emp.getPk().toString();
    	}
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.getLeaveBalanceByMonth(leaveType, calYear,
				employeeId));
	}
    
    @RequestMapping(value="/getLeaveBalanceByType/{leaveType}/{calYear}",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response getLeaveBalanceByType(@PathVariable("leaveType") String leaveType, @PathVariable("calYear") String calYear,
			@QueryParam("empId") String empId, HttpServletRequest request){
    	String employeeId = PerfUtils.getUserId(request.getSession());
    	if(empId != null && empId.trim().length() != 0){
    		Employee emp = (Employee) employeeService.loadByEmployeeId(empId);
    		employeeId = emp.getPk().toString();
    	}
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.getLeaveBalanceByType(leaveType, calYear,
				employeeId));
	}
    
    @RequestMapping(value="/loadLeaveReport",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response loadLeaveReport(@RequestBody EmployeeLeaves employeeLeaves){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadLeaveReport(employeeLeaves));
	}
    
    @RequestMapping(value="/loadAllLeaveReport",method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response loadAllLeaveReport(@RequestBody EmployeeLeaves employeeLeaves){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadAllLeaveReport(employeeLeaves));
	}
    
    @RequestMapping(value="/loadLeaveById",method=RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Response loadLeaveById(@RequestParam(value="leaveId") String leaveId){
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.loadLeaveById(leaveId));
	}
    
    @RequestMapping(value="/applyLeave", method=RequestMethod.POST)
	@Produces("application/json")
	@ResponseBody
	public Response applyLeave(@RequestBody EmployeeLeaves employeeLeaves, HttpServletRequest request) throws RecordExistsException{
		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.applyLeave(employeeLeaves, PerfUtils.getUserId(request.getSession()), false));
	}
    
    @RequestMapping(value="/updateLeave", method=RequestMethod.PUT)
    @Produces("application/json")
	@Consumes("application/json")
	@ResponseBody
	public Response updateLeave(@RequestBody EmployeeLeaves employeeLeaves, HttpServletRequest request){
    	if(employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
    			||employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
    			||employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())){
    		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.updateWfh(employeeLeaves, PerfUtils.getUserId(request.getSession())));
    	} else
    		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.updateLeave(employeeLeaves, PerfUtils.getUserId(request.getSession())));
	}
    
    @RequestMapping(value="/deleteLeave", method=RequestMethod.PUT)
	@Produces("application/json")
	@ResponseBody
	public Response deleteLeave(@RequestBody EmployeeLeaves employeeLeaves, HttpServletRequest request){
    	if(employeeLeaves.getRequestType().equals(LeaveType.WFH.getLeaveType())
    			||employeeLeaves.getRequestType().equals(LeaveType.MATERNITY_WFH.getLeaveType())
    			||employeeLeaves.getRequestType().equals(LeaveType.PATERNITY_WFH.getLeaveType())){
    		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.deleteWfh(employeeLeaves, PerfUtils.getUserId(request.getSession())));
    	} else
    		return  ResponseHandlingUtil.prepareResponse(employeeLeavesService.deleteLeave(employeeLeaves, PerfUtils.getUserId(request.getSession())));
	}
}
