package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "sp_tcdr_high_level_requirement")
public class HighLevelRequirement extends AbstractModel{
	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name="process_pk", length = 10)
	private Long process_pk;
	
	@Column(name = "project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign")
	private Long projectAuditId;
	
	@Column(name="review_status")
	private String reviewStatus;
	
	@Column(name="applicable")
	private boolean applicable;

	@Column(name="who_defines_hlr")
	private String whoDefinesHlr;
	
	@Column(name="requirement_analysis_method")
	private String reqAnalysisMethod;
	
	@Column(name="requirement_elicitation_method")
	private String reqElicitationMethod;
	
	@Column(name="requirement_definition_or_representation_method")
	private String reqDefOrRepMethod;
	
	@Column(name="requirement_clarification")
	private String reqClarification;
	
	@Column(name="requirement_owner")
	private String reqOwner;
	
	@Column(name="requirement_repository")
	private String reqRepository;
	
	@Column(name="requirement_tracking")
	private String reqTracking;

	@Column(name="requirement_review")
	private String reqReview;
	
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

	public void setProjectAuditId(Long projectAuditPk) {
		this.projectAuditId = projectAuditPk;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}

	public String getWhoDefinesHlr() {
		return whoDefinesHlr;
	}

	public void setWhoDefinesHlr(String whoDefinesHlr) {
		this.whoDefinesHlr = whoDefinesHlr;
	}

	public String getReqAnalysisMethod() {
		return reqAnalysisMethod;
	}

	public void setReqAnalysisMethod(String reqAnalysisMethod) {
		this.reqAnalysisMethod = reqAnalysisMethod;
	}

	public String getReqElicitationMethod() {
		return reqElicitationMethod;
	}

	public void setReqElicitationMethod(String reqElicitationMethod) {
		this.reqElicitationMethod = reqElicitationMethod;
	}

	public String getReqDefOrRepMethod() {
		return reqDefOrRepMethod;
	}

	public void setReqDefOrRepMethod(String reqDefOrRepMethod) {
		this.reqDefOrRepMethod = reqDefOrRepMethod;
	}

	public String getReqClarification() {
		return reqClarification;
	}

	public void setReqClarification(String reqClarification) {
		this.reqClarification = reqClarification;
	}

	public String getReqOwner() {
		return reqOwner;
	}

	public void setReqOwner(String reqOwner) {
		this.reqOwner = reqOwner;
	}

	public String getReqRepository() {
		return reqRepository;
	}

	public void setReqRepository(String reqRepository) {
		this.reqRepository = reqRepository;
	}

	public String getReqTracking() {
		return reqTracking;
	}

	public void setReqTracking(String reqTracking) {
		this.reqTracking = reqTracking;
	}

	public String getReqReview() {
		return reqReview;
	}

	public void setReqReview(String reqReview) {
		this.reqReview = reqReview;
	}
	
	}