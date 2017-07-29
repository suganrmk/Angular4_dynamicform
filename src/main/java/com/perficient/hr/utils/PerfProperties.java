package com.perficient.hr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerfProperties {
	
	@Value("${email.host}")
	private String host;
	
	@Value("${email.port}")
	private String port;
	
	@Value("${email.username}")
	private String username;
	
	@Value("${email.password}")
	private String password;
	
	@Value("${ptoStoreLoc}")
	private String ptoStoreLoc;
	
	@Value("${ticketsStoreLocation}")
	private String ticketsStoreLocation;
	
	@Value("${ptoCount}")
	private String ptoCount;
	
	@Value("${wfhCount}")
	private String wfhCount;
	
	@Value("${startYear}")
	private String startYear;
	
	@Value("${timeout}")
	private String timeout;
	
	@Value("${sendMail}")
	private String sendMail;
	
	@Value("${maternityCount}")
	private String maternityCount;
	
	@Value("${supportEmail}")
	private String supportEmail;
	
	@Value("${ptoEmailList}")
	private String ptoEmailList;
	
	@Value("${wfhEmailList}")
	private String wfhEmailList;
	
	@Value("${emailTemplate}")
	private String emailTemplate;
	
	@Value("${emailTitle}")
	private String emailTitle;
	
	@Value("${emailFooter}")
	private String emailFooter;
	
	@Value("${newUserEmail}")
	private String newUserEmail;
	
	@Value("${minSupportedVersion}")
	private String minSupportedVersion;
	
	@Value("${interviewEmail}")
	private String interviewEmail;
	
	@Value("${interviewPass}")
	private String interviewPass;
	
	@Value("${paternityWfhCount}")
	private String paternityWfhCount;
	
	@Value("${maternityWfhCount}")
	private String maternityWfhCount;
	
	@Value("${notifyAsApprover}")
	private String notifyAsApprover;
	
	@Value("${wfhLimit}")
	private String wfhLimit;
	
	@Value("${wfhContinuousLimit}")
	private String wfhContinuousLimit;
	
	@Value("${ptoLimit}")
	private String ptoLimit;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the ptoStoreLoc
	 */
	public String getPtoStoreLoc() {
		return ptoStoreLoc;
	}

	/**
	 * @param ptoStoreLoc the ptoStoreLoc to set
	 */
	public void setPtoStoreLoc(String ptoStoreLoc) {
		this.ptoStoreLoc = ptoStoreLoc;
	}

	/**
	 * @return the ticketsStoreLocation
	 */
	public String getTicketsStoreLocation() {
		return ticketsStoreLocation;
	}

	/**
	 * @param ticketsStoreLocation the ticketsStoreLocation to set
	 */
	public void setTicketsStoreLocation(String ticketsStoreLocation) {
		this.ticketsStoreLocation = ticketsStoreLocation;
	}

	/**
	 * @return the ptoCount
	 */
	public String getPtoCount() {
		return ptoCount;
	}

	/**
	 * @param ptoCount the ptoCount to set
	 */
	public void setPtoCount(String ptoCount) {
		this.ptoCount = ptoCount;
	}

	/**
	 * @return the wfhCount
	 */
	public String getWfhCount() {
		return wfhCount;
	}

	/**
	 * @param wfhCount the wfhCount to set
	 */
	public void setWfhCount(String wfhCount) {
		this.wfhCount = wfhCount;
	}

	/**
	 * @return the startYear
	 */
	public String getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the sendMail
	 */
	public String getSendMail() {
		return sendMail;
	}

	/**
	 * @param sendMail the sendMail to set
	 */
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * @return the maternityCount
	 */
	public String getMaternityCount() {
		return maternityCount;
	}

	/**
	 * @param maternityCount the maternityCount to set
	 */
	public void setMaternityCount(String maternityCount) {
		this.maternityCount = maternityCount;
	}

	/**
	 * @return the supportEmail
	 */
	public String getSupportEmail() {
		return supportEmail;
	}

	/**
	 * @param supportEmail the supportEmail to set
	 */
	public void setSupportEmail(String supportEmail) {
		this.supportEmail = supportEmail;
	}

	/**
	 * @return the ptoEmailList
	 */
	public String getPtoEmailList() {
		return ptoEmailList;
	}

	/**
	 * @param ptoEmailList the ptoEmailList to set
	 */
	public void setPtoEmailList(String ptoEmailList) {
		this.ptoEmailList = ptoEmailList;
	}

	/**
	 * @return the wfhEmailList
	 */
	public String getWfhEmailList() {
		return wfhEmailList;
	}

	/**
	 * @param wfhEmailList the wfhEmailList to set
	 */
	public void setWfhEmailList(String wfhEmailList) {
		this.wfhEmailList = wfhEmailList;
	}

	/**
	 * @return the emailTemplate
	 */
	public String getEmailTemplate() {
		return emailTemplate;
	}

	/**
	 * @param emailTemplate the emailTemplate to set
	 */
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	/**
	 * @return the emailTitle
	 */
	public String getEmailTitle() {
		return emailTitle;
	}

	/**
	 * @param emailTitle the emailTitle to set
	 */
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	/**
	 * @return the emailFooter
	 */
	public String getEmailFooter() {
		return emailFooter;
	}

	/**
	 * @param emailFooter the emailFooter to set
	 */
	public void setEmailFooter(String emailFooter) {
		this.emailFooter = emailFooter;
	}

	/**
	 * @return the newUserEmail
	 */
	public String getNewUserEmail() {
		return newUserEmail;
	}

	/**
	 * @param newUserEmail the newUserEmail to set
	 */
	public void setNewUserEmail(String newUserEmail) {
		this.newUserEmail = newUserEmail;
	}

	/**
	 * @return the minSupportedVersion
	 */
	public String getMinSupportedVersion() {
		return minSupportedVersion;
	}

	/**
	 * @param minSupportedVersion the minSupportedVersion to set
	 */
	public void setMinSupportedVersion(String minSupportedVersion) {
		this.minSupportedVersion = minSupportedVersion;
	}

	/**
	 * @return the interviewEmail
	 */
	public String getInterviewEmail() {
		return interviewEmail;
	}

	/**
	 * @param interviewEmail the interviewEmail to set
	 */
	public void setInterviewEmail(String interviewEmail) {
		this.interviewEmail = interviewEmail;
	}

	/**
	 * @return the interviewPass
	 */
	public String getInterviewPass() {
		return interviewPass;
	}

	/**
	 * @param interviewPass the interviewPass to set
	 */
	public void setInterviewPass(String interviewPass) {
		this.interviewPass = interviewPass;
	}

	/**
	 * @return the paternityWfhCount
	 */
	public String getPaternityWfhCount() {
		return paternityWfhCount;
	}

	/**
	 * @param paternityWfhCount the paternityWfhCount to set
	 */
	public void setPaternityWfhCount(String paternityWfhCount) {
		this.paternityWfhCount = paternityWfhCount;
	}

	/**
	 * @return the maternityWfhCount
	 */
	public String getMaternityWfhCount() {
		return maternityWfhCount;
	}

	/**
	 * @param maternityWfhCount the maternityWfhCount to set
	 */
	public void setMaternityWfhCount(String maternityWfhCount) {
		this.maternityWfhCount = maternityWfhCount;
	}

	/**
	 * @return the notifyAsApprover
	 */
	public String getNotifyAsApprover() {
		return notifyAsApprover;
	}

	/**
	 * @param notifyAsApprover the notifyAsApprover to set
	 */
	public void setNotifyAsApprover(String notifyAsApprover) {
		this.notifyAsApprover = notifyAsApprover;
	}

	/**
	 * @return the wfhLimit
	 */
	public String getWfhLimit() {
		return wfhLimit;
	}

	/**
	 * @param wfhLimit the wfhLimit to set
	 */
	public void setWfhLimit(String wfhLimit) {
		this.wfhLimit = wfhLimit;
	}

	/**
	 * @return the wfhContinuousLimit
	 */
	public String getWfhContinuousLimit() {
		return wfhContinuousLimit;
	}

	/**
	 * @param wfhContinuousLimit the wfhContinuousLimit to set
	 */
	public void setWfhContinuousLimit(String wfhContinuousLimit) {
		this.wfhContinuousLimit = wfhContinuousLimit;
	}

	/**
	 * @return the ptoLimit
	 */
	public String getPtoLimit() {
		return ptoLimit;
	}

	/**
	 * @param ptoLimit the ptoLimit to set
	 */
	public void setPtoLimit(String ptoLimit) {
		this.ptoLimit = ptoLimit;
	}
	
}