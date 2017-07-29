package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG")
public class AuditDetails {

	
	@Id
	@GeneratedValue
	@Column(name = "pk", nullable = false, columnDefinition = "Long default 1")
	//@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "employee_fname")
	private String employeeName;
	
	@Column(name="component_name")
	private String componentName;
	
	@Column(name="component_method_name")
	private String componentMethod;

	@Column(name="operation_date")
	private Date date;
	
	@Column(name="record_pk")
	private Long record_pk;
	
	@Column(name="httpStatus")
	private int httpStatus;
	
	
	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentMethod() {
		return componentMethod;
	}

	public void setComponentMethod(String componentMethod) {
		this.componentMethod = componentMethod;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRecord_pk() {
		return record_pk;
	}

	public void setRecord_pk(Long record_pk) {
		this.record_pk = record_pk;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
}
