package com.perficient.hr.service;

import org.hibernate.Session;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.User;

public interface LoginService {    
	
	public Long checkLogin(String userName, String salt, String iv, String passphrase, String ciphertext) throws Exception;
	
	public User checkUserLogin(String userName, String salt, String iv, String passphrase, String ciphertext) throws Exception;
	
	public void createPasswordForEmployees() throws Exception;
	
	public void createUser(Employee employee, Session session) throws Exception;
	
	public Object forgotPwdTicket(String email) throws Exception;
	
}