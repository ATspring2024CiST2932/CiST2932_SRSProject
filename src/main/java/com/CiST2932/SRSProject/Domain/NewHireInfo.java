// src/main/java/com/CiST2932/SRSProject/Domain/NewHireInfo.java
package com.CiST2932.SRSProject.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MentorAssignments> assignmentsAsMentor;

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MentorAssignments> assignmentsAsMentee;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PeerCodingTasks> assignedTasks;

    @OneToOne (mappedBy = "newHireInfo", cascade = CascadeType.ALL)
    @JsonManagedReference
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

    public boolean getIsMentor() {
        return isMentor;
    }    

    public void setIsMentor(boolean isMentor) {
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
