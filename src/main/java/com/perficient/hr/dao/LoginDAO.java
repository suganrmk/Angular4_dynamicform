package com.perficient.hr.dao;

import org.hibernate.Session;

import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.User;

public interface LoginDAO {    
	
	public int checkLogin(String userName, String userPassword, Session session) throws RecordNotFoundException;
	
	public User checkUserLogin(String userName, String userPassword, Session session) throws RecordNotFoundException;
	
	public boolean updatePwd(User user, Session session);
	
	public User addEmployeeLogin(User user, Session session);
	
	public User loadUserByEmpPk(String employeePk, Session session);
}