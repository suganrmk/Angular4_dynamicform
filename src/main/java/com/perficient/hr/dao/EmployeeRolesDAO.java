package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeRoles;
import com.perficient.hr.model.RolesComponents;

public interface EmployeeRolesDAO {

	public EmployeeRoles saveEmpRoles(EmployeeRoles employeeRoles, Session session);
	
	public List<EmployeeRoles> loadEmpRolesByRoles(String roleId, Session session);
	
	public List<EmployeeRoles> loadEmpRolesByEmployeeId(String userId, Session session);
	
	public EmployeeRoles getEmpRolesByRoleandEmployeeId(String roleId, String userId, Session session);
	
	public RolesComponents loadRoleComponents(Long roleId, Session session);
	
	public List<RolesComponents> loadRoleCompList(List<Long> roleIds, Session session);
	
	public List<Employee> loadEmpByRoleId(Long roleId, Session session);
}