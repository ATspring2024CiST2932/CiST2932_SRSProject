// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfo.java
package com.CiST2932.SRSProject.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "newhireinfo")
public class NewHireInfo {
    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "EmploymentType")
    private String employmentType;

    @Column(name = "Mentor")
    private boolean isMentor;

    @OneToMany(mappedBy = "mentor")
    @JsonBackReference
    private List<MentorAssignments> assignmentsAsMentor;

    @OneToMany(mappedBy = "mentee")
    @JsonBackReference
    private List<MentorAssignments> assignmentsAsMentee;

    @OneToMany(mappedBy = "assignee")
    @JsonBackReference
    private List<PeerCodingTasks> assignedTasks;

    @OneToOne(mappedBy = "newHireInfo")
    private Users user;

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

    public List<PeerCodingTasks> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<PeerCodingTasks> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
