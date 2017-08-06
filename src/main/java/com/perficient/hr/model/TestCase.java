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
@Table(name = "sp_ast_test_case")
public class TestCase extends AbstractModel{
	
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

	@Column(name="test_case_management")
	private String testCaseManagement;
	
	@Column(name="test_automation")
	private String testAutomation;
	
	@Column(name="test_case_mapping_to_requirement")
	private String testCaseMapToReq;
	
	
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

	public String getTestCaseManagement() {
		return testCaseManagement;
	}

	public void setTestCaseManagement(String testCaseManagement) {
		this.testCaseManagement = testCaseManagement;
	}

	public String getTestAutomation() {
		return testAutomation;
	}

	public void setTestAutomation(String testAutomation) {
		this.testAutomation = testAutomation;
	}

	public String getTestCaseMapToReq() {
		return testCaseMapToReq;
	}

	public void setTestCaseMapToReq(String testCaseMapToReq) {
		this.testCaseMapToReq = testCaseMapToReq;
	}

	}