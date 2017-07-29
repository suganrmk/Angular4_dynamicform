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
@Table(name = "sp_dar_decision_analysis_and_resolution_artifacts")
public class DecisionAnalysisResolutionArtifacts extends AbstractModel{
	
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

	@Column(name="design_or_technical_solution")
	private String designOrTechSolution;
	
	@Column(name="conflict_mangement")
	private String conflictManagement;
	
	@Column(name="tool_selection")
	private String toolSelection;
	
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

	public boolean isApplicable() {
		return applicable;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public boolean applicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}

	public String getDesignOrTechSolution() {
		return designOrTechSolution;
	}

	public void setDesignOrTechSolution(String designOrTechSolution) {
		this.designOrTechSolution = designOrTechSolution;
	}

	public String getConflictManagement() {
		return conflictManagement;
	}

	public void setConflictManagement(String conflictManagement) {
		this.conflictManagement = conflictManagement;
	}

	public String getToolSelection() {
		return toolSelection;
	}

	public void setToolSelection(String toolSelection) {
		this.toolSelection = toolSelection;
	}


	}