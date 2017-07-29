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
@Table(name = "sp_tcdr_traceability_matrix")
public class TraceabilityMatrix extends AbstractModel{
	
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

	@Column(name="requirement_traceability_method")
	private String reqTraceabilityMethod;
	
	@Column(name="traceable_items")
	private String traceableItems;
	
	@Column(name="bi_directional_traceability")
	private String biDirectionalTraceability;
	
	@Column(name="owner")
	private String owner;
	
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

	public void setProjectAuditId(Long projectAuditPk) {
		this.projectAuditId = projectAuditPk;
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

	public String getReqTraceabilityMethod() {
		return reqTraceabilityMethod;
	}

	public void setReqTraceabilityMethod(String reqTraceabilityMethod) {
		this.reqTraceabilityMethod = reqTraceabilityMethod;
	}

	public String getTraceableItems() {
		return traceableItems;
	}

	public void setTraceableItems(String traceableItems) {
		this.traceableItems = traceableItems;
	}

	public String getBiDirectionalTraceability() {
		return biDirectionalTraceability;
	}

	public void setBiDirectionalTraceability(String biDirectionalTraceability) {
		this.biDirectionalTraceability = biDirectionalTraceability;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}


	}