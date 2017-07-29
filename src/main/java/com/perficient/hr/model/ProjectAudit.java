package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PROJECT_AUDIT")
public class ProjectAudit extends AbstractModel {
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	@Column(name = "project_pk")
	private Long projectId;
	@Column(name = "review_status")
	private String status;
	@Column(name = "sequence")
	private String sequence;
	@OneToOne(mappedBy = "projectAudit")
	private ContinuosCodeReview continuosCodeReview;
	
	@OneToOne(mappedBy = "projectAudit")
	private PeerReviewPlan peerReviewPlan;
	
	@OneToOne(mappedBy = "projectAudit")
	private DecisionAnalysisResolutionArtifacts decisionAnalysisResolutionArtifacts;
	
	@OneToOne(mappedBy = "projectAudit")
	private DefectTracking defectTracking;
	
	@OneToOne(mappedBy = "projectAudit")
	private DetailedRequirement detailedRequirement;
	
	@OneToOne(mappedBy = "projectAudit")
	private HighLevelRequirement highLevelRequirement;
	
	@OneToOne(mappedBy = "projectAudit")
	private TraceabilityMatrix traceabilityMatrix;
	
	@OneToOne(mappedBy = "projectAudit")
	private RiskIssuesDependencyTracking riskIssuesDependencyTracking;
	
	@OneToOne(mappedBy = "projectAudit")
	private IterationDemoEntity iterationDemoEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private IterationRetrospectiveEntity iterationRetrospectiveEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private ProjectPlanningTaskTrackingEntity projectPlanningTaskTrackingEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private ConfigMgmtBackupRecovery configMgmtBackupRecovery;
	
	@OneToOne(mappedBy = "projectAudit")
	private ConfigMgmtBuildRelease configMgmtBuildRelease;
	
	@OneToOne(mappedBy = "projectAudit")
	private ConfigMgmtCMPlan configMgmtCMPlan;
	
	@OneToOne(mappedBy = "projectAudit")
	private ConfigMgmtContinuousIntegration configMgmtContinuousIntegration;
	
	@OneToOne(mappedBy = "projectAudit")
	private PqppMetricsCollection pqppMetricsCollection;
	
	@OneToOne(mappedBy = "projectAudit")
	private PqppProcessPrfmncModel pqppProcessPrfmncModel;
	
	@OneToOne(mappedBy = "projectAudit")
	private PqppSpcAnalysis pqppSpcAnalysis;
	
	@OneToOne(mappedBy = "projectAudit")
	private ScopeChangeLog scopeChangeLog;
	
	@OneToOne(mappedBy = "projectAudit")
	private ScopeChangeRequest scopeChangeRequest;

	
	public ConfigMgmtBackupRecovery getConfigMgmtBackupRecovery() {
		return configMgmtBackupRecovery;
	}

	public void setConfigMgmtBackupRecovery(ConfigMgmtBackupRecovery configMgmtBackupRecovery) {
		this.configMgmtBackupRecovery = configMgmtBackupRecovery;
	}

	public ConfigMgmtBuildRelease getConfigMgmtBuildRelease() {
		return configMgmtBuildRelease;
	}

	public void setConfigMgmtBuildRelease(ConfigMgmtBuildRelease configMgmtBuildRelease) {
		this.configMgmtBuildRelease = configMgmtBuildRelease;
	}

	public ConfigMgmtCMPlan getConfigMgmtCMPlan() {
		return configMgmtCMPlan;
	}

	public void setConfigMgmtCMPlan(ConfigMgmtCMPlan configMgmtCMPlan) {
		this.configMgmtCMPlan = configMgmtCMPlan;
	}

	public ConfigMgmtContinuousIntegration getConfigMgmtContinuousIntegration() {
		return configMgmtContinuousIntegration;
	}

	public void setConfigMgmtContinuousIntegration(ConfigMgmtContinuousIntegration configMgmtContinuousIntegration) {
		this.configMgmtContinuousIntegration = configMgmtContinuousIntegration;
	}

	public PqppMetricsCollection getPqppMetricsCollection() {
		return pqppMetricsCollection;
	}

	public void setPqppMetricsCollection(PqppMetricsCollection pqppMetricsCollection) {
		this.pqppMetricsCollection = pqppMetricsCollection;
	}

	public PqppProcessPrfmncModel getPqppProcessPrfmncModel() {
		return pqppProcessPrfmncModel;
	}

	public void setPqppProcessPrfmncModel(PqppProcessPrfmncModel pqppProcessPrfmncModel) {
		this.pqppProcessPrfmncModel = pqppProcessPrfmncModel;
	}

	public PqppSpcAnalysis getPqppSpcAnalysis() {
		return pqppSpcAnalysis;
	}

	public void setPqppSpcAnalysis(PqppSpcAnalysis pqppSpcAnalysis) {
		this.pqppSpcAnalysis = pqppSpcAnalysis;
	}

	public ScopeChangeLog getScopeChangeLog() {
		return scopeChangeLog;
	}

	public void setScopeChangeLog(ScopeChangeLog scopeChangeLog) {
		this.scopeChangeLog = scopeChangeLog;
	}

	public ScopeChangeRequest getScopeChangeRequest() {
		return scopeChangeRequest;
	}

	public void setScopeChangeRequest(ScopeChangeRequest scopeChangeRequest) {
		this.scopeChangeRequest = scopeChangeRequest;
	}

	public IterationDemoEntity getIterationDemoEntity() {
		return iterationDemoEntity;
	}

	public void setIterationDemoEntity(IterationDemoEntity iterationDemoEntity) {
		this.iterationDemoEntity = iterationDemoEntity;
	}

	public IterationRetrospectiveEntity getIterationRetrospectiveEntity() {
		return iterationRetrospectiveEntity;
	}

	public void setIterationRetrospectiveEntity(IterationRetrospectiveEntity iterationRetrospectiveEntity) {
		this.iterationRetrospectiveEntity = iterationRetrospectiveEntity;
	}

	public ProjectPlanningTaskTrackingEntity getProjectPlanningTaskTrackingEntity() {
		return projectPlanningTaskTrackingEntity;
	}

	public void setProjectPlanningTaskTrackingEntity(ProjectPlanningTaskTrackingEntity projectPlanningTaskTrackingEntity) {
		this.projectPlanningTaskTrackingEntity = projectPlanningTaskTrackingEntity;
	}

	public ArchitectureDesignEntity getArchitectureDesignEntity() {
		return architectureDesignEntity;
	}

	public void setArchitectureDesignEntity(ArchitectureDesignEntity architectureDesignEntity) {
		this.architectureDesignEntity = architectureDesignEntity;
	}

	public CarArtifactsEntity getCarArtifactsEntity() {
		return carArtifactsEntity;
	}

	public void setCarArtifactsEntity(CarArtifactsEntity carArtifactsEntity) {
		this.carArtifactsEntity = carArtifactsEntity;
	}

	public HandOffEntity getHandOffEntity() {
		return handOffEntity;
	}

	public void setHandOffEntity(HandOffEntity handOffEntity) {
		this.handOffEntity = handOffEntity;
	}

	public KnowledgeTransferEntity getKnowledgeTransferEntity() {
		return knowledgeTransferEntity;
	}

	public void setKnowledgeTransferEntity(KnowledgeTransferEntity knowledgeTransferEntity) {
		this.knowledgeTransferEntity = knowledgeTransferEntity;
	}

	@OneToOne(mappedBy = "projectAudit")
	private ArchitectureDesignEntity architectureDesignEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private CarArtifactsEntity carArtifactsEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private HandOffEntity handOffEntity;
	
	@OneToOne(mappedBy = "projectAudit")
	private KnowledgeTransferEntity knowledgeTransferEntity;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public ContinuosCodeReview getContinuosCodeReview() {
		return continuosCodeReview;
	}

	public void setContinuosCodeReview(ContinuosCodeReview continuosCodeReview) {
		this.continuosCodeReview = continuosCodeReview;
	}

	public PeerReviewPlan getPeerReviewPlan() {
		return peerReviewPlan;
	}

	public void setPeerReviewPlan(PeerReviewPlan peerReviewPlan) {
		this.peerReviewPlan = peerReviewPlan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public DetailedRequirement getDetailedRequirement() {
		return detailedRequirement;
	}

	public void setDetailedRequirement(DetailedRequirement detailedRequirement) {
		this.detailedRequirement = detailedRequirement;
	}

	public HighLevelRequirement getHighLevelRequirement() {
		return highLevelRequirement;
	}

	public void setHighLevelRequirement(HighLevelRequirement highLevelRequirement) {
		this.highLevelRequirement = highLevelRequirement;
	}

	public TraceabilityMatrix getTraceabilityMatrix() {
		return traceabilityMatrix;
	}

	public void setTraceabilityMatrix(TraceabilityMatrix traceabilityMatrix) {
		this.traceabilityMatrix = traceabilityMatrix;
	}

	public DecisionAnalysisResolutionArtifacts getDecisionAnalysisResolutionArtifacts() {
		return decisionAnalysisResolutionArtifacts;
	}

	public void setDecisionAnalysisResolutionArtifacts(
			DecisionAnalysisResolutionArtifacts decisionAnalysisResolutionArtifacts) {
		this.decisionAnalysisResolutionArtifacts = decisionAnalysisResolutionArtifacts;
	}

	
	public DefectTracking getDefectTracking() {
		return defectTracking;
	}

	public void setDefectTracking(DefectTracking defectTracking) {
		this.defectTracking = defectTracking;
	}

	public RiskIssuesDependencyTracking getRiskIssuesDependencyTracking() {
		return riskIssuesDependencyTracking;
	}

	public void setRiskIssuesDependencyTracking(RiskIssuesDependencyTracking riskIssuesDependencyTracking) {
		this.riskIssuesDependencyTracking = riskIssuesDependencyTracking;
	}
 }