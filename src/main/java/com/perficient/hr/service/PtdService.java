package com.perficient.hr.service;

import com.perficient.hr.to.PtdResponseTO;

public interface PtdService {
	public Object saveAuditDetail(String userId, PtdResponseTO ptdResponseTO);
	public Object merge(String userId,String prjectId,String version, PtdResponseTO ptdResponseTO);
	public Object loadPtd(String projectId, String auditVersion);
}
