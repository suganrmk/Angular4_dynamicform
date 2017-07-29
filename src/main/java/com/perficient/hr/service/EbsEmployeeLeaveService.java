package com.perficient.hr.service;

import org.springframework.stereotype.Repository;

import com.perficient.hr.model.EmployeeLeaves;

@Repository("ebsEmployeeLeavesService")
public interface EbsEmployeeLeaveService {

	public Object parseDocument(String fileName, String startYear, String totalLeaves);
	
	public Object loadEbsLeaveReport(EmployeeLeaves employeeLeaves);
}
