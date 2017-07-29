package com.perficient.hr.dao;

import org.hibernate.Session;

import com.perficient.hr.form.StatusReportList;
import com.perficient.hr.model.FinanceItrwindow;
import com.perficient.hr.model.FinanceTransaction;

public interface FinanceITRDAO {

	public String getUserFinanceStatus(String userId, String financialYr, Session session);

	public void saveTransaction(FinanceTransaction financeTxn, Session session);

	public StatusReportList getEmpFinanceStatusReport(String financialYear, Session session);

	public FinanceItrwindow getFinanceYearDetails(String financialYear, Session session);

	public void saveOrUpdateItrWindow(FinanceItrwindow itrwindow, Session session);
}
