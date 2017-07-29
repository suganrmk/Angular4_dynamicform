package com.perficient.hr.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.FinanceITRDAO;
import com.perficient.hr.form.Status;
import com.perficient.hr.form.StatusReport;
import com.perficient.hr.form.StatusReportList;
import com.perficient.hr.model.FinanceItrwindow;
import com.perficient.hr.model.FinanceTransaction;

@Repository
public class FinanceITRDAOImpl implements FinanceITRDAO {

	@Override
	public void saveTransaction(FinanceTransaction financeTxn, Session session) {
		session.saveOrUpdate(financeTxn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StatusReportList getEmpFinanceStatusReport(String financialYear, Session session) {
		String queryString = "select e.employee_id as employeeId, concat(e.firstname, ' ', e.lastname) as employeeName, e.email as email, f.financial_status as status "
				+ "from finance_transaction f left join employee e on e.pk=f.employee_pk where f.financial_year = :financeYear";
		Query query = session.createQuery(queryString);
		query.setParameter("financeYear", financialYear);
		query.setResultTransformer(Transformers.aliasToBean(StatusReport.class));
		StatusReportList statusReportList = new StatusReportList();
		statusReportList.setReportList(query.list());
		int notApplicable = (int) statusReportList.getReportList().stream()
				.filter(rs -> rs.getStatus().equals("NOT_APPLICABLE")).count();
		int completed = (int) statusReportList.getReportList().stream()
				.filter(rs -> rs.getStatus().equals("PROPOSED_COMPLETED")).count();
		int open = (int) statusReportList.getReportList().stream().filter(rs -> rs.getStatus().equals("OPEN")).count();
		Status status = new Status();
		status.setNotApplicable(notApplicable);
		status.setCompleted(completed);
		status.setOpen(open);
		statusReportList.setStatus(status);
		return statusReportList;
	}

	@Override
	public String getUserFinanceStatus(String userId, String financialYr, Session session) {
		String queryString = "select f.financialStatus from FinanceTransaction f where f.employeeId = :empId and f.financialYear = :financeYear";
		Query query = session.createQuery(queryString);
		query.setParameter("empId", Long.parseLong(userId));
		query.setParameter("financeYear", financialYr);
		return (String) query.uniqueResult();
	}

	@Override
	public FinanceItrwindow getFinanceYearDetails(String financialYear, Session session) {
		String queryString = "from FinanceItrwindow f where f.financialYear = :financeYear";
		Query query = session.createQuery(queryString);
		query.setParameter("financeYear", financialYear);
		return (FinanceItrwindow) query.uniqueResult();
	}

	@Override
	public void saveOrUpdateItrWindow(FinanceItrwindow itrwindow, Session session) {
		session.saveOrUpdate(itrwindow);
	}

}
