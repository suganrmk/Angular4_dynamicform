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
@Table(name = "SP_APM_ITERATION_RETROSPECTIVE")
public class IterationRetrospectiveEntity extends AbstractModel{
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
	
	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}

	public Long getProjectAuditPK() {
		return projectAuditPK;
	}

	public void setProjectAuditPK(Long projectAuditPK) {
		this.projectAuditPK = projectAuditPK;
	}

	@Column(name="retrospective_duration")
	private String retrospectiveDuration;
	
	@Column(name="retrospective_frequency")
	private String retrospectiveFrequency;
	
	@Column(name="retrospective_action_item_tracking")
	private String retrospectiveActionItemTracking;
	
	@Column(name="retrospective_outcome_documentation")
	private String retrospectiveOutcomeDocumentation;
	
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

	public String getRetrospectiveDuration() {
		return retrospectiveDuration;
	}

	public void setRetrospectiveDuration(String retrospectiveDuration) {
		this.retrospectiveDuration = retrospectiveDuration;
	}

	public String getRetrospectiveFrequency() {
		return retrospectiveFrequency;
	}

	public void setRetrospectiveFrequency(String retrospectiveFrequency) {
		this.retrospectiveFrequency = retrospectiveFrequency;
	}

	public String getRetrospectiveActionItemTracking() {
		return retrospectiveActionItemTracking;
	}

	public void setRetrospectiveActionItemTracking(String retrospectiveActionItemTracking) {
		this.retrospectiveActionItemTracking = retrospectiveActionItemTracking;
	}

	public String getRetrospectiveOutcomeDocumentation() {
		return retrospectiveOutcomeDocumentation;
	}

	public void setRetrospectiveOutcomeDocumentation(String retrospectiveOutcomeDocumentation) {
		this.retrospectiveOutcomeDocumentation = retrospectiveOutcomeDocumentation;
	}

	public String getRetrospectiveOwner() {
		return retrospectiveOwner;
	}

	public void setRetrospectiveOwner(String retrospectiveOwner) {
		this.retrospectiveOwner = retrospectiveOwner;
	}

	public String getStakeholderInvolvement() {
		return stakeholderInvolvement;
	}

	public void setStakeholderInvolvement(String stakeholderInvolvement) {
		this.stakeholderInvolvement = stakeholderInvolvement;
	}

	public String getRetrospectiveFocusOnAll() {
		return retrospectiveFocusOnAll;
	}

	public void setRetrospectiveFocusOnAll(String retrospectiveFocusOnAll) {
		this.retrospectiveFocusOnAll = retrospectiveFocusOnAll;
	}

	@Column(name="retrospective_owner")
	private String retrospectiveOwner;
	
	@Column(name="stakeholder_involvement")
	private String stakeholderInvolvement;
	
	@Column(name="retrospective_focus_on_all_pa")
	private String retrospectiveFocusOnAll;
}
