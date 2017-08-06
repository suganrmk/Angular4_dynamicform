

	package com.perficient.hr.service.impl;

	import java.util.Date;
	import java.util.List;

	import javax.annotation.Resource;

	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.Transaction;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import com.perficient.hr.dao.PtdDAO;
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
import com.perficient.hr.model.ScopeChangeLog;
import com.perficient.hr.model.ScopeChangeRequest;
import com.perficient.hr.model.StatusReportPlan;
import com.perficient.hr.model.TestCase;
import com.perficient.hr.model.TestPlan;
import com.perficient.hr.model.TestReport;
import com.perficient.hr.model.TraceabilityMatrix;
import com.perficient.hr.to.PtdResponseTO;
	import com.perficient.hr.service.PtdService;
	import com.perficient.hr.utils.ExceptionHandlingUtil;
	import com.perficient.hr.utils.LoggerUtil;
	import com.perficient.hr.utils.PerfHrConstants;

	@Service("PtdService")
	public class PtdServiceImpl implements PtdService{

		protected Logger logger = LoggerFactory.getLogger(PtdServiceImpl.class);
				
		
		@Resource(name="sessionFactory")
	    protected SessionFactory sessionFactory;
		
		@Autowired
		private PtdDAO pdtDao;

		
		@Override
		public Object saveAuditDetail(String userId,com.perficient.hr.to.PtdResponseTO ptdResponseTO) {
			LoggerUtil.infoLog(logger, "Save PTD Service Started");
			Session session = null;
			Transaction tx = null;
			ProjectAudit projectAudit = null;
			try {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				projectAudit = new ProjectAudit();
				projectAudit.setSequence(ptdResponseTO.getSqa().getSequence());
				projectAudit.setActive(true);
				projectAudit.setCreatedBy(Long.parseLong(userId));
				projectAudit.setDtCreated(new Date());
				projectAudit.setDtModified(new Date());
				projectAudit.setModifiedBy(Long.parseLong(userId));
				projectAudit.setProjectId(Long.parseLong(ptdResponseTO.getSqa().getProject()));
				projectAudit.setStatus(PerfHrConstants.PTD_DRAFT);
				projectAudit = (ProjectAudit) pdtDao.save(projectAudit, session);
				Long usrId = Long.parseLong(userId);
				Date dt = new Date();
				if(projectAudit != null){
					if(ptdResponseTO.getContinuosCodeReview().isApplicable()){
							ptdResponseTO.getContinuosCodeReview().setProjectAuditId(projectAudit.getPk());
							ptdResponseTO.getContinuosCodeReview().setCreatedBy(usrId);
							ptdResponseTO.getContinuosCodeReview().setDtCreated(dt);
							ptdResponseTO.getContinuosCodeReview().setDtModified(dt);
							ptdResponseTO.getContinuosCodeReview().setModifiedBy(usrId);
							pdtDao.save(ptdResponseTO.getContinuosCodeReview(),session);
					}
					if(ptdResponseTO.getPeerReviewPlan().isApplicable()){
						ptdResponseTO.getPeerReviewPlan().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getPeerReviewPlan().setCreatedBy(usrId);
						ptdResponseTO.getPeerReviewPlan().setDtCreated(new Date());
						ptdResponseTO.getPeerReviewPlan().setDtModified(new Date());
						ptdResponseTO.getPeerReviewPlan().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getPeerReviewPlan(), session);
					}
					if(ptdResponseTO.getDefectTracking().isApplicable()){
						ptdResponseTO.getDefectTracking().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getDefectTracking().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getDefectTracking().setDtCreated(new Date());
						ptdResponseTO.getDefectTracking().setDtModified(new Date());
						ptdResponseTO.getDefectTracking().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getDefectTracking(), session);
					}
					
					if(ptdResponseTO.getRiskIssuesDependencyTracking().isApplicable()){
						ptdResponseTO.getRiskIssuesDependencyTracking().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getRiskIssuesDependencyTracking().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getRiskIssuesDependencyTracking().setDtCreated(new Date());
						ptdResponseTO.getRiskIssuesDependencyTracking().setDtModified(new Date());
						ptdResponseTO.getRiskIssuesDependencyTracking().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getRiskIssuesDependencyTracking(), session);
					}
					
					if(ptdResponseTO.getDetailedRequirement().isApplicable()){
						ptdResponseTO.getDetailedRequirement().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getDetailedRequirement().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getDetailedRequirement().setDtCreated(new Date());
						ptdResponseTO.getDetailedRequirement().setDtModified(new Date());
						ptdResponseTO.getDetailedRequirement().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getDetailedRequirement(), session);
					}
					
					if(ptdResponseTO.getHighLevelRequirement().isApplicable()){
						ptdResponseTO.getHighLevelRequirement().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getHighLevelRequirement().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getHighLevelRequirement().setDtCreated(new Date());
						ptdResponseTO.getHighLevelRequirement().setDtModified(new Date());
						ptdResponseTO.getHighLevelRequirement().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getHighLevelRequirement(), session);
					}
					
					if(ptdResponseTO.getDecisionAnalysisResolutionArtifacts().isApplicable()){
						ptdResponseTO.getDecisionAnalysisResolutionArtifacts().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getDecisionAnalysisResolutionArtifacts().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getDecisionAnalysisResolutionArtifacts().setDtCreated(new Date());
						ptdResponseTO.getDecisionAnalysisResolutionArtifacts().setDtModified(new Date());
						ptdResponseTO.getDecisionAnalysisResolutionArtifacts().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getDecisionAnalysisResolutionArtifacts(), session);
					}
					
					if(ptdResponseTO.getTraceabilityMatrix().isApplicable()){
						ptdResponseTO.getTraceabilityMatrix().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getTraceabilityMatrix().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getTraceabilityMatrix().setDtCreated(new Date());
						ptdResponseTO.getTraceabilityMatrix().setDtModified(new Date());
						ptdResponseTO.getTraceabilityMatrix().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getTraceabilityMatrix(), session);
					}
					
					if(ptdResponseTO.getIterationDemo().isApplicable()){
						ptdResponseTO.getIterationDemo().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getIterationDemo().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getIterationDemo().setDtCreated(new Date());
						ptdResponseTO.getIterationDemo().setDtModified(new Date());
						ptdResponseTO.getIterationDemo().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getIterationDemo(), session);								
					}
					if(ptdResponseTO.getIterationRetrospective().isApplicable()){
						ptdResponseTO.getIterationRetrospective().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getIterationRetrospective().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getIterationRetrospective().setDtCreated(new Date());
						ptdResponseTO.getIterationRetrospective().setDtModified(new Date());
						ptdResponseTO.getIterationRetrospective().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getIterationRetrospective(), session);
					}
					if(ptdResponseTO.getProjectPlanningTaskTracking().isApplicable()){
						ptdResponseTO.getProjectPlanningTaskTracking().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getProjectPlanningTaskTracking().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getProjectPlanningTaskTracking().setDtCreated(new Date());
						ptdResponseTO.getProjectPlanningTaskTracking().setDtModified(new Date());
						ptdResponseTO.getProjectPlanningTaskTracking().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getProjectPlanningTaskTracking(), session);
					}
					if(ptdResponseTO.getArchitectureDesignEntity().isApplicable()){
						ptdResponseTO.getArchitectureDesignEntity().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getArchitectureDesignEntity().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getArchitectureDesignEntity().setDtCreated(new Date());
						ptdResponseTO.getArchitectureDesignEntity().setDtModified(new Date());
						ptdResponseTO.getArchitectureDesignEntity().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getArchitectureDesignEntity(), session);
					}
					if(ptdResponseTO.getHandOffEntity().isApplicable()){
						ptdResponseTO.getHandOffEntity().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getHandOffEntity().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getHandOffEntity().setDtCreated(new Date());
						ptdResponseTO.getHandOffEntity().setDtModified(new Date());
						ptdResponseTO.getHandOffEntity().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getHandOffEntity(), session);
					}
					if(ptdResponseTO.getKnowledgeTransferEntity().isApplicable()){
						ptdResponseTO.getKnowledgeTransferEntity().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getKnowledgeTransferEntity().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getKnowledgeTransferEntity().setDtCreated(new Date());
						ptdResponseTO.getKnowledgeTransferEntity().setDtModified(new Date());
						ptdResponseTO.getKnowledgeTransferEntity().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getKnowledgeTransferEntity(), session);
					}
					if(ptdResponseTO.getCarArtifactsEntity().isApplicable()){
						ptdResponseTO.getCarArtifactsEntity().setProjectAuditPK(projectAudit.getPk());
						ptdResponseTO.getCarArtifactsEntity().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getCarArtifactsEntity().setDtCreated(new Date());
						ptdResponseTO.getCarArtifactsEntity().setDtModified(new Date());
						ptdResponseTO.getCarArtifactsEntity().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getCarArtifactsEntity(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtBackupRecovery().isApplicable()){
						ptdResponseTO.getConfigMgmtBackupRecovery().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getConfigMgmtBackupRecovery().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getConfigMgmtBackupRecovery().setDtCreated(new Date());
						ptdResponseTO.getConfigMgmtBackupRecovery().setDtModified(new Date());
						ptdResponseTO.getConfigMgmtBackupRecovery().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getConfigMgmtBackupRecovery(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtBuildRelease().isApplicable()){
						ptdResponseTO.getConfigMgmtBuildRelease().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getConfigMgmtBuildRelease().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getConfigMgmtBuildRelease().setDtCreated(new Date());
						ptdResponseTO.getConfigMgmtBuildRelease().setDtModified(new Date());
						ptdResponseTO.getConfigMgmtBuildRelease().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getConfigMgmtBuildRelease(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtCMPlan().isApplicable()){
						ptdResponseTO.getConfigMgmtCMPlan().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getConfigMgmtCMPlan().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getConfigMgmtCMPlan().setDtCreated(new Date());
						ptdResponseTO.getConfigMgmtCMPlan().setDtModified(new Date());
						ptdResponseTO.getConfigMgmtCMPlan().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getConfigMgmtCMPlan(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtContinuousIntegration().isApplicable()){
						ptdResponseTO.getConfigMgmtContinuousIntegration().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getConfigMgmtContinuousIntegration().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getConfigMgmtContinuousIntegration().setDtCreated(new Date());
						ptdResponseTO.getConfigMgmtContinuousIntegration().setDtModified(new Date());
						ptdResponseTO.getConfigMgmtContinuousIntegration().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getConfigMgmtContinuousIntegration(), session);
					}
					
					if(ptdResponseTO.getPqppMetricsCollection().isApplicable()){
						ptdResponseTO.getPqppMetricsCollection().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getPqppMetricsCollection().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getPqppMetricsCollection().setDtCreated(new Date());
						ptdResponseTO.getPqppMetricsCollection().setDtModified(new Date());
						ptdResponseTO.getPqppMetricsCollection().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getPqppMetricsCollection(), session);
					}
					
					if(ptdResponseTO.getPqppProcessPrfmncModel().isApplicable()){
						ptdResponseTO.getPqppProcessPrfmncModel().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getPqppProcessPrfmncModel().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getPqppProcessPrfmncModel().setDtCreated(new Date());
						ptdResponseTO.getPqppProcessPrfmncModel().setDtModified(new Date());
						ptdResponseTO.getPqppProcessPrfmncModel().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getPqppProcessPrfmncModel(), session);
					}
					
					if(ptdResponseTO.getPqppSpcAnalysis().isApplicable()){
						ptdResponseTO.getPqppSpcAnalysis().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getPqppSpcAnalysis().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getPqppSpcAnalysis().setDtCreated(new Date());
						ptdResponseTO.getPqppSpcAnalysis().setDtModified(new Date());
						ptdResponseTO.getPqppSpcAnalysis().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getPqppSpcAnalysis(), session);
					}
					
					if(ptdResponseTO.getScopeChangeLog().isApplicable()){
						ptdResponseTO.getScopeChangeLog().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getScopeChangeLog().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getScopeChangeLog().setDtCreated(new Date());
						ptdResponseTO.getScopeChangeLog().setDtModified(new Date());
						ptdResponseTO.getScopeChangeLog().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getScopeChangeLog(), session);
					}
					
					if(ptdResponseTO.getScopeChangeRequest().isApplicable()){
						ptdResponseTO.getScopeChangeRequest().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getScopeChangeRequest().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getScopeChangeRequest().setDtCreated(new Date());
						ptdResponseTO.getScopeChangeRequest().setDtModified(new Date());
						ptdResponseTO.getScopeChangeRequest().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getScopeChangeRequest(), session);
					}
					
					if(ptdResponseTO.getStatusReportPlan().isApplicable()){
						ptdResponseTO.getStatusReportPlan().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getStatusReportPlan().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getStatusReportPlan().setDtCreated(new Date());
						ptdResponseTO.getStatusReportPlan().setDtModified(new Date());
						ptdResponseTO.getStatusReportPlan().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getStatusReportPlan(), session);
					}
					
					if(ptdResponseTO.getTestPlan().isApplicable()){
						ptdResponseTO.getTestPlan().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getTestPlan().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getTestPlan().setDtCreated(new Date());
						ptdResponseTO.getTestPlan().setDtModified(new Date());
						ptdResponseTO.getTestPlan().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getTestPlan(), session);
					}
					
					if(ptdResponseTO.getTestCase().isApplicable()){
						ptdResponseTO.getTestCase().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getTestCase().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getTestCase().setDtCreated(new Date());
						ptdResponseTO.getTestCase().setDtModified(new Date());
						ptdResponseTO.getTestCase().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getTestCase(), session);
					}
					
					if(ptdResponseTO.getTestReport().isApplicable()){
						ptdResponseTO.getTestReport().setProjectAuditId(projectAudit.getPk());
						ptdResponseTO.getTestReport().setCreatedBy(Long.parseLong(userId));
						ptdResponseTO.getTestReport().setDtCreated(new Date());
						ptdResponseTO.getTestReport().setDtModified(new Date());
						ptdResponseTO.getTestReport().setModifiedBy(Long.parseLong(userId));
						pdtDao.save(ptdResponseTO.getTestReport(), session);
					}
				}
				tx.commit();
			} catch (Exception e) {
				ExceptionHandlingUtil.transactionRollback(tx);
				LoggerUtil.errorLog(logger, "Unable to save PtdAudit Details" , e);
				return ExceptionHandlingUtil.returnErrorObject("Unable to save PtdAudit Details" , e);
			}finally{
				ExceptionHandlingUtil.closeSession(session);
			}
			return ptdResponseTO;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object loadPtd(String projectId, String auditVersion) {
			LoggerUtil.infoLog(logger, "load PtdDetail Service Started");
			Session session = null;
			Transaction tx = null;
			ProjectAudit projectAudit = null;
			List<Process> procesList = null;
			PtdResponseTO ptdResponseTO = new PtdResponseTO();
			try {
				session  = sessionFactory.openSession();
				tx = session.beginTransaction();
				projectAudit = (ProjectAudit) pdtDao.loadPtd(projectId,auditVersion,session);
				if(projectAudit != null){
					ptdResponseTO.setContinuosCodeReview(projectAudit.getContinuosCodeReview());
					ptdResponseTO.setPeerReviewPlan(projectAudit.getPeerReviewPlan());
					ptdResponseTO.setDefectTracking(projectAudit.getDefectTracking());
					ptdResponseTO.setRiskIssuesDependencyTracking(projectAudit.getRiskIssuesDependencyTracking());
					ptdResponseTO.setTraceabilityMatrix(projectAudit.getTraceabilityMatrix());
					ptdResponseTO.setDetailedRequirement(projectAudit.getDetailedRequirement());
					ptdResponseTO.setHighLevelRequirement(projectAudit.getHighLevelRequirement());
					ptdResponseTO.setDecisionAnalysisResolutionArtifacts(projectAudit.getDecisionAnalysisResolutionArtifacts());
					ptdResponseTO.setArchitectureDesignEntity(projectAudit.getArchitectureDesignEntity());
					ptdResponseTO.setProjectPlanningTaskTracking(projectAudit.getProjectPlanningTaskTrackingEntity());
					ptdResponseTO.setIterationRetrospective(projectAudit.getIterationRetrospectiveEntity());
					ptdResponseTO.setIterationDemo(projectAudit.getIterationDemoEntity());
					ptdResponseTO.setCarArtifactsEntity(projectAudit.getCarArtifactsEntity());
					ptdResponseTO.setHandOffEntity(projectAudit.getHandOffEntity());
					ptdResponseTO.setKnowledgeTransferEntity(projectAudit.getKnowledgeTransferEntity());
					ptdResponseTO.setConfigMgmtBackupRecovery(projectAudit.getConfigMgmtBackupRecovery());
					ptdResponseTO.setConfigMgmtBuildRelease(projectAudit.getConfigMgmtBuildRelease());
					ptdResponseTO.setConfigMgmtCMPlan(projectAudit.getConfigMgmtCMPlan());
					ptdResponseTO.setConfigMgmtContinuousIntegration(projectAudit.getConfigMgmtContinuousIntegration());
					ptdResponseTO.setPqppMetricsCollection(projectAudit.getPqppMetricsCollection());
					ptdResponseTO.setPqppProcessPrfmncModel(projectAudit.getPqppProcessPrfmncModel());
					ptdResponseTO.setPqppSpcAnalysis(projectAudit.getPqppSpcAnalysis());
					ptdResponseTO.setScopeChangeLog(projectAudit.getScopeChangeLog());
					ptdResponseTO.setScopeChangeRequest(projectAudit.getScopeChangeRequest());
					ptdResponseTO.setStatusReportPlan(projectAudit.getStatusReportPlan());
					ptdResponseTO.setTestPlan(projectAudit.getTestPlan());
					ptdResponseTO.setTestCase(projectAudit.getTestCase());
					ptdResponseTO.setTestReport(projectAudit.getTestReport());
					ptdResponseTO.setProjectAudit(projectAudit);
				}
				procesList = (List<Process>) pdtDao.loadProcess(session);
				ptdResponseTO.setProcesList(procesList);
				tx.commit();	
			} catch (Exception e) {
				LoggerUtil.errorLog(logger, "Unable to Load Ptd details" , e);
				ExceptionHandlingUtil.transactionRollback(tx);
				return ExceptionHandlingUtil.returnErrorObject("Unable to Load Ptd details" , e);
			}finally{
				ExceptionHandlingUtil.closeSession(session);
			}
			return ptdResponseTO;
		}
		

		
		@Override
		public Object merge(String userId,String projectId, String version ,com.perficient.hr.to.PtdResponseTO ptdResponseTO) {
			LoggerUtil.infoLog(logger, "Merge PTD Service Started");
			Session session = null;
			Transaction tx = null;
			try {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				if(checkAuditExist(projectId,version,session)){
				
					pdtDao.merge(ptdResponseTO.getProjectAudit(),session);
					if(ptdResponseTO.getContinuosCodeReview().isApplicable()){
						pdtDao.merge(ptdResponseTO.getContinuosCodeReview(),session);
					}
					if(ptdResponseTO.getPeerReviewPlan().isApplicable()){
						pdtDao.merge(ptdResponseTO.getPeerReviewPlan(), session);
					}
					if(ptdResponseTO.getDefectTracking().isApplicable()){
						pdtDao.merge(ptdResponseTO.getDefectTracking(),session);
					}
					if(ptdResponseTO.getDecisionAnalysisResolutionArtifacts().isApplicable()){
						pdtDao.merge(ptdResponseTO.getDecisionAnalysisResolutionArtifacts(), session);
					}
					if(ptdResponseTO.getDetailedRequirement().isApplicable()){
						pdtDao.merge(ptdResponseTO.getDetailedRequirement(),session);
					}
					if(ptdResponseTO.getHighLevelRequirement().isApplicable()){
						pdtDao.merge(ptdResponseTO.getHighLevelRequirement(), session);
					}
					if(ptdResponseTO.getRiskIssuesDependencyTracking().isApplicable()){
						pdtDao.merge(ptdResponseTO.getRiskIssuesDependencyTracking(),session);
					}
					if(ptdResponseTO.getTraceabilityMatrix().isApplicable()){
						pdtDao.merge(ptdResponseTO.getTraceabilityMatrix(), session);
					}
					if(ptdResponseTO.getIterationDemo().isApplicable()){
						pdtDao.merge(ptdResponseTO.getIterationDemo(), session);								
					}
					if(ptdResponseTO.getIterationRetrospective().isApplicable()){
						pdtDao.merge(ptdResponseTO.getIterationRetrospective(), session);
					}
					if(ptdResponseTO.getProjectPlanningTaskTracking().isApplicable()){
						pdtDao.merge(ptdResponseTO.getProjectPlanningTaskTracking(), session);
					}
					if(ptdResponseTO.getArchitectureDesignEntity().isApplicable()){
						pdtDao.merge(ptdResponseTO.getArchitectureDesignEntity(), session);
					}
					if(ptdResponseTO.getHandOffEntity().isApplicable()){
						pdtDao.merge(ptdResponseTO.getHandOffEntity(), session);
					}
					if(ptdResponseTO.getKnowledgeTransferEntity().isApplicable()){
						pdtDao.merge(ptdResponseTO.getKnowledgeTransferEntity(), session);
					}
					if(ptdResponseTO.getCarArtifactsEntity().isApplicable()){
						pdtDao.merge(ptdResponseTO.getCarArtifactsEntity(), session);
					}
					if(ptdResponseTO.getConfigMgmtBackupRecovery().isApplicable()){
						pdtDao.merge(ptdResponseTO.getConfigMgmtBackupRecovery(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtBuildRelease().isApplicable()){
						pdtDao.merge(ptdResponseTO.getConfigMgmtBuildRelease(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtCMPlan().isApplicable()){
						pdtDao.merge(ptdResponseTO.getConfigMgmtCMPlan(), session);
					}
					
					if(ptdResponseTO.getConfigMgmtContinuousIntegration().isApplicable()){
						pdtDao.merge(ptdResponseTO.getConfigMgmtContinuousIntegration(), session);
					}
					
					if(ptdResponseTO.getPqppMetricsCollection().isApplicable()){
						pdtDao.merge(ptdResponseTO.getPqppMetricsCollection(), session);
					}
					
					if(ptdResponseTO.getPqppProcessPrfmncModel().isApplicable()){
						pdtDao.merge(ptdResponseTO.getPqppProcessPrfmncModel(), session);
					}
					
					if(ptdResponseTO.getPqppSpcAnalysis().isApplicable()){
						pdtDao.merge(ptdResponseTO.getPqppSpcAnalysis(), session);
					}
					
					if(ptdResponseTO.getScopeChangeLog().isApplicable()){
						pdtDao.merge(ptdResponseTO.getScopeChangeLog(), session);
					}
					
					if(ptdResponseTO.getScopeChangeRequest().isApplicable()){
						pdtDao.merge(ptdResponseTO.getScopeChangeRequest(), session);
					}
					
					if(ptdResponseTO.getStatusReportPlan().isApplicable()){
						pdtDao.merge(ptdResponseTO.getStatusReportPlan(), session);
					}
					
					if(ptdResponseTO.getTestPlan().isApplicable()){
						pdtDao.merge(ptdResponseTO.getTestPlan(), session);
					}
					
					if(ptdResponseTO.getTestCase().isApplicable()){
						pdtDao.merge(ptdResponseTO.getTestCase(), session);
					}
					
					if(ptdResponseTO.getTestReport().isApplicable()){
						pdtDao.merge(ptdResponseTO.getTestReport(), session);
					}
					tx.commit();
				}else{
					ProjectAudit projectAudit = new ProjectAudit();
					projectAudit.setProjectId(Long.parseLong(projectId));
					projectAudit.setStatus(PerfHrConstants.PTD_DRAFT);
					projectAudit.setActive(true);
					projectAudit.setCreatedBy(Long.parseLong(userId));
					projectAudit.setModifiedBy(Long.parseLong(userId));
					projectAudit.setDtCreated(new Date());
					projectAudit.setDtModified(new Date());
					projectAudit.setProjectId(Long.parseLong(projectId));
					projectAudit = (ProjectAudit)pdtDao.save(projectAudit, session);
					
					ContinuosCodeReview continuosCodeReview = new ContinuosCodeReview();
					continuosCodeReview.setProcess_pk(Long.parseLong(PerfHrConstants.CCR_ID));
					continuosCodeReview.setProjectAuditId(projectAudit.getPk());
					continuosCodeReview.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					continuosCodeReview.setApplicable(true);
					continuosCodeReview.setActive(true);
					continuosCodeReview.setCreatedBy(Long.parseLong(userId));
					continuosCodeReview.setModifiedBy(Long.parseLong(userId));
					continuosCodeReview.setDtCreated(new Date());
					continuosCodeReview.setDtModified(new Date());
					continuosCodeReview = (ContinuosCodeReview) pdtDao.save(continuosCodeReview, session);
					
					PeerReviewPlan peerReviewPlan = new PeerReviewPlan();
					peerReviewPlan.setProcess_pk(Long.parseLong(PerfHrConstants.CCR_ID));
					peerReviewPlan.setProjectAuditId(projectAudit.getPk());
					peerReviewPlan.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					peerReviewPlan.setApplicable(true);
					peerReviewPlan.setActive(true);
					peerReviewPlan.setCreatedBy(Long.parseLong(userId));
					peerReviewPlan.setModifiedBy(Long.parseLong(userId));
					peerReviewPlan.setDtCreated(new Date());
					peerReviewPlan.setDtModified(new Date());
					peerReviewPlan = (PeerReviewPlan) pdtDao.save(peerReviewPlan, session);
					
					RiskIssuesDependencyTracking riskIssuesDependencyTracking = new RiskIssuesDependencyTracking();
					riskIssuesDependencyTracking.setProcess_pk(Long.parseLong(PerfHrConstants.RIDM_ID));
					riskIssuesDependencyTracking.setProjectAuditId(projectAudit.getPk());
					riskIssuesDependencyTracking.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					riskIssuesDependencyTracking.setApplicable(true);
					riskIssuesDependencyTracking.setActive(true);
					riskIssuesDependencyTracking.setCreatedBy(Long.parseLong(userId));
					riskIssuesDependencyTracking.setModifiedBy(Long.parseLong(userId));
					riskIssuesDependencyTracking.setDtCreated(new Date());
					riskIssuesDependencyTracking.setDtModified(new Date());
					riskIssuesDependencyTracking = (RiskIssuesDependencyTracking) pdtDao.save(riskIssuesDependencyTracking, session);
					
					DefectTracking defectTracking = new DefectTracking();
					defectTracking.setProcess_pk(Long.parseLong(PerfHrConstants.DM_ID));
					defectTracking.setProjectAuditId(projectAudit.getPk());
					defectTracking.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					defectTracking.setApplicable(true);
					defectTracking.setActive(true);
					defectTracking.setCreatedBy(Long.parseLong(userId));
					defectTracking.setModifiedBy(Long.parseLong(userId));
					defectTracking.setDtCreated(new Date());
					defectTracking.setDtModified(new Date());
					defectTracking = (DefectTracking) pdtDao.save(defectTracking, session);
					
					DetailedRequirement detailedRequirement = new DetailedRequirement();
					detailedRequirement.setProcess_pk(Long.parseLong(PerfHrConstants.TCDR_ID));
					detailedRequirement.setProjectAuditId(projectAudit.getPk());
					detailedRequirement.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					detailedRequirement.setApplicable(true);
					detailedRequirement.setActive(true);
					detailedRequirement.setCreatedBy(Long.parseLong(userId));
					detailedRequirement.setModifiedBy(Long.parseLong(userId));
					detailedRequirement.setDtCreated(new Date());
					detailedRequirement.setDtModified(new Date());
					detailedRequirement = (DetailedRequirement) pdtDao.save(detailedRequirement, session);
					
					TraceabilityMatrix traceabilityMatrix = new TraceabilityMatrix();
					traceabilityMatrix.setProcess_pk(Long.parseLong(PerfHrConstants.TCDR_ID));
					traceabilityMatrix.setProjectAuditId(projectAudit.getPk());
					traceabilityMatrix.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					traceabilityMatrix.setApplicable(true);
					traceabilityMatrix.setActive(true);
					traceabilityMatrix.setCreatedBy(Long.parseLong(userId));
					traceabilityMatrix.setModifiedBy(Long.parseLong(userId));
					traceabilityMatrix.setDtCreated(new Date());
					traceabilityMatrix.setDtModified(new Date());
					traceabilityMatrix = (TraceabilityMatrix) pdtDao.save(traceabilityMatrix, session);
					
					HighLevelRequirement highLevelRequirement = new HighLevelRequirement();
					highLevelRequirement.setProcess_pk(Long.parseLong(PerfHrConstants.TCDR_ID));
					highLevelRequirement.setProjectAuditId(projectAudit.getPk());
					highLevelRequirement.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					highLevelRequirement.setApplicable(true);
					highLevelRequirement.setActive(true);
					highLevelRequirement.setCreatedBy(Long.parseLong(userId));
					highLevelRequirement.setModifiedBy(Long.parseLong(userId));
					highLevelRequirement.setDtCreated(new Date());
					highLevelRequirement.setDtModified(new Date());
					highLevelRequirement = (HighLevelRequirement) pdtDao.save(highLevelRequirement, session);
					
					DecisionAnalysisResolutionArtifacts decisionAnalysisResolutionArtifacts = new DecisionAnalysisResolutionArtifacts();
					decisionAnalysisResolutionArtifacts.setProcess_pk(Long.parseLong(PerfHrConstants.DAR_ID));
					decisionAnalysisResolutionArtifacts.setProjectAuditId(projectAudit.getPk());
					decisionAnalysisResolutionArtifacts.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					decisionAnalysisResolutionArtifacts.setApplicable(true);
					decisionAnalysisResolutionArtifacts.setActive(true);
					decisionAnalysisResolutionArtifacts.setCreatedBy(Long.parseLong(userId));
					decisionAnalysisResolutionArtifacts.setModifiedBy(Long.parseLong(userId));
					decisionAnalysisResolutionArtifacts.setDtCreated(new Date());
					decisionAnalysisResolutionArtifacts.setDtModified(new Date());
					decisionAnalysisResolutionArtifacts = (DecisionAnalysisResolutionArtifacts) pdtDao.save(decisionAnalysisResolutionArtifacts, session);
					
					IterationDemoEntity iterationDemoEntity = new IterationDemoEntity();
					iterationDemoEntity.setProcess_pk(Long.parseLong(PerfHrConstants.APM_ID));
					iterationDemoEntity.setProjectAuditPK(projectAudit.getPk());
					iterationDemoEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					iterationDemoEntity.setApplicable(true);
					iterationDemoEntity.setActive(true);
					iterationDemoEntity.setCreatedBy(Long.parseLong(userId));
					iterationDemoEntity.setModifiedBy(Long.parseLong(userId));
					iterationDemoEntity.setDtCreated(new Date());
					iterationDemoEntity.setDtModified(new Date());
					iterationDemoEntity = (IterationDemoEntity)pdtDao.save(iterationDemoEntity, session);
					
					ArchitectureDesignEntity architectureDesignEntity = new ArchitectureDesignEntity();
					architectureDesignEntity.setProcess_pk(Long.parseLong(PerfHrConstants.AD_ID));
					architectureDesignEntity.setProjectAuditPK(projectAudit.getPk());
					architectureDesignEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					architectureDesignEntity.setApplicable(true);
					architectureDesignEntity.setActive(true);
					architectureDesignEntity.setCreatedBy(Long.parseLong(userId));
					architectureDesignEntity.setModifiedBy(Long.parseLong(userId));
					architectureDesignEntity.setDtCreated(new Date());
					architectureDesignEntity.setDtModified(new Date());
					architectureDesignEntity = (ArchitectureDesignEntity)pdtDao.save(architectureDesignEntity, session);
					
					CarArtifactsEntity carArtifactsEntity = new CarArtifactsEntity();
					carArtifactsEntity.setProcess_pk(Long.parseLong(PerfHrConstants.CAR_ID));
					carArtifactsEntity.setProjectAuditPK(projectAudit.getPk());
					carArtifactsEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					carArtifactsEntity.setApplicable(true);
					carArtifactsEntity.setActive(true);
					carArtifactsEntity.setCreatedBy(Long.parseLong(userId));
					carArtifactsEntity.setModifiedBy(Long.parseLong(userId));
					carArtifactsEntity.setDtCreated(new Date());
					carArtifactsEntity.setDtModified(new Date());
					carArtifactsEntity = (CarArtifactsEntity)pdtDao.save(carArtifactsEntity, session);
					
					HandOffEntity handOffEntity = new HandOffEntity();
					handOffEntity.setProcess_pk(Long.parseLong(PerfHrConstants.HO_ID));
					handOffEntity.setProjectAuditPK(projectAudit.getPk());
					handOffEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					handOffEntity.setApplicable(true);
					handOffEntity.setActive(true);
					handOffEntity.setCreatedBy(Long.parseLong(userId));
					handOffEntity.setModifiedBy(Long.parseLong(userId));
					handOffEntity.setDtCreated(new Date());
					handOffEntity.setDtModified(new Date());
					handOffEntity = (HandOffEntity)pdtDao.save(handOffEntity, session);
					
					IterationRetrospectiveEntity iterationRetrospectiveEntity = new IterationRetrospectiveEntity();
					iterationRetrospectiveEntity.setProcess_pk(Long.parseLong(PerfHrConstants.APM_ID));
					iterationRetrospectiveEntity.setProjectAuditPK(projectAudit.getPk());
					iterationRetrospectiveEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					iterationRetrospectiveEntity.setApplicable(true);
					iterationRetrospectiveEntity.setActive(true);
					iterationRetrospectiveEntity.setCreatedBy(Long.parseLong(userId));
					iterationRetrospectiveEntity.setModifiedBy(Long.parseLong(userId));
					iterationRetrospectiveEntity.setDtCreated(new Date());
					iterationRetrospectiveEntity.setDtModified(new Date());
					iterationRetrospectiveEntity = (IterationRetrospectiveEntity)pdtDao.save(iterationRetrospectiveEntity, session);
					
					KnowledgeTransferEntity knowledgeTransferEntity = new KnowledgeTransferEntity();
					knowledgeTransferEntity.setProcess_pk(Long.parseLong(PerfHrConstants.HO_ID));
					knowledgeTransferEntity.setProjectAuditPK(projectAudit.getPk());
					knowledgeTransferEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					knowledgeTransferEntity.setApplicable(true);
					knowledgeTransferEntity.setActive(true);
					knowledgeTransferEntity.setCreatedBy(Long.parseLong(userId));
					knowledgeTransferEntity.setModifiedBy(Long.parseLong(userId));
					knowledgeTransferEntity.setDtCreated(new Date());
					knowledgeTransferEntity.setDtModified(new Date());
					knowledgeTransferEntity = (KnowledgeTransferEntity)pdtDao.save(knowledgeTransferEntity, session);
					
					ProjectPlanningTaskTrackingEntity projectPlanningTaskTrackingEntity = new ProjectPlanningTaskTrackingEntity();
					projectPlanningTaskTrackingEntity.setProcess_pk(Long.parseLong(PerfHrConstants.APM_ID));
					projectPlanningTaskTrackingEntity.setProjectAuditPK(projectAudit.getPk());
					projectPlanningTaskTrackingEntity.setStatus(PerfHrConstants.PTD_DRAFT);
					projectPlanningTaskTrackingEntity.setApplicable(true);
					projectPlanningTaskTrackingEntity.setActive(true);
					projectPlanningTaskTrackingEntity.setCreatedBy(Long.parseLong(userId));
					projectPlanningTaskTrackingEntity.setModifiedBy(Long.parseLong(userId));
					projectPlanningTaskTrackingEntity.setDtCreated(new Date());
					projectPlanningTaskTrackingEntity.setDtModified(new Date());
					projectPlanningTaskTrackingEntity = (ProjectPlanningTaskTrackingEntity)pdtDao.save(projectPlanningTaskTrackingEntity, session);
					
					ConfigMgmtBackupRecovery configMgmtBackupRecovery = new ConfigMgmtBackupRecovery();
					configMgmtBackupRecovery.setProcess_pk(Long.parseLong(PerfHrConstants.CM_ID));
					configMgmtBackupRecovery.setProjectAuditId(projectAudit.getPk());
					configMgmtBackupRecovery.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					configMgmtBackupRecovery.setApplicable(true);
					configMgmtBackupRecovery.setActive(true);
					configMgmtBackupRecovery.setCreatedBy(Long.parseLong(userId));
					configMgmtBackupRecovery.setModifiedBy(Long.parseLong(userId));
					configMgmtBackupRecovery.setDtCreated(new Date());
					configMgmtBackupRecovery.setDtModified(new Date());
					configMgmtBackupRecovery = (ConfigMgmtBackupRecovery) pdtDao.save(configMgmtBackupRecovery, session);
					
					ConfigMgmtBuildRelease configMgmtBuildRelease = new ConfigMgmtBuildRelease();
					configMgmtBuildRelease.setProcess_pk(Long.parseLong(PerfHrConstants.CM_ID));
					configMgmtBuildRelease.setProjectAuditId(projectAudit.getPk());
					configMgmtBuildRelease.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					configMgmtBuildRelease.setApplicable(true);
					configMgmtBuildRelease.setActive(true);
					configMgmtBuildRelease.setCreatedBy(Long.parseLong(userId));
					configMgmtBuildRelease.setModifiedBy(Long.parseLong(userId));
					configMgmtBuildRelease.setDtCreated(new Date());
					configMgmtBuildRelease.setDtModified(new Date());
					configMgmtBuildRelease = (ConfigMgmtBuildRelease) pdtDao.save(configMgmtBuildRelease, session);
					
					ConfigMgmtCMPlan configMgmtCMPlan = new ConfigMgmtCMPlan();
					configMgmtCMPlan.setProcess_pk(Long.parseLong(PerfHrConstants.CM_ID));
					configMgmtCMPlan.setProjectAuditId(projectAudit.getPk());
					configMgmtCMPlan.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					configMgmtCMPlan.setApplicable(true);
					configMgmtCMPlan.setActive(true);
					configMgmtCMPlan.setCreatedBy(Long.parseLong(userId));
					configMgmtCMPlan.setModifiedBy(Long.parseLong(userId));
					configMgmtCMPlan.setDtCreated(new Date());
					configMgmtCMPlan.setDtModified(new Date());
					configMgmtCMPlan = (ConfigMgmtCMPlan) pdtDao.save(configMgmtCMPlan, session);
					
					ConfigMgmtContinuousIntegration configMgmtContinuousIntegration = new ConfigMgmtContinuousIntegration();
					configMgmtContinuousIntegration.setProcess_pk(Long.parseLong(PerfHrConstants.CM_ID));
					configMgmtContinuousIntegration.setProjectAuditId(projectAudit.getPk());
					configMgmtContinuousIntegration.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					configMgmtContinuousIntegration.setApplicable(true);
					configMgmtContinuousIntegration.setActive(true);
					configMgmtContinuousIntegration.setCreatedBy(Long.parseLong(userId));
					configMgmtContinuousIntegration.setModifiedBy(Long.parseLong(userId));
					configMgmtContinuousIntegration.setDtCreated(new Date());
					configMgmtContinuousIntegration.setDtModified(new Date());
					configMgmtContinuousIntegration = (ConfigMgmtContinuousIntegration) pdtDao.save(configMgmtContinuousIntegration, session);
					
					PqppMetricsCollection pqppMetricsCollection = new PqppMetricsCollection();
					pqppMetricsCollection.setProcess_pk(Long.parseLong(PerfHrConstants.PQPP_ID));
					pqppMetricsCollection.setProjectAuditId(projectAudit.getPk());
					pqppMetricsCollection.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					pqppMetricsCollection.setApplicable(true);
					pqppMetricsCollection.setActive(true);
					pqppMetricsCollection.setCreatedBy(Long.parseLong(userId));
					pqppMetricsCollection.setModifiedBy(Long.parseLong(userId));
					pqppMetricsCollection.setDtCreated(new Date());
					pqppMetricsCollection.setDtModified(new Date());
					pqppMetricsCollection = (PqppMetricsCollection) pdtDao.save(pqppMetricsCollection, session);
					
					PqppProcessPrfmncModel pqppProcessPrfmncModel = new PqppProcessPrfmncModel();
					pqppProcessPrfmncModel.setProcess_pk(Long.parseLong(PerfHrConstants.PQPP_ID));
					pqppProcessPrfmncModel.setProjectAuditId(projectAudit.getPk());
					pqppProcessPrfmncModel.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					pqppProcessPrfmncModel.setApplicable(true);
					pqppProcessPrfmncModel.setActive(true);
					pqppProcessPrfmncModel.setCreatedBy(Long.parseLong(userId));
					pqppProcessPrfmncModel.setModifiedBy(Long.parseLong(userId));
					pqppProcessPrfmncModel.setDtCreated(new Date());
					pqppProcessPrfmncModel.setDtModified(new Date());
					pqppProcessPrfmncModel = (PqppProcessPrfmncModel) pdtDao.save(pqppProcessPrfmncModel, session);
					
					PqppSpcAnalysis pqppSpcAnalysis = new PqppSpcAnalysis();
					pqppSpcAnalysis.setProcess_pk(Long.parseLong(PerfHrConstants.PQPP_ID));
					pqppSpcAnalysis.setProjectAuditId(projectAudit.getPk());
					pqppSpcAnalysis.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					pqppSpcAnalysis.setApplicable(true);
					pqppSpcAnalysis.setActive(true);
					pqppSpcAnalysis.setCreatedBy(Long.parseLong(userId));
					pqppSpcAnalysis.setModifiedBy(Long.parseLong(userId));
					pqppSpcAnalysis.setDtCreated(new Date());
					pqppSpcAnalysis.setDtModified(new Date());
					pqppSpcAnalysis = (PqppSpcAnalysis) pdtDao.save(pqppSpcAnalysis, session);
					
					ScopeChangeLog scopeChangeLog = new ScopeChangeLog();
					scopeChangeLog.setProcess_pk(Long.parseLong(PerfHrConstants.SC_ID));
					scopeChangeLog.setProjectAuditId(projectAudit.getPk());
					scopeChangeLog.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					scopeChangeLog.setApplicable(true);
					scopeChangeLog.setActive(true);
					scopeChangeLog.setCreatedBy(Long.parseLong(userId));
					scopeChangeLog.setModifiedBy(Long.parseLong(userId));
					scopeChangeLog.setDtCreated(new Date());
					scopeChangeLog.setDtModified(new Date());
					scopeChangeLog = (ScopeChangeLog) pdtDao.save(scopeChangeLog, session);
					
					ScopeChangeRequest scopeChangeRequest = new ScopeChangeRequest();
					scopeChangeRequest.setProcess_pk(Long.parseLong(PerfHrConstants.SC_ID));
					scopeChangeRequest.setProjectAuditId(projectAudit.getPk());
					scopeChangeRequest.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					scopeChangeRequest.setApplicable(true);
					scopeChangeRequest.setActive(true);
					scopeChangeRequest.setCreatedBy(Long.parseLong(userId));
					scopeChangeRequest.setModifiedBy(Long.parseLong(userId));
					scopeChangeRequest.setDtCreated(new Date());
					scopeChangeRequest.setDtModified(new Date());
					scopeChangeRequest = (ScopeChangeRequest) pdtDao.save(scopeChangeRequest, session);
					
					StatusReportPlan statusReportPlan = new StatusReportPlan();
					statusReportPlan.setProcess_pk(Long.parseLong(PerfHrConstants.SR_ID));
					statusReportPlan.setProjectAuditId(projectAudit.getPk());
					statusReportPlan.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					statusReportPlan.setApplicable(true);
					statusReportPlan.setActive(true);
					statusReportPlan.setCreatedBy(Long.parseLong(userId));
					statusReportPlan.setModifiedBy(Long.parseLong(userId));
					statusReportPlan.setDtCreated(new Date());
					statusReportPlan.setDtModified(new Date());
					statusReportPlan = (StatusReportPlan) pdtDao.save(statusReportPlan, session);
					
					TestPlan testPlan = new TestPlan();
					testPlan.setProcess_pk(Long.parseLong(PerfHrConstants.AST_ID));
					testPlan.setProjectAuditId(projectAudit.getPk());
					testPlan.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					testPlan.setApplicable(true);
					testPlan.setActive(true);
					testPlan.setCreatedBy(Long.parseLong(userId));
					testPlan.setModifiedBy(Long.parseLong(userId));
					testPlan.setDtCreated(new Date());
					testPlan.setDtModified(new Date());
					testPlan = (TestPlan) pdtDao.save(testPlan, session);
					
					TestCase testCase = new TestCase();
					testCase.setProcess_pk(Long.parseLong(PerfHrConstants.AST_ID));
					testCase.setProjectAuditId(projectAudit.getPk());
					testCase.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					testCase.setApplicable(true);
					testCase.setActive(true);
					testCase.setCreatedBy(Long.parseLong(userId));
					testCase.setModifiedBy(Long.parseLong(userId));
					testCase.setDtCreated(new Date());
					testCase.setDtModified(new Date());
					testCase = (TestCase) pdtDao.save(testCase, session);
					
					TestReport testReport = new TestReport();
					testReport.setProcess_pk(Long.parseLong(PerfHrConstants.AST_ID));
					testReport.setProjectAuditId(projectAudit.getPk());
					testReport.setReviewStatus(PerfHrConstants.PTD_DRAFT);
					testReport.setApplicable(true);
					testReport.setActive(true);
					testReport.setCreatedBy(Long.parseLong(userId));
					testReport.setModifiedBy(Long.parseLong(userId));
					testReport.setDtCreated(new Date());
					testReport.setDtModified(new Date());
					testReport = (TestReport) pdtDao.save(testReport, session);
					
					tx.commit();
					
					ptdResponseTO.setProjectAudit(projectAudit);
					ptdResponseTO.setContinuosCodeReview(continuosCodeReview);
					ptdResponseTO.setPeerReviewPlan(peerReviewPlan);
					ptdResponseTO.setArchitectureDesignEntity(architectureDesignEntity);
					ptdResponseTO.setIterationDemo(iterationDemoEntity);
					ptdResponseTO.setCarArtifactsEntity(carArtifactsEntity);
					ptdResponseTO.setHandOffEntity(handOffEntity);
					ptdResponseTO.setIterationRetrospective(iterationRetrospectiveEntity);
					ptdResponseTO.setKnowledgeTransferEntity(knowledgeTransferEntity);
					ptdResponseTO.setProjectPlanningTaskTracking(projectPlanningTaskTrackingEntity);
					ptdResponseTO.setConfigMgmtBackupRecovery(configMgmtBackupRecovery);
					ptdResponseTO.setConfigMgmtBuildRelease(configMgmtBuildRelease);
					ptdResponseTO.setConfigMgmtCMPlan(configMgmtCMPlan);
					ptdResponseTO.setConfigMgmtContinuousIntegration(configMgmtContinuousIntegration);
					ptdResponseTO.setPqppMetricsCollection(pqppMetricsCollection);
					ptdResponseTO.setPqppProcessPrfmncModel(pqppProcessPrfmncModel);
					ptdResponseTO.setPqppSpcAnalysis(pqppSpcAnalysis);
					ptdResponseTO.setScopeChangeLog(scopeChangeLog);
					ptdResponseTO.setScopeChangeRequest(scopeChangeRequest);
					ptdResponseTO.setStatusReportPlan(statusReportPlan);
					ptdResponseTO.setTestPlan(testPlan);
					ptdResponseTO.setTestCase(testCase);
					ptdResponseTO.setTestReport(testReport);
					
				}
			} catch (Exception e) {
				ExceptionHandlingUtil.transactionRollback(tx);
				LoggerUtil.errorLog(logger, "Unable to merge PtdAudit Details" , e);
				return ExceptionHandlingUtil.returnErrorObject("Unable to merge PtdAudit Details" , e);
			}finally{
				ExceptionHandlingUtil.closeSession(session);
			}
			return ptdResponseTO;
		}
		
		
		
		private boolean checkAuditExist(String projectId,String version,Session session) throws  Exception {
			LoggerUtil.infoLog(logger, "CheckAuditExist PTD Service Started");
			ProjectAudit projectAudit = null;
			try {
				projectAudit = (ProjectAudit) pdtDao.loadPtd(projectId,version,session);
				if(projectAudit != null){
					return true;
				}
			} catch (Exception e) {
				LoggerUtil.errorLog(logger, "Unable to perform CheckAuditExist " , e);
				throw e;
			}
			return false;
		}

		public SessionFactory getSessionFactory() {
			return sessionFactory;
		}

		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}

	}
