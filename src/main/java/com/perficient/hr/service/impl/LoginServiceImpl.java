package com.perficient.hr.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmailTrackDAO;
import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.LoginDAO;
import com.perficient.hr.exception.PrftException;
import com.perficient.hr.model.EmailTrack;
import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeLoginHistory;
import com.perficient.hr.model.Tickets;
import com.perficient.hr.model.User;
import com.perficient.hr.model.type.MailMediaType;
import com.perficient.hr.model.type.MailType;
import com.perficient.hr.service.LoginService;
import com.perficient.hr.service.TicketsService;
import com.perficient.hr.utils.AesUtil;
import com.perficient.hr.utils.ExceptionHandlingUtil;
import com.perficient.hr.utils.LoggerUtil;
import com.perficient.hr.utils.PerfHrConstants;
import com.perficient.hr.utils.PerfProperties;
import com.perficient.hr.utils.RandomPasswordGenerator;

@Repository("loginService")
public class LoginServiceImpl implements LoginService {
			 
	protected Logger logger = LoggerFactory.getLogger(LoginService.class);
	
    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;
    
    @Autowired
    private LoginDAO loginDAO;
    
    @Autowired
    private EmployeeDAO employeeDAO;
    
    @Autowired
    private EmailTrackDAO emailTrackDAO;

    @Autowired
    private PerfProperties perfProperties;
    
    @Autowired
    TicketsService ticketsService;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }
  
    protected Session getSession(){
       return sessionFactory.openSession();
    }

	@Override
    public Long checkLogin(String userName, String salt, String iv, String passphrase, String ciphertext) throws Exception{
		LoggerUtil.infoLog(logger, "Service to Check Login for the User : "+userName);
		Session session = null;
		Long userPk = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			AesUtil aesUtil = new AesUtil(128, 1000);
			Employee emp = employeeDAO.loadByEmail(userName, session);
			/*if(emp != null){
				if(!emp.isAdlogin()){
					verifyCredentials(userName, aesUtil.decrypt(salt, iv, passphrase, ciphertext));
					userPk = emp.getPk();
				} else {
					userPk = (long) loginDAO.checkLogin(userName, AesUtil.getSecurePassword( aesUtil.decrypt(salt, iv, passphrase, ciphertext),
			        		AesUtil.hexStringToByteArray(PerfHrConstants.PASS_KEY)), session);		
				}
			}
			if(userPk != null){
				EmployeeLoginHistory elh = new EmployeeLoginHistory();
				elh.setEmployeeId(userPk);
				elh.setLoginTime(new Date());
				session.save(elh);
				tx.commit();
			}*/
			userPk = emp.getPk();
			return userPk;
		} catch (Exception e) {
			ExceptionHandlingUtil.transactionRollback(tx);
			LoggerUtil.errorLog(logger, "Unable to check user login credentials for the User: "+userName , e);
			throw e;
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
    }
	
	private static boolean verifyCredentials(String email, String password) throws MessagingException{
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.user", email);
		props.put("mail.smtp.port", "25");
		props.setProperty("mail.debug", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		
		javax.mail.Session session = javax.mail.Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
		Transport transport = session.getTransport("smtp");
        transport.connect();
		return true;
	}
	
	@Override
    public User checkUserLogin(String userName, String salt, String iv, String passphrase, String ciphertext) throws Exception{
		LoggerUtil.infoLog(logger, "Service to Check Login for the User : "+userName);
		Session session = null;
		User user = null;
		try {
			session = sessionFactory.openSession();
			AesUtil aesUtil = new AesUtil(128, 1000);
			user = loginDAO.checkUserLogin(userName, AesUtil.getSecurePassword( aesUtil.decrypt(salt, iv, passphrase, ciphertext),
	        		AesUtil.hexStringToByteArray(PerfHrConstants.PASS_KEY)), session);
			return user;
		} catch (Exception e) {
			LoggerUtil.errorLog(logger, "Unable to check user login credentials for the User: "+userName , e);
			throw e;
		} finally {
			ExceptionHandlingUtil.closeSession(session);
		}
    }

	@Override
	public void createPasswordForEmployees() throws Exception {
		LoggerUtil.infoLog(logger, "Service to Create Password started ");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			for(Employee emp: employeeDAO.loadAllEmployees(session)){
				if(emp.getLastWorkDate() == null)
					createUser(emp, session);
			}
			tx.commit();
		} catch (Exception e ) {
			ExceptionHandlingUtil.transactionRollback(tx);
			LoggerUtil.errorLog(logger, "Service to Create Password completed.", e);
			throw e;
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
	@Override
	public void createUser(Employee emp, Session session) throws Exception{
		User empLogin = new User();
		empLogin.setEmailId(emp.getEmail());
		empLogin.setEmployeePk(emp.getPk());
		empLogin.setEmpLock(PerfHrConstants.ACTIVE);
		char[] pswd = RandomPasswordGenerator.generatePswd(8, 10,
                0, 1, 2);
		empLogin.setPwd(AesUtil.getSecurePassword(new String(pswd),
				AesUtil.hexStringToByteArray(PerfHrConstants.PASS_KEY)));
		empLogin.setCreatedBy((long)(1));
		empLogin.setModifiedBy((long)(1));
		empLogin.setDtModified(new Date());
		empLogin.setDtCreated(new Date());
		loginDAO.addEmployeeLogin(empLogin, session);
		EmailTrack emailTrack = new EmailTrack();
		emailTrack.setIdGeneric(empLogin.getPk());
		emailTrack.setMailType(MailType.SYSTEM.getMailType());
		emailTrack.setMediaType(MailMediaType.HTML.getMailMediaType());
		emailTrack.setMailStatus(PerfHrConstants.SUCCESS);
		emailTrack.setSubject("Welcome "+emp.getLastName()+", "+emp.getFirstName()+"!");
		String emailTemplate = perfProperties.getEmailTemplate();
		String emailTitle = perfProperties.getEmailTitle().replace("{{title}}", "Welcome to Perficient-Work Force Management!.<br><br>");
		String newUserInvite = perfProperties.getNewUserEmail().replace("{{body}}", 
				"You can login with the following credentials until your account id migrated to office365:<br><br>"+
						"Username/Email: "+emp.getEmail()+"<br>"+
						"Password: "+new String(pswd)+"<br><br>"+
						"Click <a href='https://www.chennaiwfm.com'>here</a> to login or you can download"+ 
						" Android App from <a href='https://play.google.com/store/apps/details?id=com.perfhr.app&hl=en'>here</a>.<br><br>"+
						" For IOS users: Currently the IOS App is in the works and will be launched shortly, we will intimate once the App is up & running! </a>.<br><br>"+
						"If you have any questions, feel free to create a ticket at help desk.  Thanks.");
		emailTrack.setComments(emailTemplate.replace("{{title}}", emailTitle).replace("{{body}}", newUserInvite).replace("{{footer}}", ""));
		emailTrack.setPriority("5");
		emailTrack.setMailFrom(perfProperties.getUsername());
		emailTrack.setMailTo(emp.getEmail());
		emailTrack.setFlag(0);
		emailTrack.setActive(PerfHrConstants.ACTIVE);
		emailTrack.setCreatedBy((long)(1));
		emailTrack.setModifiedBy((long)(1));
		emailTrack.setDtModified(new Date());
		emailTrack.setDtCreated(new Date());
		emailTrackDAO.saveEmailTrack(emailTrack, session);
	}

	@Override
	public Object forgotPwdTicket(String email) throws Exception {
		LoggerUtil.infoLog(logger, "Service to forgot Password Ticket started ");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Employee emp = employeeDAO.loadByEmail(email, session);
			Tickets ticket = new Tickets();
			if(emp != null){
				Employee assignTo = employeeDAO.loadByEmail(perfProperties.getSupportEmail(), session);
				ticket.setLocation("CHEOP-5");
				ticket.setIssueType("WFM_Application");
				ticket.setDescription("Reset my password!");
				ticket.setStatus(PerfHrConstants.OPEN);
				ticket.setAssignedTo(assignTo.getPk());
				ticket.setCreatedBy(emp.getPk());
				ticket.setModifiedBy(emp.getPk());
				ticket.setDtCreated(new Date());
				ticket.setDtModified(new Date());
				ticket = (Tickets) ticketsService.addTicket(ticket, null, emp.getPk().toString());
				if(ticket == null){
					throw new PrftException();
				}
			} else {
				throw new PrftException();
			}
			return ticket;
		} catch (Exception e ) {
			LoggerUtil.errorLog(logger, "Service to forgot Password Ticket completed.", e);
			throw e;
		} finally{
			ExceptionHandlingUtil.closeSession(session);
		}
	}
	
}