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
@Table(name = "sp_ridm_risks_issues_dependency_tracking")
public class RiskIssuesDependencyTracking extends AbstractModel{
	
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

	@Column(name="risk_management_owner")
	private String riskMgmtOwn;
	
	@Column(name="risk_tracking_system")
	private String riskTrkSys;
	
	@Column(name="enable_2_0_risk_process_or_tailored")
	private String enable2RiskProcess;
	
	@Column(name="criteria_for_updating_risk_in_PMO_dashboard")
	private String criteriaforRiskPMO;
	
	@Column(name="risk_tracking_frequency")
	private String riskTrkFreq;
	
	@Column(name="organization_risk_included")
	private String orgRiskIncl;
	
	@Column(name="known_risks")
	private String knownRisk;
	
	@Column(name="comments")
	private String comments;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;

	
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Long getProjectAuditId() {
		return projectAuditId;
	}

	public void setProjectAuditId(Long projectAuditId) {
		this.projectAuditId = projectAuditId;
	}
	
	public Long getProcess_pk() {
		return process_pk;
	}

	public void setProcess_pk(Long process_pk) {
		this.process_pk = process_pk;
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

	public String getRiskMgmtOwn() {
		return riskMgmtOwn;
	}

	public void setRiskMgmtOwn(String riskMgmtOwn) {
		this.riskMgmtOwn = riskMgmtOwn;
	}

	public String getRiskTrkSys() {
		return riskTrkSys;
	}

	public void setRiskTrkSys(String riskTrkSys) {
		this.riskTrkSys = riskTrkSys;
	}

	public String getEnable2RiskProcess() {
		return enable2RiskProcess;
	}

	public void setEnable2RiskProcess(String enable2RiskProcess) {
		this.enable2RiskProcess = enable2RiskProcess;
	}

	public String getCriteriaforRiskPMO() {
		return criteriaforRiskPMO;
	}

	public void setCriteriaforRiskPMO(String criteriaforRiskPMO) {
		this.criteriaforRiskPMO = criteriaforRiskPMO;
	}

	public String getRiskTrkFreq() {
		return riskTrkFreq;
	}

	public void setRiskTrkFreq(String riskTrkFreq) {
		this.riskTrkFreq = riskTrkFreq;
	}

	public String getOrgRiskIncl() {
		return orgRiskIncl;
	}

	public void setOrgRiskIncl(String orgRiskIncl) {
		this.orgRiskIncl = orgRiskIncl;
	}

	public String getKnownRisk() {
		return knownRisk;
	}

	public void setKnownRisk(String knownRisk) {
		this.knownRisk = knownRisk;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}