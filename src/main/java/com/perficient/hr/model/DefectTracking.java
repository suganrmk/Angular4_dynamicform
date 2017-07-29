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
@Table(name = "sp_dm_defect_tracking")
public class DefectTracking extends AbstractModel{
	
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

	@Column(name="defect_management_by_GDC")
	private String defManagementByGDC;
	
	@Column(name="defect_tracking_tool")
	private String defTrackingTool;
	
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

	public String getDefManagementByGDC() {
		return defManagementByGDC;
	}

	public void setDefManagementByGDC(String defManagementByGDC) {
		this.defManagementByGDC = defManagementByGDC;
	}

	public String getDefTrackingTool() {
		return defTrackingTool;
	}

	public void setDefTrackingTool(String defTrackingTool) {
		this.defTrackingTool = defTrackingTool;
	}


	}