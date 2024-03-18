// src/main/java/com/CiST2932/SRSProject/Domain/MentorAssignments.java
package com.CiST2932.SRSProject.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "mentorassignments")
public class MentorAssignments {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private int assignmentId;

    @ManyToOne
    @JoinColumn(name = "mentorID", referencedColumnName = "EmployeeID")
    @JsonManagedReference
    private NewHireInfo mentor;

    @ManyToOne
    @JoinColumn(name = "menteeID", referencedColumnName = "EmployeeID")
    @JsonManagedReference
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