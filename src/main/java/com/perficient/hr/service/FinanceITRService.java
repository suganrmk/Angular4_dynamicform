package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.FinanceItrwindow;
import com.perficient.hr.model.FinanceTransaction;

public interface FinanceITRService {

	public Object saveEmpFinanceTxn(FinanceTransaction financeTxn);

	@PreAuthorize("@emprolesService.hasRoles('itrrept')")
	public Object getEmpFinanceStatusReport(String userId, String financialYear);

	public Object getUserFinanceStatus(String userId, String financialYr);

	public Object getFinancialYearDetails(String financialYr);

	@PreAuthorize("@emprolesService.hasRoles('itruwin')")
	public Object saveFinancialItrWindow(FinanceItrwindow itrwindow, String userId);

}
