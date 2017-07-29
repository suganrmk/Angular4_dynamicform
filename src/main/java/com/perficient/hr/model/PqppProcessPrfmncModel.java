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
@Table(name = "sp_pqpp_process_performance_model")
@SuppressWarnings("serial")
public class PqppProcessPrfmncModel extends AbstractModel {
	
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
	
	@Column(name = "project_improvement_plan")
	private String prjctImprvmntPlan;
	
	@Column(name = "sqa_audit_cycle")
	private String sqaAuditCycle;
	
	@Column(name = "work_product_audit")
	private String wrkPrdctAudit;
	
	@Column(name = "internal_process_audit")
	private String intrnlProcessAudit;
	
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

	public String getPrjctImprvmntPlan() {
		return prjctImprvmntPlan;
	}

	public void setPrjctImprvmntPlan(String prjctImprvmntPlan) {
		this.prjctImprvmntPlan = prjctImprvmntPlan;
	}

	public String getSqaAuditCycle() {
		return sqaAuditCycle;
	}

	public void setSqaAuditCycle(String sqaAuditCycle) {
		this.sqaAuditCycle = sqaAuditCycle;
	}

	public String getWrkPrdctAudit() {
		return wrkPrdctAudit;
	}

	public void setWrkPrdctAudit(String wrkPrdctAudit) {
		this.wrkPrdctAudit = wrkPrdctAudit;
	}

	public String getIntrnlProcessAudit() {
		return intrnlProcessAudit;
	}

	public void setIntrnlProcessAudit(String intrnlProcessAudit) {
		this.intrnlProcessAudit = intrnlProcessAudit;
	}
	
}