package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.RolesComponents;

public interface EmployeeRolesService {

	@PreAuthorize("@emprolesService.hasRoles('erApBtn')")
	public Object saveEmpRoles(RolesComponents rolesComponents, String userId);
	
	public Object loadEmpByRoles(String roleId, String userId);
	
	public Object loadEmployeeRoles(String userId);
	
	public Object loadRolesByEmpId(String userId);
}
