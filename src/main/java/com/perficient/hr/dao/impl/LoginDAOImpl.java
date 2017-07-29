package com.perficient.hr.dao.impl;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {
			 
	protected Logger logger = LoggerFactory.getLogger(LoginDAO.class);
	
	@Override
    public int checkLogin(String userName, String userPwd, Session session) throws RecordNotFoundException {
		String sqlQuery ="select e.pk from employee e left join employee_login o on o.employee_pk=e.pk where o.email_id=:email "
				+ " and o.pwd=:pwd and e.active=:active and e.flag=:flag";
		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.setParameter("email", userName);
		query.setParameter("pwd", userPwd);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("flag", PerfHrConstants.ACTIVE);
		return (int)query.uniqueResult();
    }
	
	@Override
    public User checkUserLogin(String userName, String userPwd, Session session) throws RecordNotFoundException {
		String sqlQuery ="from User o where o.emailId=:email and o.pwd=:pwd";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("email", userName);
		query.setParameter("pwd", userPwd);
		return (User) query.uniqueResult();
    }

	@Override
	public boolean updatePwd(User user, Session session) {
		session.merge(user);
		return true;
	}

	@Override
	public User addEmployeeLogin(User user, Session session) {
		session.save(user);
		return user;
	}

	@Override
	public User loadUserByEmpPk(String employeePk, Session session) {
		String sqlQuery ="from User o where o.employeePk=:employeePk";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("employeePk", Long.parseLong(employeePk));
		return (User) query.uniqueResult();
	}
	
}