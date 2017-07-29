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
@Table(name = "sp_pqpp_metrics_collection")
@SuppressWarnings("serial")
public class PqppMetricsCollection extends AbstractModel {
	
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
	
	@Column(name = "quality_metrics_goal_source")
	private String qualityMetricsGoal;
	
	@Column(name = "schedule_metrics_goal_source")
	private String schdulMetricsGoal;
	
	@Column(name = "productivity_metrics_goal_source")
	private String prodctvtyMetricsGoal;
	
	@Column(name = "test_metrics_goal_source")
	private String testMetricsGoal;
	
	@Column(name = "defect_metrics_goal_source")
	private String defectMetricsGoal;
	
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

	public String getQualityMetricsGoal() {
		return qualityMetricsGoal;
	}

	public void setQualityMetricsGoal(String qualityMetricsGoal) {
		this.qualityMetricsGoal = qualityMetricsGoal;
	}

	public String getSchdulMetricsGoal() {
		return schdulMetricsGoal;
	}

	public void setSchdulMetricsGoal(String schdulMetricsGoal) {
		this.schdulMetricsGoal = schdulMetricsGoal;
	}

	public String getProdctvtyMetricsGoal() {
		return prodctvtyMetricsGoal;
	}

	public void setProdctvtyMetricsGoal(String prodctvtyMetricsGoal) {
		this.prodctvtyMetricsGoal = prodctvtyMetricsGoal;
	}

	public String getTestMetricsGoal() {
		return testMetricsGoal;
	}

	public void setTestMetricsGoal(String testMetricsGoal) {
		this.testMetricsGoal = testMetricsGoal;
	}

	public String getDefectMetricsGoal() {
		return defectMetricsGoal;
	}

	public void setDefectMetricsGoal(String defectMetricsGoal) {
		this.defectMetricsGoal = defectMetricsGoal;
	}
	
}