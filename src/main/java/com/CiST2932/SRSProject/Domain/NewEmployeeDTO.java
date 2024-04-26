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
    
    // Detailed relationship fields
    private List<Integer> assignmentsAsMentorIds;
    private List<Integer> assignmentsAsMenteeIds;
    private Integer mentorOrMenteeId; // ID of the mentor or mentee
    private List<NewHireInfo> mentees; // List of mentees if the employee is a mentor
    private List<NewHireInfo> mentor; // List of mentor if the employee is a mentee
    private List<TaskDTO> tasks; // Tasks associated with the employee

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

    public List<NewHireInfo> getMentees() {
        return mentees;
    }

    public void setMentees(List<NewHireInfo> mentees) {
        this.mentees = mentees;
    }

    public List<NewHireInfo> getMentor() {
        return mentor;
    }

    public void setMentor(List<NewHireInfo> mentor) {
        this.mentor = mentor;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
