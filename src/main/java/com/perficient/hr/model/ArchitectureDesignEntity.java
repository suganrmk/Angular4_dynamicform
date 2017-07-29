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
@Table(name = "SP_AD_ARCHITECTURE_DESIGN")
public class ArchitectureDesignEntity extends AbstractModel{
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name = "process_pk")
	private Long process_pk;
	
	public Long getProcess_pk() {
		return process_pk;
	}

	public void setProcess_pk(Long process_pk) {
		this.process_pk = process_pk;
	}

	@Column(name="review_status")
	private String status;
	
	@Column(name = "project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign")
	private Long projectAuditPK;
	
	@Column(name = "applicable")
	private boolean applicable;
	
	@Column(name = "gdc_involvement")
	private String gdcInvolvement;
	
	@Column(name = "low_level_design")
	private String lowLevelDesign;
	
	@Column(name = "design_repository")
	private String designRepository;
	
	@Column(name = "design_review_outcome")
	private String designReviewOutcome;
	
	@Column(name = "design_tool")
	private String designTool;
	
	@Column(name = "alternative_design_evaluation")
	private boolean alternativeDesignEvaluation;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;
	
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getProjectAuditPK() {
		return projectAuditPK;
	}

	public void setProjectAuditPK(Long projectAuditPK) {
		this.projectAuditPK = projectAuditPK;
	}

	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}


	public String getGdcInvolvement() {
		return gdcInvolvement;
	}

	public void setGdcInvolvement(String gdcInvolvement) {
		this.gdcInvolvement = gdcInvolvement;
	}

	public String getLowLevelDesign() {
		return lowLevelDesign;
	}

	public void setLowLevelDesign(String lowLevelDesign) {
		this.lowLevelDesign = lowLevelDesign;
	}

	public String getDesignRepository() {
		return designRepository;
	}

	public void setDesignRepository(String designRepository) {
		this.designRepository = designRepository;
	}

	public String getDesignReviewOutcome() {
		return designReviewOutcome;
	}

	public void setDesignReviewOutcome(String designReviewOutcome) {
		this.designReviewOutcome = designReviewOutcome;
	}

	public String getDesignTool() {
		return designTool;
	}

	public void setDesignTool(String designTool) {
		this.designTool = designTool;
	}

	public boolean isAlternativeDesignEvaluation() {
		return alternativeDesignEvaluation;
	}

	public void setAlternativeDesignEvaluation(boolean alternativeDesignEvaluation) {
		this.alternativeDesignEvaluation = alternativeDesignEvaluation;
	}

	public String getReusableComponents() {
		return reusableComponents;
	}

	public void setReusableComponents(String reusableComponents) {
		this.reusableComponents = reusableComponents;
	}

	@Column(name = "reusable_components")
	private String reusableComponents;
}
