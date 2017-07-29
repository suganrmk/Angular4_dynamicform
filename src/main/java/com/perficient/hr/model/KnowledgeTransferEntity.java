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
@Table(name = "SP_HO_KNOWLEDGE_TRANSFER")
public class KnowledgeTransferEntity extends AbstractModel{
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name = "process_pk")
	private Long process_pk;
	
	@Column(name="review_status")
	private String status;
	
	@Column(name = "project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign")
	private Long projectAuditPK;
	
	@Column(name = "applicable")
	private boolean applicable;
	
	@Column(name = "knowledge_transfer_mode")
	private String knowledgeTransferMode;
	
	@Column(name = "resource_planning_backlog")
	private String resourcePlanningBacklog;

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

	public String getKnowledgeTransferMode() {
		return knowledgeTransferMode;
	}

	public void setKnowledgeTransferMode(String knowledgeTransferMode) {
		this.knowledgeTransferMode = knowledgeTransferMode;
	}

	public String getResourcePlanningBacklog() {
		return resourcePlanningBacklog;
	}

	public void setResourcePlanningBacklog(String resourcePlanningBacklog) {
		this.resourcePlanningBacklog = resourcePlanningBacklog;
	}
	
}
