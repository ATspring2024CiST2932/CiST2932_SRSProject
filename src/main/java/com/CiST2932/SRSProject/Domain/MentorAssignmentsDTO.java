//src/main/java/com/CiST2932/SRSProject/Domain/MentorAssignmentsDTO.java
package com.CiST2932.SRSProject.Domain;

public class MentorAssignmentsDTO {
    private int assignmentId;  // Unique ID for the mentorship assignment
    private int mentorId;      // Employee ID of the mentor
    private int menteeId;      // Employee ID of the mentee

    public MentorAssignmentsDTO(int mentorEmployeeId, int menteeEmployeeId) {
        this.mentorId = mentorEmployeeId;
        this.menteeId = menteeEmployeeId;
    }

    public int getAssignmentId() {return assignmentId;}
    public void setAssignmentId(int assignmentId) {this.assignmentId = assignmentId;}

    public int getMentorId() {return mentorId;}
    public void setMentorId(int mentorId) {this.mentorId = mentorId;}

    public int getMenteeId() {return menteeId;}
    public void setMenteeId(int menteeId) {this.menteeId = menteeId;}

}