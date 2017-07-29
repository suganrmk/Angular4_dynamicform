package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.form.EbsReport;
import com.perficient.hr.model.EbsEmployeeLeaves;
import com.perficient.hr.model.EmployeeLeaves;

public interface EbsEmployeeLeavesDAO {

	public EbsEmployeeLeaves saveEbsLeave(EbsEmployeeLeaves ebsEmployeeLeaves, Session session);
	
	public List<EbsReport> ebsLeaveReport(EmployeeLeaves employeeLeaves, Session session);
	
}
