package com.perficient.hr.to;

import java.util.List;

import com.perficient.hr.model.ArchitectureDesignEntity;
import com.perficient.hr.model.CarArtifactsEntity;
import com.perficient.hr.model.ConfigMgmtBackupRecovery;
import com.perficient.hr.model.ConfigMgmtBuildRelease;
import com.perficient.hr.model.ConfigMgmtCMPlan;
import com.perficient.hr.model.ConfigMgmtContinuousIntegration;
import com.perficient.hr.model.ContinuosCodeReview;
import com.perficient.hr.model.DecisionAnalysisResolutionArtifacts;
import com.perficient.hr.model.DefectTracking;
import com.perficient.hr.model.DetailedRequirement;
import com.perficient.hr.model.HandOffEntity;
import com.perficient.hr.model.HighLevelRequirement;
import com.perficient.hr.model.IterationDemoEntity;
import com.perficient.hr.model.IterationRetrospectiveEntity;
import com.perficient.hr.model.KnowledgeTransferEntity;
import com.perficient.hr.model.PeerReviewPlan;
import com.perficient.hr.model.PqppMetricsCollection;
import com.perficient.hr.model.PqppProcessPrfmncModel;
import com.perficient.hr.model.PqppSpcAnalysis;
import com.perficient.hr.model.Process;
import com.perficient.hr.model.ProjectAudit;
import com.perficient.hr.model.ProjectPlanningTaskTrackingEntity;
import com.perficient.hr.model.RiskIssuesDependencyTracking;
import com.perficient.hr.model.SQA;
import com.perficient.hr.model.ScopeChangeLog;
import com.perficient.hr.model.ScopeChangeRequest;
import com.perficient.hr.model.TraceabilityMatrix;;


public class PtdResponseTO {

	
	private List<Process> procesList;
	private SQA sqa;

	private ProjectAudit projectAudit;
	private ContinuosCodeReview continuosCodeReview;
	private PeerReviewPlan peerReviewPlan;
	private DefectTracking defectTracking;
	private RiskIssuesDependencyTracking riskIssuesDependencyTracking;
	private DetailedRequirement detailedRequirement;
	private HighLevelRequirement highLevelRequirement;
	private TraceabilityMatrix traceabilityMatrix;
	private DecisionAnalysisResolutionArtifacts decisionAnalysisResolutionArtifacts;
	private IterationDemoEntity iterationDemo;
	private IterationRetrospectiveEntity iterationRetrospective;
	private ProjectPlanningTaskTrackingEntity projectPlanningTaskTracking;
	private ArchitectureDesignEntity architectureDesignEntity;
	private HandOffEntity handOffEntity;
	private KnowledgeTransferEntity knowledgeTransferEntity;
	private CarArtifactsEntity carArtifactsEntity;
	private ConfigMgmtBackupRecovery configMgmtBackupRecovery;
	private ConfigMgmtBuildRelease configMgmtBuildRelease;
	private ConfigMgmtCMPlan configMgmtCMPlan;
	private ConfigMgmtContinuousIntegration configMgmtContinuousIntegration;
	private PqppMetricsCollection pqppMetricsCollection;
	private PqppProcessPrfmncModel pqppProcessPrfmncModel;
	private PqppSpcAnalysis pqppSpcAnalysis;
	private ScopeChangeLog scopeChangeLog;
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

	
	public SQA getSqa() {
		return sqa;
	}
	public void setSqa(SQA sqa) {
		this.sqa = sqa;
	}
	
	public RiskIssuesDependencyTracking getRiskIssuesDependencyTracking() {
		return riskIssuesDependencyTracking;
	}
	public void setRiskIssuesDependencyTracking(RiskIssuesDependencyTracking riskIssuesDependencyTracking) {
		this.riskIssuesDependencyTracking = riskIssuesDependencyTracking;
	}
	public DefectTracking getDefectTracking() {
		return defectTracking;
	}
	public void setDefectTracking(DefectTracking defectTracking) {
		this.defectTracking = defectTracking;
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
	public ProjectAudit getProjectAudit() {
		return projectAudit;
	}
	public void setProjectAudit(ProjectAudit projectAudit) {
		this.projectAudit = projectAudit;
	}
	public List<Process> getProcesList() {
		return procesList;
	}
	public void setProcesList(List<Process> procesList) {
		this.procesList = procesList;
	}
	public IterationDemoEntity getIterationDemo() {
		return iterationDemo;
	}
	public void setIterationDemo(IterationDemoEntity iterationDemo) {
		this.iterationDemo = iterationDemo;
	}
	public IterationRetrospectiveEntity getIterationRetrospective() {
		return iterationRetrospective;
	}
	public void setIterationRetrospective(IterationRetrospectiveEntity iterationRetrospective) {
		this.iterationRetrospective = iterationRetrospective;
	}
	public ProjectPlanningTaskTrackingEntity getProjectPlanningTaskTracking() {
		return projectPlanningTaskTracking;
	}
	public void setProjectPlanningTaskTracking(ProjectPlanningTaskTrackingEntity projectPlanningTaskTracking) {
		this.projectPlanningTaskTracking = projectPlanningTaskTracking;
	}
	public ArchitectureDesignEntity getArchitectureDesignEntity() {
		return architectureDesignEntity;
	}
	public void setArchitectureDesignEntity(ArchitectureDesignEntity architectureDesignEntity) {
		this.architectureDesignEntity = architectureDesignEntity;
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
	public CarArtifactsEntity getCarArtifactsEntity() {
		return carArtifactsEntity;
	}
	public void setCarArtifactsEntity(CarArtifactsEntity carArtifactsEntity) {
		this.carArtifactsEntity = carArtifactsEntity;
	}
	
			
}
