package com.perficient.hr.service.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.FinanceITRDAO;
import com.perficient.hr.form.StatusReportList;
import com.perficient.hr.model.FinanceItrwindow;
import com.perficient.hr.model.FinanceTransaction;
import com.perficient.hr.service.EmployeeRolesService;
import com.perficient.hr.service.FinanceITRService;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;

@Repository
public class FinanceITRServiceImpl implements FinanceITRService {
	protected Logger logger = LoggerFactory.getLogger(FinanceITRServiceImpl.class);

	@Autowired
	FinanceITRDAO financeITRDAO;

	@Autowired
	EmployeeRolesService empRoleService;

	@Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public Object saveEmpFinanceTxn(FinanceTransaction financeTxn) {
		LoggerUtil.infoLog(logger, "Service to save transaction is started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			financeITRDAO.saveTransaction(financeTxn, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to save transaction is completed");
			return "SUCCESS";
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to save transaction: ", e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save finance transaction of Employee ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getEmpFinanceStatusReport(String userId, String financialYear) {
		LoggerUtil.infoLog(logger, "Service to get finance transaction report is started");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Object returnVal = financeITRDAO.getEmpFinanceStatusReport(financialYear, session);
			LoggerUtil.infoLog(logger, "Service to get finance transaction report is completed");
			return (StatusReportList) returnVal;
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.errorLog(logger, "Unable to get finance transaction report: ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get finance transaction report ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}

	}

	@Override
	public Object getUserFinanceStatus(String userId, String financialYr) {
		LoggerUtil.infoLog(logger, "Service to get finance ITR documents status is started");
		LoggerUtil.infoLog(logger, "UserId: " + userId + ", financial year: " + financialYr);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Object returnVal = financeITRDAO.getUserFinanceStatus(userId, financialYr, session);
			LoggerUtil.infoLog(logger, "Service to get finance ITR documents status is completed");
			return (String) returnVal;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to get finance ITR documents status: ", e);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get finance ITR documents status", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

	@Override
	public Object getFinancialYearDetails(String financialYr) {
		LoggerUtil.infoLog(logger, "Service to get finance ITR year details is started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Object returnVal = financeITRDAO.getFinanceYearDetails(financialYr, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to get finance ITR year details is completed");
			return returnVal;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to get finance ITR year details: ", e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to get finance ITR year details", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}

	}

	@Override
	public Object saveFinancialItrWindow(FinanceItrwindow itrwindow, String userId) {
		LoggerUtil.infoLog(logger, "Service to save ITR window is started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			financeITRDAO.saveOrUpdateItrWindow(itrwindow, session);
			tx.commit();
			LoggerUtil.infoLog(logger, "Service to save ITR window is completed");
			return "SUCCESS";
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to save ITR window: ", e);
			ExceptionHandlingUtil.closeSessionOnError(session, tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to save ITR window: ", e);
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
	}

}
