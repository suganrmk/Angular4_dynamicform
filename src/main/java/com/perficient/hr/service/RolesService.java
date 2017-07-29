package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.Roles;

public interface RolesService {

    public Object loadRoles();
	
	public Object loadRolesById(String roleId);
	
	@PreAuthorize("@emprolesService.hasRoles('rlSvBtn')")
	public Object addRoles(Roles role, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('rlUpBtn')")
	public Object updateRoles(Roles role, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('rlDlBtn')")
	public Object deleteRoles(Roles role, String userId);
	
}
