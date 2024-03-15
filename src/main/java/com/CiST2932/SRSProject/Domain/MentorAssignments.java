// src/main/java/com/CiST2932/SRSProject/Domain/MentorAssignments.java
package com.CiST2932.SRSProject.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mentorassignments")
public class MentorAssignments {
    @Id
    @Column(name = "AssignmentID")
    private int assignmentId;

    @ManyToOne
    @JoinColumn(name = "mentorID", referencedColumnName = "EmployeeID")
    @JsonManagedReference // Add this annotation
    private NewHireInfo mentor;

    @ManyToOne
    @JoinColumn(name = "menteeID", referencedColumnName = "EmployeeID")
    @JsonManagedReference // Add this annotation
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
