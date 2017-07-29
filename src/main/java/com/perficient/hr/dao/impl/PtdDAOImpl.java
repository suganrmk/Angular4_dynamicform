package com.perficient.hr.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.PtdDAO;
import com.perficient.hr.utils.PerfHrConstants;


@Repository("pTDReviewDAO")
public class PtdDAOImpl  implements PtdDAO{

	
	@Override
	public Object save(Object obj,Session session) {
		session.persist(obj); 
		return obj;
	}
	
	@Override    
	public Object loadPtd(String projectId, String auditVersion,Session session) {
		String sqlQuery =" from ProjectAudit a where a.pk=:pk and active=:active  and review_status=:status ";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("pk", Long.parseLong(projectId));
		query.setParameter(PerfHrConstants.ACTIVE_COLUMN, PerfHrConstants.ACTIVE);
		query.setParameter(PerfHrConstants.STATUS_COLUMN, PerfHrConstants.PTD_DRAFT);
		return query.uniqueResult();
	}

	@Override
	public Object loadProcess(Session session) {
		String sqlQuery =" from Process";
   		Query query = session.createQuery(sqlQuery);
		return query.list();
	}

	@Override
	public Object merge(Object obj, Session session) {
		session.merge(obj);
		return obj;
	}
	
	
}
