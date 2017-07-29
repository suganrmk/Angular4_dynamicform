package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeRolesDAO;
import com.perficient.hr.dao.RolesDAO;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.RolesComponents;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("employeeRolesDAO")
public class EmployeeRolesDAOImpl implements EmployeeRolesDAO {

	protected Logger logger = LoggerFactory.getLogger(EmployeeRolesDAOImpl.class);
	
	@Autowired
    RolesDAO rolesDAO;
	
	@Override
	public EmployeeRoles saveEmpRoles(EmployeeRoles employeeRoles, Session session) {
		session.save(employeeRoles);
		return employeeRoles;
	}
	
	@Override
	public EmployeeRoles getEmpRolesByRoleandEmployeeId(String roleId, String userId, Session session){
		String sqlQuery =" from EmployeeRoles r where r.active=:active and r.roleId.pk=:roleId and r.employee.pk=:employee_pk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("roleId", Long.parseLong(roleId));
		query.setParameter("employee_pk", Long.parseLong(userId));
		return (EmployeeRoles) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeRoles> loadEmpRolesByRoles(String roleId, Session session) {
		String sqlQuery =" from EmployeeRoles r where r.active=:active and r.roleId.pk=:roleId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("roleId", Long.parseLong(roleId));
		return query.list();
	}

	@Override
	public RolesComponents loadRoleComponents(Long roleId, Session session) {
		String sqlQuery ="from RolesComponents r where r.active=:active and r.role.pk=:roleId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("roleId", roleId);
		return (RolesComponents) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeRoles> loadEmpRolesByEmployeeId(String userId, Session session) {
		String sqlQuery ="from EmployeeRoles as er where er.active=:active and er.employee.pk=:employee_pk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("employee_pk", Long.parseLong(userId));
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolesComponents> loadRoleCompList(List<Long> roleIds, Session session) {
		String sqlQuery ="from RolesComponents r where r.active=:active and r.role.pk in ('"+(roleIds).toString().replace("[", "").replace("]", "").replace(", ","','")+"')";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> loadEmpByRoleId(Long roleId, Session session) {
		String sqlQuery ="SELECT er.employee from EmployeeRoles as er where er.active=:active and er.roleId.pk=:roleId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter("roleId", roleId);
		return query.list();
	}
	
}
