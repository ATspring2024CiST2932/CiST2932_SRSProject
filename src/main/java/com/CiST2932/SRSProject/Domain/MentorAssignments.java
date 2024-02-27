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
    @Column(name = "assignmentId")
    private int assignmentId;

    @Column(name = "mentorId")
    private int mentorId;

    @Column(name = "menteeId")
    private int menteeId;

    // Constructors, getters, and setters
    public MentorAssignments() {
    }

    public MentorAssignments(int assignmentId, int mentorId, int menteeId) {
        this.assignmentId = assignmentId;
        this.mentorId = mentorId;
        this.menteeId = menteeId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }
}
