package com.perficient.hr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.exception.GenericException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("emailTrackDAO")
public class EmailTrackDAOImpl implements EmailTrackDAO{

protected Logger logger = LoggerFactory.getLogger(EmailTrackDAOImpl.class);
	
	@Override
	public boolean saveEmailTrack(EmailTrack emailTrack, Session session) throws GenericException {
		session.save(emailTrack);
		return true;
	}

	@Override
	public boolean updateEmailTrack(EmailTrack emailTrack, Session session)  {
		session.merge(emailTrack);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailTrack> getEmailList(Session session) {
		String sql = "from EmailTrack where mailStatus in (:status)";
		Query query = session.createQuery(sql);
		List<String> statusList = new ArrayList<String>();
		statusList.add(PerfHrConstants.PENDING);
		query.setFirstResult(0);
		query.setMaxResults(25);
//		statusList.add(PerfHrConstants.CANCEL);
		query.setParameterList("status", statusList);
		List<EmailTrack> emailList = query.list();
		return emailList;
	}

	@Override
	public Object getEmailListByTypeAndStatus(String status, String mailType, Session session) {
		String sql = "from EmailTrack where mailStatus =:status and mailType=:mailType";
		Query query = session.createQuery(sql);
		query.setParameter("status", status);
		query.setParameter("mailType", mailType);
		return query.list();
	}
}
