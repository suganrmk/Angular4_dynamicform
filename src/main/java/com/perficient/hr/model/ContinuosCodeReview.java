package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@SuppressWarnings("serial")
@Entity
@Table(name = "SP_PR_CONTINUOUS_CR")
public class ContinuosCodeReview  extends AbstractModel{

	
	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name = "process_pk")
	private Long process_pk;
	
	
	@Column(name="project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign",parameters= @Parameter(name="property", value="projectAudit"))
	private Long projectAuditId;
	
	@Column(name="applicable")
	private boolean applicable;
	
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name="review_status")
	private String reviewStatus;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="team_peer_review")
	private String teamPeerReview;
	
	@Column(name="review_analysis")
	private String reviewAnalysis;
	
	@Column(name="review_from_gdc")
	private String reviewFromGDC;
	
	@Column(name="enable_latest_review_template")
	private String enableLatestReviewTemplate;
	
	@Column(name="review_comment_tracking")
	private String reviewCommentTracking;
	
	@Column(name="automated")
	private String automated;
	
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;

	
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Long getProcess_pk() {
		return process_pk;
	}

	public void setProcess_pk(Long process_pk) {
		this.process_pk = process_pk;
	}

	public Long getProjectAuditId() {
		return projectAuditId;
	}

	public void setProjectAuditId(Long projectAuditId) {
		this.projectAuditId = projectAuditId;
	}

	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}


	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getTeamPeerReview() {
		return teamPeerReview;
	}

	public void setTeamPeerReview(String teamPeerReview) {
		this.teamPeerReview = teamPeerReview;
	}

	public String getReviewAnalysis() {
		return reviewAnalysis;
	}

	public void setReviewAnalysis(String reviewAnalysis) {
		this.reviewAnalysis = reviewAnalysis;
	}

	public String getReviewFromGDC() {
		return reviewFromGDC;
	}

	public void setReviewFromGDC(String reviewFromGDC) {
		this.reviewFromGDC = reviewFromGDC;
	}

	public String getEnableLatestReviewTemplate() {
		return enableLatestReviewTemplate;
	}

	public void setEnableLatestReviewTemplate(String enableLatestReviewTemplate) {
		this.enableLatestReviewTemplate = enableLatestReviewTemplate;
	}

	public String getReviewCommentTracking() {
		return reviewCommentTracking;
	}

	public void setReviewCommentTracking(String reviewCommentTracking) {
		this.reviewCommentTracking = reviewCommentTracking;
	}

	public String getAutomated() {
		return automated;
	}

	public void setAutomated(String automated) {
		this.automated = automated;
	}

	/*public ProjectAudit getProjectAudit() {
		return projectAudit;
	}

	public void setProjectAudit(ProjectAudit projectAudit) {
		this.projectAudit = projectAudit;
	}*/
	
	
		
	
}
