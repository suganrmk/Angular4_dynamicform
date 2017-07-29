package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.Projects;

public interface ProjectService {

    public Object loadProjects();
	
    @PreAuthorize("@emprolesService.hasRoles('prSvBtn')")
	public Object addProject(Projects project, String userId);
	
    @PreAuthorize("@emprolesService.hasRoles('prUpBtn')")
	public Object updateProject(Projects project, String userId);
	
    @PreAuthorize("@emprolesService.hasRoles('prDlBtn')")
	public Object deleteProject(Projects project, String userId);
	
	public Object loadProjectById(String projectPk);
	
}
