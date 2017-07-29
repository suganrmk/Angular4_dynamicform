package com.perficient.hr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EbsEmployeeLeavesDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.form.EbsReport;
import com.perficient.hr.model.EbsEmployeeLeaves;
import com.perficient.hr.model.EmployeeLeaves;
import com.perficient.hr.model.type.LeaveType;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("ebsEmployeeLeavesDAO")
public class EbsEmployeeLeavesDAOImpl implements EbsEmployeeLeavesDAO {

	protected Logger logger = LoggerFactory.getLogger(EbsEmployeeLeavesDAOImpl.class);
	
	@Autowired
	EmployeeDAO employeeDAO;

	@Override
	public EbsEmployeeLeaves saveEbsLeave(EbsEmployeeLeaves ebsEmployeeLeaves, Session session) {
		session.save(ebsEmployeeLeaves);
		return ebsEmployeeLeaves;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EbsReport> ebsLeaveReport(EmployeeLeaves employeeLeaves, Session session) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String sqlQuery = "select a.*, b.* from "
				+ " (select e.pk  as pk, e.employee_id as employeeId, e.firstname as firstName, e.lastname as lastName, ld.leave_dt as appLeaveDate, "
				+ " el.hours as appLeaveHours, el.request_type as requestType"
				+ " from employee_leaves_details ld"
				+ " left join employee_leaves el on ld.employee_leaves_pk=el.pk"
				+ " left join employee e on e.pk=el.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where el.employee_pk=e.pk and el.request_type in ('"+LeaveType.PTO.getLeaveType()+"', '"+LeaveType.UNPLANNED_PTO.getLeaveType()+"','"+LeaveType.LOSS_OF_PAY.getLeaveType()+"','"+LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()+"','"+LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType()+"')"
				+ " and el.active='"+PerfHrConstants.ACTIVE+"' and ld.active='"+PerfHrConstants.ACTIVE+"'"
				+ " and ld.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by e.employee_id, ld.leave_dt) a"
				+ " left outer join "
				+ " (SELECT e.employee_id as ebsEmployeeId, e.firstname as ebsFirstName, e.lastname as ebsLastName, ebs.leave_dt as ebsLeaveDate, ebs.hours as ebsLeaveHours, "
				+ " ebs.employee_pk as pk , ebs.request_type as ebsRequestType FROM ebs_employee_leaves ebs "
				+ " left join employee e on e.pk=ebs.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where ebs.employee_pk = e.pk "
				+ " and ebs.active='"+PerfHrConstants.ACTIVE+"' "
				+ " and ebs.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by ebs.employee_pk, ebs.leave_dt) b on a.pk=b.pk  and a.appLeaveDate = b.ebsLeaveDate"
				+ " union "
				+ " select a.*, b.* from "
				+ " (select e.pk as pk, e.employee_id as employeeId, e.firstname as firstName, e.lastname as lastName, ld.leave_dt as appLeaveDate, "
				+ " el.hours as appLeaveHours, el.request_type as requestType"
				+ " from employee_leaves_details ld"
				+ " left join employee_leaves el on ld.employee_leaves_pk=el.pk"
				+ " left join employee e on e.pk=el.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where el.employee_pk=e.pk and el.request_type in ('"+LeaveType.PTO.getLeaveType()+"', '"+LeaveType.UNPLANNED_PTO.getLeaveType()+"','"+LeaveType.LOSS_OF_PAY.getLeaveType()+"','"+LeaveType.MATERNITY_PAID_LEAVE.getLeaveType()+"','"+LeaveType.MATERNITY_UNPAID_LEAVE.getLeaveType()+"')"
				+ " and el.active='"+PerfHrConstants.ACTIVE+"' and ld.active='"+PerfHrConstants.ACTIVE+"'"
				+ " and ld.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by e.employee_id, ld.leave_dt) a"
				+ " right outer join "
				+ " (SELECT e.employee_id as ebsEmployeeId, e.firstname as ebsFirstName, e.lastname as ebsLastName, ebs.leave_dt as ebsLeaveDate, ebs.hours as ebsLeaveHours,"
				+ " ebs.employee_pk as pk , ebs.request_type as ebsRequestType FROM ebs_employee_leaves ebs"
				+ " left join employee e on e.pk=ebs.employee_pk and e.pk in ("  +  employeeLeaves.getEmployeeReportList().toString().replace("[", "").replace("]", "") + ")"
				+ " where ebs.employee_pk = e.pk "
				+ " and ebs.active='"+PerfHrConstants.ACTIVE+"'"
				+ " and ebs.leave_dt between '"+sf.format(employeeLeaves.getStartsAt())+"' and '"+sf.format(employeeLeaves.getEndsAt())+"'"
				+ " group by ebs.employee_pk, ebs.leave_dt) b on a.pk=b.pk  and a.appLeaveDate = b.ebsLeaveDate";
		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.addScalar("employeeId", new StringType())
		.addScalar("firstName", new StringType())
		.addScalar("lastName", new StringType())
		.addScalar("appLeaveDate", new DateType())
		.addScalar("appLeaveHours", new LongType())
		.addScalar("ebsLeaveDate", new DateType())
		.addScalar("ebsLeaveHours", new LongType())
		.addScalar("ebsEmployeeId", new StringType())
		.addScalar("ebsFirstName", new StringType())
		.addScalar("ebsLastName", new StringType())
		.addScalar("requestType", new StringType())
		.addScalar("ebsRequestType", new StringType())
		.setResultTransformer(Transformers.aliasToBean(EbsReport.class));
		return query.list();
	}
	
}
