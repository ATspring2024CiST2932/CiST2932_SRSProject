//src/main/java/com/CiST2932/SRSProject/Domain/NewEmployeeDTO.java
package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;
import java.util.List;

public class NewEmployeeDTO {
    private int employeeId;
    private String name;
    private String email;
    private boolean isMentor;
    private String username;
    private String passwordHash;
    private String employmentType;
    private Timestamp registrationDate;
    private List<Integer> assignmentsAsMentorIds;
    private List<Integer> assignmentsAsMenteeIds;
    private Integer mentorOrMenteeId; // This will store either mentor ID or mentee ID based on the role
    private int mentor;  // Mentor's employee ID
    private int mentee;  // Mentee's employee ID

    // Getters and setters
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getIsMentor() {
        return isMentor;
    }
    public void setIsMentor(boolean isMentor) {
        this.isMentor = isMentor;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Integer> getAssignmentsAsMentorIds() {
        return assignmentsAsMentorIds;
    }

    public void setAssignmentsAsMentorIds(List<Integer> assignmentsAsMentorIds) {
        this.assignmentsAsMentorIds = assignmentsAsMentorIds;
    }

    public List<Integer> getAssignmentsAsMenteeIds() {
        return assignmentsAsMenteeIds;
    }

    public void setAssignmentsAsMenteeIds(List<Integer> assignmentsAsMenteeIds) {
        this.assignmentsAsMenteeIds = assignmentsAsMenteeIds;
    }

    public Integer getMentorOrMenteeId() {
        return mentorOrMenteeId;
    }
    
    public void setMentorOrMenteeId(Integer mentorOrMenteeId) {
        this.mentorOrMenteeId = mentorOrMenteeId;
    }

    public int getMentor() {
        return mentor;
    }
    
    public void setMentor(int mentor) {
        this.mentor = mentor;
    }
    
    public int getMentee() {
        return mentee;
    }

    public void setMentee(int mentee) {
        this.mentee = mentee;
    }
    
}