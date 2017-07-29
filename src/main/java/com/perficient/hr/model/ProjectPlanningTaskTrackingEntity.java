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
@Table(name = "SP_APM_PROJECTPLANNING_TT")
public class ProjectPlanningTaskTrackingEntity extends AbstractModel{
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
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private ProjectAudit projectAudit;
	
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

	@Column(name="project_planning")
	private String projectPlanning;
	
	@Column(name="high_level_project_planning")
	private String highLevelProjectPlanning;
	
	@Column(name="sprint_frequency")
	private String sprintFrequency;
	
	@Column(name="project_estimation_method")
	private String projectEstimationMethod;
	
	@Column(name="sprint_planning")
	private String sprintPlanning;
	
	@Column(name="task_update_freq")
	private String taskUpdateFreq;
	
	@Column(name="onsite_offsite_overlap_hrs")
	private int onsiteOffsiteOverlap;
	
	@Column(name="release_frequency")
	private String releaseFrequency;
	
	@Column(name="release_planning")
	private String releaseplanning;
	
	@Column(name="project_process")
	private String projectProcess;
	
	@Column(name="daily_scrum")
	private String dailyScrum;
	
	@Column(name="sprint_backlog_management")
	private String sprintBacklogManagement;
	
	@Column(name="estimation_tracking_tool")
	private String estimationTrackingTool;
	
	@Column(name="project_document_repository")
	private String projectDocumentRepository;
	
	@Column(name="meeting_minutes")
	private String meetingMinutes;
	
	@Column(name="tdc_involvement")
	private String tdcInvolvement;
	
	@Column(name="infrastructure_request_management")
	private String infrastructureRequestManagement;
	
	@Column(name="sprint_baseline_critera")
	private String sprintBaselineCritera;
	
	@Column(name="support_task_management")
	private String supportTtaskManagement;
	
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


	public String getProjectPlanning() {
		return projectPlanning;
	}


	public void setProjectPlanning(String projectPlanning) {
		this.projectPlanning = projectPlanning;
	}


	public String getHighLevelProjectPlanning() {
		return highLevelProjectPlanning;
	}


	public void setHighLevelProjectPlanning(String highLevelProjectPlanning) {
		this.highLevelProjectPlanning = highLevelProjectPlanning;
	}


	public String getSprintFrequency() {
		return sprintFrequency;
	}


	public void setSprintFrequency(String sprintFrequency) {
		this.sprintFrequency = sprintFrequency;
	}


	public String getProjectEstimationMethod() {
		return projectEstimationMethod;
	}


	public void setProjectEstimationMethod(String projectEstimationMethod) {
		this.projectEstimationMethod = projectEstimationMethod;
	}


	public String getSprintPlanning() {
		return sprintPlanning;
	}


	public void setSprintPlanning(String sprintPlanning) {
		this.sprintPlanning = sprintPlanning;
	}


	public String getTaskUpdateFreq() {
		return taskUpdateFreq;
	}


	public void setTaskUpdateFreq(String taskUpdateFreq) {
		this.taskUpdateFreq = taskUpdateFreq;
	}


	public int getOnsiteOffsiteOverlap() {
		return onsiteOffsiteOverlap;
	}


	public void setOnsiteOffsiteOverlap(int onsiteOffsiteOverlap) {
		this.onsiteOffsiteOverlap = onsiteOffsiteOverlap;
	}


	public String getReleaseFrequency() {
		return releaseFrequency;
	}


	public void setReleaseFrequency(String releaseFrequency) {
		this.releaseFrequency = releaseFrequency;
	}


	public String getReleaseplanning() {
		return releaseplanning;
	}


	public void setReleaseplanning(String releaseplanning) {
		this.releaseplanning = releaseplanning;
	}


	public String getProjectProcess() {
		return projectProcess;
	}


	public void setProjectProcess(String projectProcess) {
		this.projectProcess = projectProcess;
	}


	public String getDailyScrum() {
		return dailyScrum;
	}


	public void setDailyScrum(String dailyScrum) {
		this.dailyScrum = dailyScrum;
	}


	public String getSprintBacklogManagement() {
		return sprintBacklogManagement;
	}


	public void setSprintBacklogManagement(String sprintBacklogManagement) {
		this.sprintBacklogManagement = sprintBacklogManagement;
	}


	public String getEstimationTrackingTool() {
		return estimationTrackingTool;
	}


	public void setEstimationTrackingTool(String estimationTrackingTool) {
		this.estimationTrackingTool = estimationTrackingTool;
	}


	public String getProjectDocumentRepository() {
		return projectDocumentRepository;
	}


	public void setProjectDocumentRepository(String projectDocumentRepository) {
		this.projectDocumentRepository = projectDocumentRepository;
	}


	public String getMeetingMinutes() {
		return meetingMinutes;
	}


	public void setMeetingMinutes(String meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}


	public String getTdcInvolvement() {
		return tdcInvolvement;
	}


	public void setTdcInvolvement(String tdcInvolvement) {
		this.tdcInvolvement = tdcInvolvement;
	}


	public String getInfrastructureRequestManagement() {
		return infrastructureRequestManagement;
	}


	public void setInfrastructureRequestManagement(String infrastructureRequestManagement) {
		this.infrastructureRequestManagement = infrastructureRequestManagement;
	}


	public String getSprintBaselineCritera() {
		return sprintBaselineCritera;
	}


	public void setSprintBaselineCritera(String sprintBaselineCritera) {
		this.sprintBaselineCritera = sprintBaselineCritera;
	}


	public String getSupportTtaskManagement() {
		return supportTtaskManagement;
	}


	public void setSupportTtaskManagement(String supportTtaskManagement) {
		this.supportTtaskManagement = supportTtaskManagement;
	}



}
