package com.perficient.hr.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.perficient.hr.service.EmployeeRolesService;
import com.perficient.hr.utils.PerfHrConstants;

@Component("emprolesService")
public class RolesValidator {

	@Autowired
	private EmployeeRolesService employeeRolesService;
	
	@Autowired
	HttpSession httpSession;
	
	@SuppressWarnings("unchecked")
	public Object hasRoles(Object roleKey) {
		String userId = httpSession.getAttribute(PerfHrConstants.USER_ID).toString();
		Map<String, String> roleMap = (Map<String, String>) employeeRolesService.loadEmployeeRoles(userId);
		if(roleMap.containsKey(roleKey)){
			return true;
		} else
			throw new AccessDeniedException("UnAuthorized Access. Please Contact System Administrator.");
	}
	
	@SuppressWarnings("unchecked")
	public Object hasEitherRoles(Object firstKey, Object secondKey) {
		String userId = httpSession.getAttribute(PerfHrConstants.USER_ID).toString();
		Map<String, String> roleMap = (Map<String, String>) employeeRolesService.loadEmployeeRoles(userId);
		if(roleMap.containsKey(firstKey) || roleMap.containsKey(secondKey)){
			return true;
		} else
			throw new AccessDeniedException("UnAuthorized Access. Please Contact System Administrator.");
	}
	
}
