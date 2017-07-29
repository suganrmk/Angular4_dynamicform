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
@Table(name = "sp_cm_cmplan")
@SuppressWarnings("serial")
public class ConfigMgmtCMPlan extends AbstractModel {
	
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
	
	@Column(name = "environment_details")
	private String envDetails;

	@Column(name = "configuration_items")
	private String configItems;

	@Column(name = "baseline_plan")
	private String baseLinePlan;
	
	@Column(name = "checkin_and_checkout_procedure")
	private String chkInChkOut;
	
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

	public String getEnvDetails() {
		return envDetails;
	}

	public void setEnvDetails(String envDetails) {
		this.envDetails = envDetails;
	}

	public String getConfigItems() {
		return configItems;
	}

	public void setConfigItems(String configItems) {
		this.configItems = configItems;
	}

	public String getBaseLinePlan() {
		return baseLinePlan;
	}

	public void setBaseLinePlan(String baseLinePlan) {
		this.baseLinePlan = baseLinePlan;
	}

	public String getChkInChkOut() {
		return chkInChkOut;
	}

	public void setChkInChkOut(String chkInChkOut) {
		this.chkInChkOut = chkInChkOut;
	}
		
}