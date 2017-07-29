package com.perficient.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SUB_PROCESS")
public class SubProcess {

	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;

	@Column(name = "process_pk")
	private Long processId;
	
	@Column(name = "sub_process_name")
	private String subProcessName;
	
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name="process_desc")
	private String processDesc;
	
	@Column(name="sub_process_code")
	private String subProcessCode;

	
	
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}
	


	public String getSubProcessName() {
		return subProcessName;
	}

	public void setSubProcessName(String subProcessName) {
		this.subProcessName = subProcessName;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getSubProcessCode() {
		return subProcessCode;
	}

	public void setSubProcessCode(String subProcessCode) {
		this.subProcessCode = subProcessCode;
	}
	
}
