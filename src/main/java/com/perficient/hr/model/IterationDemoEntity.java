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
@Table(name = "SP_APM_ITERATION_DEMO")
public class IterationDemoEntity extends AbstractModel{
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

	@Column(name="project_audit_pk")
	@GenericGenerator(name="gen", strategy="foreign")
	private Long projectAuditPK;
	
	@Column(name="review_status")
	private String status;
	
	
	@Column(name = "applicable")
	private boolean applicable;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;
	
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

	@Column(name="demo_frequency")
	private String demoFrequency;
	
	
	@Column(name="demo_mode")
	private String demoMode;
	
	@Column(name="demo_participants")
	private String demoParticipants;
	
	@Column(name="demo_feedback_recording")
	private String demoFeedbackRecording;
	
	
	
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

	public String getDemoFrequency() {
		return demoFrequency;
	}

	public void setDemoFrequency(String demoFrequency) {
		this.demoFrequency = demoFrequency;
	}

	public String getDemoMode() {
		return demoMode;
	}

	public void setDemoMode(String demoMode) {
		this.demoMode = demoMode;
	}

	public String getDemoParticipants() {
		return demoParticipants;
	}

	public void setDemoParticipants(String demoParticipants) {
		this.demoParticipants = demoParticipants;
	}

	public String getDemoFeedbackRecording() {
		return demoFeedbackRecording;
	}

	public void setDemoFeedbackRecording(String demoFeedbackRecording) {
		this.demoFeedbackRecording = demoFeedbackRecording;
	}

	
}
