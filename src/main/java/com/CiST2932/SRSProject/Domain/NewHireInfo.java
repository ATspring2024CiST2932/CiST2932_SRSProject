// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfo.java

package com.CiST2932.SRSProject.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "newhireinfo")  // Table name in the database
@Entity
public class NewHireInfo {
    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "EmploymentType")
    private String employmentType;

    @Column(name = "Mentor")
    private boolean mentor;

    @ManyToOne
    @JoinColumn(name = "mentorID", referencedColumnName = "mentorID")
    private MentorAssignments mentorAssignment;    

    // Constructors, getters, and setters
    public NewHireInfo() {
    }

    public NewHireInfo(int employeeId, String name, String employmentType, boolean mentor) {
        this.employeeId = employeeId;
        this.name = name;
        this.employmentType = employmentType;
        this.mentor = mentor;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public boolean isMentor() {
        return mentor;
    }

    public void setMentor(boolean mentor) {
        this.mentor = mentor;
    }

    public Integer getMentorId() {
        return employeeId;
    }

    public void setMentorId(Integer mentorId) {
        this.employeeId = mentorId;
    }

    public Integer getMenteeId() {
        return employeeId;
    }

    public void setMenteeId(Integer menteeId) {
        this.employeeId = menteeId;
    }

}

