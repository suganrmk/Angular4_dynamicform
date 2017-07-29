package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.ProjectMembers;

public interface ProjectMembersService {
	
	public Object loadAllProjectMembers();

	public Object loadProjectMembersByProjectId(String projectPk);
	
	public Object loadProjectMemberById(String projectMemberId);
	
	public Object loadMyProjects(String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('pmSvBtn')")
	public Object saveProjectMember(ProjectMembers projectMembers, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('pmUpBtn')")
	public Object updateProjectMember(ProjectMembers projectMembers, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('pmDlBtn')")
	public Object deleteProjectMember(ProjectMembers projectMembers, String userId);
	
}
