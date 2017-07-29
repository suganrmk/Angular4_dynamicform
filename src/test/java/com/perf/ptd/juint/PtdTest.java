package com.perf.ptd.juint;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.perficient.hr.dao.impl.PtdDAOImpl;
import com.perficient.hr.model.ContinuosCodeReview;
import com.perficient.hr.model.DecisionAnalysisResolutionArtifacts;
import com.perficient.hr.model.DefectTracking;
import com.perficient.hr.model.DetailedRequirement;
import com.perficient.hr.model.HighLevelRequirement;
import com.perficient.hr.model.PeerReviewPlan;
import com.perficient.hr.model.ProjectAudit;
import com.perficient.hr.model.RiskIssuesDependencyTracking;
import com.perficient.hr.model.TraceabilityMatrix;
import com.perficient.hr.utils.PerfHrConstants;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:application-config-test.xml")
public class PtdTest {
/*
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private PtdDAOImpl ptdReviewDAOImpl;

	private Session session;
	
	private Transaction transaction;

	

	@Test
	public void savePAD(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			ProjectAudit projectAuditEntity = new ProjectAudit();
			projectAuditEntity.setProjectId(Long.parseLong("8"));
			projectAuditEntity.setSequence("1,2");
			projectAuditEntity.setStatus(PerfHrConstants.PTD_DRAFT);
			ptdReviewDAOImpl.save(projectAuditEntity, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void saveDR(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			DetailedRequirement  detailedRequirement = new DetailedRequirement();
			detailedRequirement.setProcess_pk(Long.parseLong("1"));
			detailedRequirement.setProjectAuditPk(Long.parseLong("8"));
			detailedRequirement.setApplicable(true);
			detailedRequirement.setReviewStatus(PerfHrConstants.PTD_DRAFT);
			detailedRequirement.setWhoDefinesDetailed("Test");
			detailedRequirement.setReqAnalysisMethod("Test");
			detailedRequirement.setReqElicitationMethod("Test");
			detailedRequirement.setReqDefOrRepMethod("Test");
			detailedRequirement.setReqClarification("Test");
			detailedRequirement.setReqOwner("Test");
			detailedRequirement.setReqRepository("Test");
			detailedRequirement.setReqTracking("Test");
			detailedRequirement.setReqReview("Test");
			ptdReviewDAOImpl.save(detailedRequirement, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void saveTM(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			TraceabilityMatrix  traceabilityMatrix = new TraceabilityMatrix();
			traceabilityMatrix.setProcess_pk(Long.parseLong("1"));
			traceabilityMatrix.setProjectAuditPk(Long.parseLong("8"));
			traceabilityMatrix.setApplicable(true);
			traceabilityMatrix.setReviewStatus(PerfHrConstants.PTD_DRAFT);
			traceabilityMatrix.setReqTraceabilityMethod("Test");
			traceabilityMatrix.setTraceableItems("Test");
			traceabilityMatrix.setBiDirectionalTraceability("Test");
			traceabilityMatrix.setOwner("Test");
			ptdReviewDAOImpl.save(traceabilityMatrix, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void saveHLR(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			HighLevelRequirement  highLevelRequirement = new HighLevelRequirement();
			highLevelRequirement.setProcess_pk(Long.parseLong("1"));
			highLevelRequirement.setProjectAuditPk(Long.parseLong("8"));
			highLevelRequirement.setApplicable(true);
			highLevelRequirement.setReviewStatus(PerfHrConstants.PTD_DRAFT);
			highLevelRequirement.setWhoDefinesHlr("Test");
			highLevelRequirement.setReqAnalysisMethod("Test");
			highLevelRequirement.setReqElicitationMethod("Test");
			highLevelRequirement.setReqDefOrRepMethod("Test");
			highLevelRequirement.setReqClarification("Test");
			highLevelRequirement.setReqOwner("Test");
			highLevelRequirement.setReqRepository("Test");
			highLevelRequirement.setReqTracking("Test");
			highLevelRequirement.setReqReview("Test");
			ptdReviewDAOImpl.save(highLevelRequirement, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void saveDT(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			DefectTracking  defectTracking = new DefectTracking();
			defectTracking.setProcess_pk(Long.parseLong("1"));
			defectTracking.setProjectAuditPk(Long.parseLong("8"));
			defectTracking.setApplicable(true);
			defectTracking.setReviewStatus(PerfHrConstants.PTD_DRAFT);
			defectTracking.setDefManagementByGDC("Test");
			defectTracking.setDefTrackingTool("Test");
			ptdReviewDAOImpl.save(defectTracking, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

		//@Test
	public void saveDARA(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			DecisionAnalysisResolutionArtifacts  decisionAnalysisResolutionArtifacts = new DecisionAnalysisResolutionArtifacts();
			decisionAnalysisResolutionArtifacts.setProcess_pk(Long.parseLong("1"));
			decisionAnalysisResolutionArtifacts.setProjectAuditPk(Long.parseLong("8"));
			decisionAnalysisResolutionArtifacts.setApplicable(true);
			decisionAnalysisResolutionArtifacts.setReviewStatus(PerfHrConstants.PTD_DRAFT);
			decisionAnalysisResolutionArtifacts.setDesignOrTechSolution("Test");
			decisionAnalysisResolutionArtifacts.setConflictManagement("Test");
			decisionAnalysisResolutionArtifacts.setToolSelection("Test");
			ptdReviewDAOImpl.save(decisionAnalysisResolutionArtifacts, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void saveCCR(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			ContinuosCodeReview  codeReviewEntity = new ContinuosCodeReview();
			codeReviewEntity.setProcess_pk(Long.parseLong("8"));
			codeReviewEntity.setProjectAuditId(Long.parseLong("8"));
			codeReviewEntity.setApplicable(true);
			codeReviewEntity.setStatus(PerfHrConstants.PTD_DRAFT);
			codeReviewEntity.setFrequency("Test");
			codeReviewEntity.setTeamPeerReview("Test");
			codeReviewEntity.setReviewAnalysis("Test");
			codeReviewEntity.setReviewFromGDC("Test");
			codeReviewEntity.setEnableLatestReviewTemplate("Test");
			codeReviewEntity.setReviewCommentTracking("Test");
			codeReviewEntity.setAutomated("Test");
			ptdReviewDAOImpl.save(codeReviewEntity, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}

	//@Test
	public void savePRP(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			PeerReviewPlan peerReviewPlanEntity = new PeerReviewPlan();
			peerReviewPlanEntity.setApplicable(true);
			peerReviewPlanEntity.setProcess_pk(Long.parseLong("8"));
			peerReviewPlanEntity.setProjectAuditId(Long.parseLong("8"));
			peerReviewPlanEntity.setStatus(PerfHrConstants.PTD_DRAFT);
			peerReviewPlanEntity.setReviewPalnTrackingSystem("Test");
			peerReviewPlanEntity.setReviewCriteriaDefinedBy("Test");
			peerReviewPlanEntity.setReviewPalnTrackingSystem("Test");
			peerReviewPlanEntity.setReviewTimeTracking("200H");
			ptdReviewDAOImpl.save(peerReviewPlanEntity, session);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}





	@Test
	public void saveRIDT(){
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			RiskIssuesDependencyTracking riskIssuesDependencyTracking = new RiskIssuesDependencyTracking();
			riskIssuesDependencyTracking.setProcess_pk(Long.parseLong("8"));
			riskIssuesDependencyTracking.setProjectAuditId(Long.parseLong("8"));
			riskIssuesDependencyTracking.setStatus(PerfHrConstants.PTD_DRAFT);
			riskIssuesDependencyTracking.setApplicable(true);
			riskIssuesDependencyTracking.setRiskMgmtOwn("Test");
			riskIssuesDependencyTracking.setRiskTrkSys("Test");
			riskIssuesDependencyTracking.setEnable2RiskProcess("Test");
			riskIssuesDependencyTracking.setCriteriaforRiskPMO("Test");
			riskIssuesDependencyTracking.setRiskTrkFreq("Test");
			riskIssuesDependencyTracking.setOrgRiskIncl("Test");
			riskIssuesDependencyTracking.setKnownRisk("Test");
			riskIssuesDependencyTracking.setComments("Test");
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
	}


	public void convertToJson(){

		SQA sqa = new SQA();
		sqa.setAccount("Blue Cross Of Blue Shiled");
		sqa.setProject("1");
		sqa.setSPEGApproval(PerfHrConstants.PTD_DRAFT);
		sqa.setCAM1("Test");
		sqa.setCAM2("Test");



		ContinuosCodeReview  codeReviewEntity = new ContinuosCodeReview();
		codeReviewEntity.setProcess_pk(Long.parseLong("8"));
		codeReviewEntity.setProjectAuditId(Long.parseLong("8"));
		codeReviewEntity.setApplicable(true);
		codeReviewEntity.setStatus(PerfHrConstants.PTD_DRAFT);
		codeReviewEntity.setFrequency("Test");
		codeReviewEntity.setTeamPeerReview("Test");
		codeReviewEntity.setReviewAnalysis("Test");
		codeReviewEntity.setReviewFromGDC("Test");
		codeReviewEntity.setEnableLatestReviewTemplate("Test");
		codeReviewEntity.setReviewCommentTracking("Test");
		codeReviewEntity.setAutomated("Test");

		PeerReviewPlan peerReviewPlanEntity = new PeerReviewPlan();
		peerReviewPlanEntity.setApplicable(true);
		peerReviewPlanEntity.setProcess_pk(Long.parseLong("8"));
		peerReviewPlanEntity.setProjectAuditId(Long.parseLong("8"));
		peerReviewPlanEntity.setStatus(PerfHrConstants.PTD_DRAFT);
		peerReviewPlanEntity.setReviewPalnTrackingSystem("Test");
		peerReviewPlanEntity.setReviewCriteriaDefinedBy("Test");
		peerReviewPlanEntity.setReviewPalnTrackingSystem("Test");
		peerReviewPlanEntity.setReviewTimeTracking("200H");


		RiskIssuesDependencyTracking riskIssuesDependencyTracking = new RiskIssuesDependencyTracking();
		riskIssuesDependencyTracking.setProcess_pk(Long.parseLong("8"));
		riskIssuesDependencyTracking.setProjectAuditId(Long.parseLong("8"));
		riskIssuesDependencyTracking.setStatus(PerfHrConstants.PTD_DRAFT);
		riskIssuesDependencyTracking.setApplicable(true);
		riskIssuesDependencyTracking.setRiskMgmtOwn("Test");
		riskIssuesDependencyTracking.setRiskTrkSys("Test");
		riskIssuesDependencyTracking.setEnable2RiskProcess("Test");
		riskIssuesDependencyTracking.setCriteriaforRiskPMO("Test");
		riskIssuesDependencyTracking.setRiskTrkFreq("Test");
		riskIssuesDependencyTracking.setOrgRiskIncl("Test");
		riskIssuesDependencyTracking.setKnownRisk("Test");
		riskIssuesDependencyTracking.setComments("Test");

		DetailedRequirement  detailedRequirement = new DetailedRequirement();
		detailedRequirement.setProcess_pk(Long.parseLong("1");
		detailedRequirement.setProjectAuditPk(Long.parseLong("8"));
		detailedRequirement.setApplicable(true);
		detailedRequirement.setStatus(PerfHrConstants.PTD_DRAFT);
		detailedRequirement.setWhoDefinesDetailed("Test");
		detailedRequirement.setReqAnalysisMethod("Test");
		detailedRequirement.setReqElicitationMethod("Test");
		detailedRequirement.setReqDefOrRepMethod("Test");
		detailedRequirement.setReqClarification("Test");
		detailedRequirement.setReqOwner("Test");
		detailedRequirement.setReqRepository("Test");
		detailedRequirement.setReqTracking("Test");
		detailedRequirement.setReqReview("Test");

		TraceabilityMatrix  traceabilityMatrix = new TraceabilityMatrix();
		traceabilityMatrix.setProcess_pk(Long.parseLong("1");
		traceabilityMatrix.setProjectAuditPk(Long.parseLong("8"));
		traceabilityMatrix.setApplicable(true);
		traceabilityMatrix.setStatus(PerfHrConstants.PTD_DRAFT);
		traceabilityMatrix.setReqTraceabilityMethod("Test");
		traceabilityMatrix.setTraceableItems("Test");
		traceabilityMatrix.setBiDirectionalTraceability("Test");
		traceabilityMatrix.setOwner("Test");

		DefectTracking  defectTracking = new DefectTracking();
		defectTracking.setProcess_pk(Long.parseLong("1");
		defectTracking.setProjectAuditPk(Long.parseLong("8"));
		defectTracking.setApplicable(true);
		defectTracking.setStatus(PerfHrConstants.PTD_DRAFT);
		defectTracking.setDefManagementByGDC("Test");
		defectTracking.setDefTrackingTool("Test");

		DecisionAnalysisResolutionArtifacts  decisionAnalysisResolutionArtifacts = new DecisionAnalysisResolutionArtifacts();
		decisionAnalysisResolutionArtifacts.setProcess_pk(Long.parseLong("1");
		decisionAnalysisResolutionArtifacts.setProjectAuditPk(Long.parseLong("8"));
		decisionAnalysisResolutionArtifacts.setApplicable(true);
		decisionAnalysisResolutionArtifacts.setStatus(PerfHrConstants.PTD_DRAFT);
		decisionAnalysisResolutionArtifacts.setDesignOrTechSolution("Test");
		decisionAnalysisResolutionArtifacts.setConflictManagement("Test");
		decisionAnalysisResolutionArtifacts.setToolSelection("Test");

		PTDResponseTO ptdResponseTO = new PTDResponseTO();
		ptdResponseTO.setSqa(sqa);

		ptdResponseTO.setAPM(true);
		ptdResponseTO.setContinuosCodeReview(codeReviewEntity);
		ptdResponseTO.setPeerReviewPlan(peerReviewPlanEntity);

		ptdResponseTO.setRIDM(true);
		ptdResponseTO.setRiskIssuesDependencyTracking(riskIssuesDependencyTracking);

		try {
			String json =  PerfUtils.convertToJson(ptdResponseTO);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


 	public void convertRIDTToJson(){
		RiskIssuesDependencyTracking riskIssuesDependencyTracking = new RiskIssuesDependencyTracking();
		riskIssuesDependencyTracking.setProcess_pk(Long.parseLong("8"));
		riskIssuesDependencyTracking.setProjectAuditId(Long.parseLong("8"));
		riskIssuesDependencyTracking.setStatus(PerfHrConstants.PTD_DRAFT);
		riskIssuesDependencyTracking.setApplicable(true);
		riskIssuesDependencyTracking.setRiskMgmtOwn("Test");
		riskIssuesDependencyTracking.setRiskTrkSys("Test");
		riskIssuesDependencyTracking.setEnable2RiskProcess("Test");
		riskIssuesDependencyTracking.setCriteriaforRiskPMO("Test");
		riskIssuesDependencyTracking.setRiskTrkFreq("Test");
		riskIssuesDependencyTracking.setOrgRiskIncl("Test");
		riskIssuesDependencyTracking.setKnownRisk("Test");
		riskIssuesDependencyTracking.setComments("Test");
		try {
			String json =  PerfUtils.convertToJson(riskIssuesDependencyTracking);
			System.out.println(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	} 

*/


}
