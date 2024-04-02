package com.CiST2932.SRSProject.Domain;

public class NewEmployeeDTO {
    private int employeeId;
    private String name;
    private String email;
    private boolean isMentor;
    private String username;
    private String password;
    private String employmentType;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }
    
}
