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
@Table(name = "SP_PR_PEER_PLAN")
public class PeerReviewPlan  extends AbstractModel {

	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name = "process_pk")
	private Long process_pk;
	
	@Column(name = "project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign")
	//,parameters= @Parameter(name="property", value="prp")
	private Long projectAuditId;
	
	@Column(name = "review_status")
	private String reviewStatus;
	
	@Column(name = "applicable")
	private boolean applicable;
	
	@Column(name = "review_time_tracking")
	private String reviewTimeTracking;
	
	@Column(name = "review_criteria_defined_by")
	private String reviewCriteriaDefinedBy;
	
	@Column(name = "review_plan_tracking_system")
	private String reviewPalnTrackingSystem;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;


	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

/*	public ProjectAudit getProjectAudit() {
		return projectAudit;
	}

	public void setProjectAudit(ProjectAudit projectAudit) {
		this.projectAudit = projectAudit;
	}*/
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

	public void setReviewStatus(String status) {
		this.reviewStatus = status;
	}

	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}

	public String getReviewTimeTracking() {
		return reviewTimeTracking;
	}

	public void setReviewTimeTracking(String reviewTimeTracking) {
		this.reviewTimeTracking = reviewTimeTracking;
	}

	public String getReviewCriteriaDefinedBy() {
		return reviewCriteriaDefinedBy;
	}

	public void setReviewCriteriaDefinedBy(String reviewCriteriaDefinedBy) {
		this.reviewCriteriaDefinedBy = reviewCriteriaDefinedBy;
	}

	public String getReviewPalnTrackingSystem() {
		return reviewPalnTrackingSystem;
	}

	public void setReviewPalnTrackingSystem(String reviewPalnTrackingSystem) {
		this.reviewPalnTrackingSystem = reviewPalnTrackingSystem;
	}
	
	
	
	
}
