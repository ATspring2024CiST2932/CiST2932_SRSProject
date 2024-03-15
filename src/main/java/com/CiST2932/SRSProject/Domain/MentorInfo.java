//src/main/java/com/CiST2932/SRSProject/Domain/MentorInfo.java

package com.CiST2932.SRSProject.Domain;

import java.util.List;

public class MentorInfo {
    private int mentor;
    private List<NewHireInfo> mentees;

    // Constructor
    public MentorInfo(int mentor, List<NewHireInfo> mentees) {
        this.mentor = mentor;
        this.mentees = mentees;
    }

    // Getters and Setters
    public int getmentor() {
        return mentor;
    }

    public void setmentor(int mentor) {
        this.mentor = mentor;
    }

    public List<NewHireInfo> getMentees() {
        return mentees;
    }

    public void setMentees(List<NewHireInfo> mentees) {
        this.mentees = mentees;
    }
}
