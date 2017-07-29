package com.perficient.hr.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeNamesView;
import com.perficient.hr.model.EmployeeView;
import com.perficient.hr.utils.DateUtils;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeDAO")
public class EmployeeDAOImpl implements EmployeeDAO{

	protected Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	
	private final static String SKIP_ADMIN = "and e.pk != 1";

    @Override
    public EmployeeView loadByUserId(String pk, Session session) {
    	String sqlQuery =" from EmployeeView as o where o.pk=:pk and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", Long.parseLong(pk));
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return (EmployeeView)query.uniqueResult();
	}

    @Override
   	public Employee loadById(String pk, Session session){
   		return (Employee)loadEmployee(pk, session).uniqueResult();
   	}
    
    @Override
   	public Employee loadByEmail(String email, Session session){
    	logger.info("Loading employee record for: "+email);
   		String sqlQuery =" from Employee as o where o.email=:email and o.active=:active and o.flag=:flag";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("email", email);
   		query.setParameter("flag", PerfHrConstants.ACTIVE);
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		return (Employee)query.uniqueResult();
   	}
    
    private Query loadEmployee(String pk, Session session){
    	logger.info("Loading employee record for: "+pk);
   		String sqlQuery =" from Employee as o where o.pk=:pk and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", Long.parseLong(pk));
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		return query;
    }
    
    @Override
   	public Employee loadByEmployeeId(String employeeId, Session session){
   		logger.info("Loading employee record for EmployeeId: "+employeeId);
   		String sqlQuery =" from Employee as o where o.employeeId=:employeeId and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("employeeId", employeeId);
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		return (Employee)query.uniqueResult();
   	}
    
    @Override
    public EmployeeNamesView loadUserNameById(String pk, Session session) {
    	String sqlQuery =" from EmployeeNamesView as o where o.pk=:pk and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", Long.parseLong(pk));
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return (EmployeeNamesView)query.uniqueResult();
	}
    
    @Override
    public EmployeeNamesView loadUserNameViewById(String employeeId, Session session) {
    	String sqlQuery =" from EmployeeNamesView as o where o.employeeId=:employeeId and o.active=:active";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("pk", employeeId);
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return (EmployeeNamesView)query.uniqueResult();
	}
    
    @Override
    public EmployeeNamesView loadUserNameViewByEmail(String email, Session session) {
    	String sqlQuery =" from EmployeeNamesView as o where o.email=:email and o.active=:active and o.flag=:flag";
   		Query query = session.createQuery(sqlQuery);
   		query.setParameter("email", email);
   		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
   		query.setParameter("flag", PerfHrConstants.ACTIVE);
		return (EmployeeNamesView)query.uniqueResult();
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<EmployeeView> loadEmployees(Session session){
		String sqlQuery =" from EmployeeView e where e.active=:active "+SKIP_ADMIN+" order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<EmployeeNamesView> loadAllEmployeeByNames(Session session){
		String sqlQuery ="from EmployeeNamesView e where e.active=:active and e.flag=:flag "+SKIP_ADMIN+" order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.ACTIVE);
		return query.list();
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<EmployeeNamesView> loadEmployeeNamesByDesignationId(String designationId, Session session){
		String sqlQuery ="from EmployeeNamesView e where e.active=:active and e.flag=:flag "+SKIP_ADMIN+" and e.designation=:designationId order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.ACTIVE);
		query.setParameter("designationId", Long.parseLong(designationId));
		return query.list();
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<EmployeeNamesView> loadAllEmployeeNamesByDate(Date stDate, Date endDate, Session session) {
    	String sqlQuery ="from EmployeeNamesView e where e.active=:active "+SKIP_ADMIN+" and e.joinDate <=:endDate "
    			+ " and ((e.flag=:flag) or (e.flag=:inactive and e.lastWorkDate>=:stDate)) order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.ACTIVE);
		query.setParameter("inactive", PerfHrConstants.INACTIVE);
		query.setParameter("endDate", new java.sql.Timestamp(endDate.getTime()));
		query.setParameter("stDate", new java.sql.Timestamp(stDate.getTime()));
		return query.list();
	}
    
	@Override
	public boolean updateEmployee(Employee employee, String userId, Session session) {
		session.merge(employee);
		return true;
	}

	@Override
	public boolean addEmployee(Employee employee, Session session){
		session.save(employee);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeView> loadEmployeeByDesHistory(String stDate,
			String endDate, String designationName, Session session){
		logger.info("Load Employees. Start Date : {}, End Date : {}, Designation : {}",
				stDate, endDate, designationName);
		Query query = session.createSQLQuery(
				"CALL getEmployeeDesignationByRange(:stDate, :endDate, :designationName)")
				.addEntity(EmployeeView.class)
				.setParameter("stDate", DateUtils.convertMilliSecondsToDate(stDate))
				.setParameter("endDate", DateUtils.convertMilliSecondsToDate(endDate))
				.setParameter("designationName", designationName);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> loadAllEmployees(Session session) {
		String sqlQuery =" from Employee e where e.active=:active order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> loadEmployeesByPk(Set<Long> empReportIds, Session session) {
		String sqlQuery =" from Employee e where e.active=:active and e.pk in ("+empReportIds.toString().replace("[", "").replace("]", "")+") order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeHierarchy(long pk, int limit, Date stDate, Date endDate, Session session) {
		String sqlQuery =" from Employee e where e.active=:active "+SKIP_ADMIN+" and e.supervisor=:supervisor and e.joinDate <=:endDate "
    			+ " and ((e.flag=:flag) or (e.flag=:inactive and e.lastWorkDate>=:stDate)) order by e.lastName asc";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("supervisor", pk);
		query.setParameter("flag", PerfHrConstants.ACTIVE);
		query.setParameter("inactive", PerfHrConstants.INACTIVE);
		query.setParameter("endDate", new java.sql.Timestamp(endDate.getTime()));
		query.setParameter("stDate", new java.sql.Timestamp(stDate.getTime()));
		return query.list();
	}

}
