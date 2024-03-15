// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfo.java

package com.CiST2932.SRSProject.Domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private boolean isMentor;  // Indicates if this NewHireInfo represents a mentor

    @OneToMany(mappedBy = "mentor")
    @JsonBackReference // Add this annotation
    private List<MentorAssignments> assignmentsAsMentor;

    @OneToMany(mappedBy = "mentee")
    @JsonBackReference // Add this annotation
    private List<MentorAssignments> assignmentsAsMentee;

    // Constructors, getters, and setters
    public NewHireInfo() {
    }

    public NewHireInfo(int employeeId, String name, String employmentType, boolean isMentor) {
        this.employeeId = employeeId;
        this.name = name;
        this.employmentType = employmentType;
        this.isMentor = isMentor;
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
        return isMentor;
    }

    public void setMentor(boolean isMentor) {
        this.isMentor = isMentor;
    }

    public List<MentorAssignments> getAssignmentsAsMentor() {
        return assignmentsAsMentor;
    }

    public void setAssignmentsAsMentor(List<MentorAssignments> assignmentsAsMentor) {
        this.assignmentsAsMentor = assignmentsAsMentor;
    }

    public List<MentorAssignments> getAssignmentsAsMentee() {
        return assignmentsAsMentee;
    }

    public void setAssignmentsAsMentee(List<MentorAssignments> assignmentsAsMentee) {
        this.assignmentsAsMentee = assignmentsAsMentee;
    }   
}
