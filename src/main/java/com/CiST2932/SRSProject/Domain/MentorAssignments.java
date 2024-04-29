// src/main/java/com/CiST2932/SRSProject/Domain/MentorAssignments.java
package com.CiST2932.SRSProject.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "mentorassignments")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "assignmentId")
public class MentorAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentorID", referencedColumnName = "EmployeeID")
    @JsonBackReference //("mentor-assignments-mentor")
    private NewHireInfo mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menteeID", referencedColumnName = "EmployeeID")
    @JsonBackReference //("mentor-assignments-mentee")
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

    public int getId() {
        return assignmentId;
    }
}