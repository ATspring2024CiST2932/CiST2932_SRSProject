package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;

public class NewEmployeeDTO {
    private int employeeId;
    private String name;
    private String email;
    private boolean isMentor;
    private String username;
    private String passwordHash;
    private String employmentType;
    private Timestamp registrationDate;

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
    
}