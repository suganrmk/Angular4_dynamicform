package com.perficient.hr.dao;


import org.hibernate.Session;

public interface PtdDAO {
	public Object save(Object obj, Session session);
	public Object loadPtd(String projectId, String auditVersion,Session session);
	public Object loadProcess(Session session);
	public Object merge(Object obj, Session session);
}
