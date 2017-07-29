package com.perficient.hr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
@SuppressWarnings("serial")
public class Projects extends AbstractModel {

    @Id
    @GeneratedValue
    @Column(name = "pk")
    private Long pk;

    @Column(name = "project_name")
//    @NotNull(message = "Project Name cannot be empty")
//    @Size(min = 3, max = 45, message = "Size has to be between 3 and 45")
//    @Pattern(regexp = PerfHrConstants.ALPHA_NUMERIC, message = "Only text is allowed")
    private String projectName;

    @Column(name = "st_date")
//    @NotNull(message = "Start date is required / incorrect")
    private Date startDate;

    @Column(name = "end_date")
//    @NotNull(message = "End date is required / incorrect")
    private Date endDate;

    /*@JsonIgnore
    @Transient
    @AssertTrue(message = "Start Date must me earlier than end date")
    public boolean isValid() {
        return startDate != null && endDate != null && endDate.after(startDate);
    }*/

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
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
