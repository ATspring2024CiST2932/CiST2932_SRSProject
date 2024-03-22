package com.CiST2932.SRSProject.Domain;

public class NewHireInfoDto {
    private int employeeId;
    private String name;
    private String employmentType;
    private boolean isMentor;

    public NewHireInfoDto(int employeeId, String name, String employmentType) {
        this.employeeId = employeeId;
        this.name = name;
        this.employmentType = employmentType;
    }
    
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

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public boolean getIsMentor() {
        return isMentor;
    }

    public void setisMentor(boolean isMentor) {
        this.isMentor = isMentor;
    }
}
