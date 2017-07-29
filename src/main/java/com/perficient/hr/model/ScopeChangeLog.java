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
@Table(name = "sp_sc_change_log")
@SuppressWarnings("serial")
public class ScopeChangeLog extends AbstractModel {
	
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
	
	@Column(name = "change_log_management")
	private String chngLogMgmt;

	@Column(name = "change_log_owner")
	private String chngLogOwner;

	@Column(name = "change_approver")
	private String changeApprver;
	
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

	public String getChngLogMgmt() {
		return chngLogMgmt;
	}

	public void setChngLogMgmt(String chngLogMgmt) {
		this.chngLogMgmt = chngLogMgmt;
	}

	public String getChngLogOwner() {
		return chngLogOwner;
	}

	public void setChngLogOwner(String chngLogOwner) {
		this.chngLogOwner = chngLogOwner;
	}

	public String getChangeApprver() {
		return changeApprver;
	}

	public void setChangeApprver(String changeApprver) {
		this.changeApprver = changeApprver;
	}
	
}
	