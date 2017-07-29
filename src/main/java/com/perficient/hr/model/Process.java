package com.perficient.hr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "PROCESS")
public class Process {

	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	private Long pk;
	
	@Column(name = "process_name")
	private String processName;

	@Column(name = "process_desc")
	private String processDecs;

	@Column(name="process_code")
	private String processCode;

	
	 @OneToMany(fetch = FetchType.EAGER)
	 @JoinTable(name = "SUB_PROCESS", joinColumns = @JoinColumn(name = "process_pk"),
	 inverseJoinColumns = @JoinColumn(name = "pk")) 
	 private Collection<SubProcess> subProcesses  ; 

	 
	  	public Long getPk() {
			return pk;
		}
	
		
		public Collection<SubProcess> getSubProcesses() {
			return subProcesses;
		}
	
	
		public void setSubProcesses(Collection<SubProcess> subProcesses) {
			this.subProcesses = subProcesses;
		}
	
	
		public void setPk(Long pk) {
			this.pk = pk;
		}
	
		public String getProcessName() {
			return processName;
		}
	
		public void setProcessName(String processName) {
			this.processName = processName;
		}
	
		public String getProcessDecs() {
			return processDecs;
		}
	
		public void setProcessDecs(String processDecs) {
			this.processDecs = processDecs;
		}
	
		public String getProcessCode() {
			return processCode;
		}
	
		public void setProcessCode(String processCode) {
			this.processCode = processCode;
		}
		
	
	
}
