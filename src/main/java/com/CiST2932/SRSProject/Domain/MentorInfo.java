//src/main/java/com/CiST2932/SRSProject/Domain/MentorInfo.java

package com.CiST2932.SRSProject.Domain;

import java.util.List;

public class MentorInfo {
    private int mentorId;
    private List<NewHireInfo> mentees;

    // Constructor
    public MentorInfo(int mentorId, List<NewHireInfo> mentees) {
        this.mentorId = mentorId;
        this.mentees = mentees;
    }

    // Getters and Setters
    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public List<NewHireInfo> getMentees() {
        return mentees;
    }

    public void setMentees(List<NewHireInfo> mentees) {
        this.mentees = mentees;
    }
}
