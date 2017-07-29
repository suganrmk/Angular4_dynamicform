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

@Entity
@Table(name = "sp_sc_change_request")
@SuppressWarnings("serial")
public class ScopeChangeRequest extends AbstractModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name="process_pk", length = 10)
	private Long process_pk;
	
	@Column(name="project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign",parameters= @Parameter(name="property", value="projectAudit"))
	private Long projectAuditId;
		
	@Column(name="applicable", length = 1)
	private Boolean applicable;
	
	@Column(name="review_status")
	private String reviewStatus;
	
	@Column(name = "scope_change_tracking")
	private String scopeChngTrck;

	@Column(name = "owner")
	private String Owner;

	@Column(name = "change_approver")
	private String changeApprover;
	
	@Column(name = "backlog_updates")
	private String backlogUpdates;
	
	@Column(name = "changes_allowed_during_middle_of_sprint")
	private String chngAllwdMiddleSprint;
	
	@Column(name = "impact_will_include")
	private String impactWillIncl;
	
	@Column(name = "scope_change_managed_by_gdc_pm")
	private String scopeChngMngdGdcPm;
	
	@Column(name = "change_log_management")
	private String chngLogMgmt;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;

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
	
	public Boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(Boolean applicable) {
		this.applicable = applicable;
	}
	
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getScopeChngTrck() {
		return scopeChngTrck;
	}

	public void setScopeChngTrck(String scopeChngTrck) {
		this.scopeChngTrck = scopeChngTrck;
	}

	public String getOwner() {
		return Owner;
	}

	public void setOwner(String owner) {
		Owner = owner;
	}

	public String getChangeApprover() {
		return changeApprover;
	}

	public void setChangeApprover(String changeApprover) {
		this.changeApprover = changeApprover;
	}

	public String getBacklogUpdates() {
		return backlogUpdates;
	}

	public void setBacklogUpdates(String backlogUpdates) {
		this.backlogUpdates = backlogUpdates;
	}

	public String getChngAllwdMiddleSprint() {
		return chngAllwdMiddleSprint;
	}

	public void setChngAllwdMiddleSprint(String chngAllwdMiddleSprint) {
		this.chngAllwdMiddleSprint = chngAllwdMiddleSprint;
	}

	public String getImpactWillIncl() {
		return impactWillIncl;
	}

	public void setImpactWillIncl(String impactWillIncl) {
		this.impactWillIncl = impactWillIncl;
	}

	public String getScopeChngMngdGdcPm() {
		return scopeChngMngdGdcPm;
	}

	public void setScopeChngMngdGdcPm(String scopeChngMngdGdcPm) {
		this.scopeChngMngdGdcPm = scopeChngMngdGdcPm;
	}

	public String getChngLogMgmt() {
		return chngLogMgmt;
	}

	public void setChngLogMgmt(String chngLogMgmt) {
		this.chngLogMgmt = chngLogMgmt;
	}
	
}
	