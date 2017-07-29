package com.perficient.hr.dao;

import com.perficient.hr.model.EmployeeLoginHistory;

public interface EmployeeLoginHistoryDAO {

	public void saveLogin(EmployeeLoginHistory employeeLoginHistory) throws Exception;
	
	public void updLogin(EmployeeLoginHistory employeeLoginHistory) throws Exception;
	
}
