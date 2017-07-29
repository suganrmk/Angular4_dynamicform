package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.Designations;

public interface DesignationsService {
	
	public Object loadDesignations();
	
	public Object loadDesignationById(String designationId);
	
	public Object loadDesignationByName(String designationName);
	
	@PreAuthorize("@emprolesService.hasRoles('jtSvBtn')")
	public Object addDesignation(Designations designation, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('jtUpBtn')")
	public Object updateDesignation(Designations designation, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('jtDlBtn')")
	public Object deleteDesignation(Designations designation, String userId);
	
}
