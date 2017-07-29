package com.perficient.hr.form;

import java.util.List;

import com.perficient.hr.model.Employee;
import com.perficient.hr.model.EmployeeBackOfficeInfo;
import com.perficient.hr.model.EmployeeEducation;
import com.perficient.hr.model.EmployeeInterviewFeedback;
import com.perficient.hr.model.EmployeeWorkExperience;

public class EmployeeForm {

	private Employee employee;
	
	private EmployeeBackOfficeInfo employeeBackOfficeInfo;
	
	private List<EmployeeInterviewFeedback> empInterviewFeedbackList;
	
	private List<EmployeeEducation> employeeEducationList;
	
	private List<EmployeeWorkExperience> empWorkExperienceList;

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the employeeBackOfficeInfo
	 */
	public EmployeeBackOfficeInfo getEmployeeBackOfficeInfo() {
		return employeeBackOfficeInfo;
	}

	/**
	 * @param employeeBackOfficeInfo the employeeBackOfficeInfo to set
	 */
	public void setEmployeeBackOfficeInfo(EmployeeBackOfficeInfo employeeBackOfficeInfo) {
		this.employeeBackOfficeInfo = employeeBackOfficeInfo;
	}

	/**
	 * @return the empInterviewFeedbackList
	 */
	public List<EmployeeInterviewFeedback> getEmpInterviewFeedbackList() {
		return empInterviewFeedbackList;
	}

	/**
	 * @param empInterviewFeedbackList the empInterviewFeedbackList to set
	 */
	public void setEmpInterviewFeedbackList(List<EmployeeInterviewFeedback> empInterviewFeedbackList) {
		this.empInterviewFeedbackList = empInterviewFeedbackList;
	}

	/**
	 * @return the employeeEducationList
	 */
	public List<EmployeeEducation> getEmployeeEducationList() {
		return employeeEducationList;
	}

	/**
	 * @param employeeEducationList the employeeEducationList to set
	 */
	public void setEmployeeEducationList(List<EmployeeEducation> employeeEducationList) {
		this.employeeEducationList = employeeEducationList;
	}

	/**
	 * @return the empWorkExperienceList
	 */
	public List<EmployeeWorkExperience> getEmpWorkExperienceList() {
		return empWorkExperienceList;
	}

	/**
	 * @param empWorkExperienceList the empWorkExperienceList to set
	 */
	public void setEmpWorkExperienceList(List<EmployeeWorkExperience> empWorkExperienceList) {
		this.empWorkExperienceList = empWorkExperienceList;
	}
	
}
