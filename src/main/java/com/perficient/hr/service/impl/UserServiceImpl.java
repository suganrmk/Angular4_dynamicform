package com.perficient.hr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.exception.RecordNotFoundException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.User;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.service.UserService;
import com.perficient.hr.utils.AesUtil;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;
import com.perficient.hr.utils.RandomPasswordGenerator;

@Service("userService")
public class UserServiceImpl implements UserService{

	protected Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;
	
	@Autowired
	public LoginService loginService;
	
	@Autowired
	public LoginDAO loginDAO;
	
	@Autowired
    private EmailTrackDAO emailTrackDAO;

    @Autowired
    private PerfProperties perfProperties;
	
	@Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
	
	@Override
	public Object updatePwd(String newPwd, String employeeId, String salt, String iv, String passphrase, String ciphertext){
		LoggerUtil.infoLog(logger, "Add Designation Service Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(employeeId, session);
			User user = loginService.checkUserLogin(employee.getEmail(), salt, iv, passphrase, ciphertext);
			if(user != null){
				user.setPwd(AesUtil.getSecurePassword(new String(newPwd),
						AesUtil.hexStringToByteArray(PerfHrConstants.PASS_KEY)));
				user.setModifiedBy(employee.getPk());
				user.setDtModified(new Date());
				loginDAO.updatePwd(user, session);
				tx.commit();
			} else {
				throw new RecordNotFoundException();
			}
		} catch(RecordNotFoundException e){
			LoggerUtil.errorLog(logger, "Incorrect password provided", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Incorrect Current Password.", HttpStatus.PRECONDITION_FAILED.value());
		}  catch(Exception e){
			LoggerUtil.errorLog(logger, "Unable to update password for user: "+employeeId, e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to update password for user: "+employeeId, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);	
		}
		return true;
	}

	@Override
	public Object resetPwdByEmpId(String employeePk, String loggedUserPk) throws Exception {
		LoggerUtil.infoLog(logger, "Service to Create Password started ");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDAO.loadById(loggedUserPk, session);
			User user = loginDAO.loadUserByEmpPk(employeePk, session);
			if(user != null){
				char[] pswd = RandomPasswordGenerator.generatePswd(8, 10,
		                0, 1, 2);
				user.setPwd(AesUtil.getSecurePassword(new String(pswd),
						AesUtil.hexStringToByteArray(PerfHrConstants.PASS_KEY)));
				user.setModifiedBy(employee.getPk());
				user.setDtModified(new Date());
				loginDAO.updatePwd(user, session);
				EmailTrack emailTrack = new EmailTrack();
				emailTrack.setIdGeneric(user.getPk());
				emailTrack.setMailType(MailType.SYSTEM.getMailType());
				emailTrack.setMediaType(MailMediaType.HTML.getMailMediaType());
				emailTrack.setMailStatus(PerfHrConstants.PENDING);
				emailTrack.setSubject("Your Perficient WFM password has been reset!");
				String emailTemplate = perfProperties.getEmailTemplate();
				String emailTitle = perfProperties.getEmailTitle().replace("{{title}}", "Your WFM credentials has been reset as per request!<br><br>");
				String newUserInvite = perfProperties.getNewUserEmail().replace("{{body}}",
								"Please try the following credentials to access the application.<br><br>"+
								"Username/Email: "+user.getEmailId()+"<br>"+
								"Password: "+new String(pswd)+"<br><br>"+
								"If you have any questions, feel free to create a ticket at support page. Thanks.");
				emailTrack.setComments(emailTemplate.replace("{{title}}", emailTitle).replace("{{body}}", newUserInvite).replace("{{footer}}", ""));
				emailTrack.setPriority("5");
				emailTrack.setMailFrom(perfProperties.getUsername());
				emailTrack.setMailTo(user.getEmailId());
				emailTrack.setFlag(0);
				emailTrack.setActive(PerfHrConstants.ACTIVE);
				emailTrack.setCreatedBy((long)(1));
				emailTrack.setModifiedBy((long)(1));
				emailTrack.setDtModified(new Date());
				emailTrack.setDtCreated(new Date());
				emailTrackDAO.saveEmailTrack(emailTrack, session);
				tx.commit();
			}
		} catch (Exception e ) {
			LoggerUtil.errorLog(logger, "Service to reset Password completed.", e);
			ExceptionHandlingUtil.transactionRollback(tx);
			return ExceptionHandlingUtil.returnErrorObject("Unable to reset password for user: "+employeePk, e);
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
		return true;
	}
}
