//src/main/java/com/CiST2932/SRSProject/Domain/Student.java

package com.CiST2932.SRSProject.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "mentorassignments")  // Table name in the database
@Entity
public class MentorAssignments {
    @Id
    @Column(name = "assignmentID")
    private int assignmentID;

    @Column(name = "mentorID")
    private int mentorID;

    @Column(name = "menteeID")
    private int menteeID;

    // Constructors, getters, and setters
    public MentorAssignments() {
    }

    public MentorAssignments(int assignmentID, int mentorID, int menteeID) {
        this.assignmentID = assignmentID;
        this.mentorID = mentorID;
        this.menteeID = menteeID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getMenteeID() {
        return menteeID;
    }

    public void setMenteeID(int menteeID) {
        this.menteeID = menteeID;
    }
}
