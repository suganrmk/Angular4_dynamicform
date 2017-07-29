package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

	public Object updatePwd(String newPwd, String employeeId, String salt, String iv, String passphrase, String ciphertext);
	
	@PreAuthorize("@emprolesService.hasRoles('rstpwdpg')")
	public Object resetPwdByEmpId(String employeePk, String loggedUserPk) throws Exception;
	
}
