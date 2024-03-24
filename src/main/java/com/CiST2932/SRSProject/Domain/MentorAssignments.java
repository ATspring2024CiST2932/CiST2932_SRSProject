// src/main/java/com/CiST2932/SRSProject/Domain/MentorAssignments.java
package com.CiST2932.SRSProject.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "mentorassignments")
public class MentorAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private int assignmentId;

    @ManyToOne
    @JoinColumn(name = "mentorID", referencedColumnName = "EmployeeID")
    @JsonBackReference
    private NewHireInfo mentor;

    @ManyToOne
    @JoinColumn(name = "menteeID", referencedColumnName = "EmployeeID")
    @JsonBackReference
    private NewHireInfo mentee;
    
    // Constructors, getters, and setters
    public MentorAssignments() {
    }

    public MentorAssignments(int assignmentId, NewHireInfo mentor, NewHireInfo mentee) {
        this.assignmentId = assignmentId;
        this.mentor = mentor;
        this.mentee = mentee;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public NewHireInfo getMentor() {
        return mentor;
    }

    public void setMentor(NewHireInfo mentor) {
        this.mentor = mentor;
    }

    public NewHireInfo getMentee() {
        return mentee;
    }

    public void setMentee(NewHireInfo mentee) {
        this.mentee = mentee;
    }
}