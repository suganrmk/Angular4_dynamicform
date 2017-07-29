package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_feedback")
@SuppressWarnings("serial")
public class EmployeeInterviewFeedback extends AbstractModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "interviewerId")
	private Long interviewerId;
	
	@Column(name = "dateOfInterview")
	private Date dateOfInterview;
	
	@Column(name = "appliedPosition")
	private Long appliedDesignationId;
	
	@Column(name = "suggestedRole")
	private Long suggestedDesignationId;
	
	@Column(name = "feedback")
	private String feedback;
	
	@Column(name = "feedbackStatus")
	private String feedbackStatus;
	
	@Column(name = "interviewLevel")
	private String interviewLevel;
	
	@Column(name = "leadershipRating")
	private int leadershipRating;
	
	@Column(name = "leadershipComments")
	private String leadershipComments;
	
	@Column(name = "decisionRating")
	private int decisionRating;
	
	@Column(name = "decisionComments")
	private String decisionComments;

	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

	/**
	 * @return the employeeId
	 */
	public Long getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the interviewerId
	 */
	public Long getInterviewerId() {
		return interviewerId;
	}

	/**
	 * @param interviewerId the interviewerId to set
	 */
	public void setInterviewerId(Long interviewerId) {
		this.interviewerId = interviewerId;
	}

	/**
	 * @return the dateOfInterview
	 */
	public Date getDateOfInterview() {
		return dateOfInterview;
	}

	/**
	 * @param dateOfInterview the dateOfInterview to set
	 */
	public void setDateOfInterview(Date dateOfInterview) {
		this.dateOfInterview = dateOfInterview;
	}

	/**
	 * @return the apppliedDesignationId
	 */
	public Long getAppliedDesignationId() {
		return appliedDesignationId;
	}

	/**
	 * @param apppliedDesignationId the apppliedDesignationId to set
	 */
	public void setAppliedDesignationId(Long appliedDesignationId) {
		this.appliedDesignationId = appliedDesignationId;
	}

	/**
	 * @return the suggestedDesignationId
	 */
	public Long getSuggestedDesignationId() {
		return suggestedDesignationId;
	}

	/**
	 * @param suggestedDesignationId the suggestedDesignationId to set
	 */
	public void setSuggestedDesignationId(Long suggestedDesignationId) {
		this.suggestedDesignationId = suggestedDesignationId;
	}

	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * @return the feedbackStatus
	 */
	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	/**
	 * @param feedbackStatus the feedbackStatus to set
	 */
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	/**
	 * @return the interviewLevel
	 */
	public String getInterviewLevel() {
		return interviewLevel;
	}

	/**
	 * @param interviewLevel the interviewLevel to set
	 */
	public void setInterviewLevel(String interviewLevel) {
		this.interviewLevel = interviewLevel;
	}

	/**
	 * @return the leadershipRating
	 */
	public int getLeadershipRating() {
		return leadershipRating;
	}

	/**
	 * @param leadershipRating the leadershipRating to set
	 */
	public void setLeadershipRating(int leadershipRating) {
		this.leadershipRating = leadershipRating;
	}

	/**
	 * @return the leadershipComments
	 */
	public String getLeadershipComments() {
		return leadershipComments;
	}

	/**
	 * @param leadershipComments the leadershipComments to set
	 */
	public void setLeadershipComments(String leadershipComments) {
		this.leadershipComments = leadershipComments;
	}

	/**
	 * @return the decisionRating
	 */
	public int getDecisionRating() {
		return decisionRating;
	}

	/**
	 * @param decisionRating the decisionRating to set
	 */
	public void setDecisionRating(int decisionRating) {
		this.decisionRating = decisionRating;
	}

	/**
	 * @return the decisionComments
	 */
	public String getDecisionComments() {
		return decisionComments;
	}

	/**
	 * @param decisionComments the decisionComments to set
	 */
	public void setDecisionComments(String decisionComments) {
		this.decisionComments = decisionComments;
	}
	
}

