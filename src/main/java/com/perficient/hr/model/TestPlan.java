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
@Table(name = "sp_ast_test_plan")
public class TestPlan extends AbstractModel{
	
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

	@Column(name="test_plan_management")
	private String testPlanManagement;
	
	@Column(name="test_plan_owner")
	private String testPlanOwner;
	
	@Column(name="test_plan_tracking")
	private String testPlanTracking;
	
	@Column(name="test_plan_review")
	private String testPlanReview;
	
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

	public String getTestPlanManagement() {
		return testPlanManagement;
	}

	public void setTestPlanManagement(String testPlanManagement) {
		this.testPlanManagement = testPlanManagement;
	}

	public String getTestPlanOwner() {
		return testPlanOwner;
	}

	public void setTestPlanOwner(String testPlanOwner) {
		this.testPlanOwner = testPlanOwner;
	}

	public String getTestPlanTracking() {
		return testPlanTracking;
	}

	public void setTestPlanTracking(String testPlanTracking) {
		this.testPlanTracking = testPlanTracking;
	}

	public String getTestPlanReview() {
		return testPlanReview;
	}

	public void setTestPlanReview(String testPlanReview) {
		this.testPlanReview = testPlanReview;
	}

	}