// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfo.java

package com.CiST2932.SRSProject.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "newhireinfo")  // Table name in the database
@Entity
public class NewHireInfo {
    @Id
    @Column(name = "employeeID")
    private int employeeID;

    @Column(name = "name")
    private String name;

    @Column(name = "employmentType")
    private String employmentType;

    @Column(name = "mentor")
    private boolean mentor;

    // Constructors, getters, and setters
    public NewHireInfo() {
    }

    public NewHireInfo(int employeeID, String name, String employmentType, boolean mentor) {
        this.employeeID = employeeID;
        this.name = name;
        this.employmentType = employmentType;
        this.mentor = mentor;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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
        return employeeID;
    }

    public void setMentorId(Integer mentorId) {
        this.employeeID = mentorId;
    }

    public Integer getMenteeId() {
        return employeeID;
    }

    public void setMenteeId(Integer menteeId) {
        this.employeeID = menteeId;
    }

}

