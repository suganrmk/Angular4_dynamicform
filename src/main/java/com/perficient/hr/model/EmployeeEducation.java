package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_education")
@SuppressWarnings("serial")
public class EmployeeEducation extends AbstractModel {
	
	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11 )
	private Long pk;
	
	@Column(name = "employee_pk")
	private Long employeeId;
	
	@Column(name = "degree_name")
	private String degreeName;
	
	@Column(name = "specialization")
	private String specialization;
	
	@Column(name = "course_type")
	private String courseType;
	
	@Column(name = "qualification")
	private String qualification;
	
	@Column(name = "college")
	private String college;
	
	@Column(name = "university")
	private String university;
	
	@Column(name = "year_passed")
	private Date yearPassed;
	
	@Column(name = "percentage")
	private Double percentage;
	
	@Column(name = "gap_days")
	private int gapDays;
	
	@Column(name = "gap_reason")
	private String gapReason;

	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

	/**
	 * @return the employeeId
	 */
	public Long getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the degreeName
	 */
	public String getDegreeName() {
		return degreeName;
	}

	/**
	 * @param degreeName the degreeName to set
	 */
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * @return the courseType
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * @param courseType the courseType to set
	 */
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	/**
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * @param qualification the qualification to set
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/**
	 * @return the college
	 */
	public String getCollege() {
		return college;
	}

	/**
	 * @param college the college to set
	 */
	public void setCollege(String college) {
		this.college = college;
	}

	/**
	 * @return the university
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 * @param university the university to set
	 */
	public void setUniversity(String university) {
		this.university = university;
	}

	/**
	 * @return the yearPassed
	 */
	public Date getYearPassed() {
		return yearPassed;
	}

	/**
	 * @param yearPassed the yearPassed to set
	 */
	public void setYearPassed(Date yearPassed) {
		this.yearPassed = yearPassed;
	}

	/**
	 * @return the percentage
	 */
	public Double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the gapDays
	 */
	public int getGapDays() {
		return gapDays;
	}

	/**
	 * @param gapDays the gapDays to set
	 */
	public void setGapDays(int gapDays) {
		this.gapDays = gapDays;
	}

	/**
	 * @return the gapReason
	 */
	public String getGapReason() {
		return gapReason;
	}

	/**
	 * @param gapReason the gapReason to set
	 */
	public void setGapReason(String gapReason) {
		this.gapReason = gapReason;
	}

}
