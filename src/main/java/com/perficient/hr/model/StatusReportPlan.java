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
@Table(name = "sp_sr_status_report_plan")
public class StatusReportPlan extends AbstractModel{
	

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

	@Column(name="status_report_pattern")
	private String statusReportPattern;
	
	@Column(name="status_tracking")
	private String statusTracking;
	
	@Column(name="status_communication_mode")
	private String statusCommunicationMode;
	
	@Column(name="pmo_dashboard_update")
	private String pmoDashboardUpdate;
	
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

	public String getStatusReportPattern() {
		return statusReportPattern;
	}

	public void setStatusReportPattern(String statusReportPattern) {
		this.statusReportPattern = statusReportPattern;
	}

	public String getStatusTracking() {
		return statusTracking;
	}

	public void setStatusTracking(String statusTracking) {
		this.statusTracking = statusTracking;
	}

	public String getStatusCommunicationMode() {
		return statusCommunicationMode;
	}

	public void setStatusCommunicationMode(String statusCommunicationMode) {
		this.statusCommunicationMode = statusCommunicationMode;
	}

	public String getPmoDashboardUpdate() {
		return pmoDashboardUpdate;
	}

	public void setPmoDashboardUpdate(String pmoDashboardUpdate) {
		this.pmoDashboardUpdate = pmoDashboardUpdate;
	}

}
