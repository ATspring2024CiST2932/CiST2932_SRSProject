// src/main/java/com/CiST2932/SRSProject/Domain/AdminTaskInfoDTO.java
// For an administrator viewing details of a task

package com.CiST2932.SRSProject.Domain; 

public class AdminTaskInfoDTO {
    private int taskId;
    private String taskName;
    private String employeeAssigned;
    private String mentorAssigned; // If the employee is a mentee

    // Getter for taskId
    public int getTaskId() {
        return taskId;
    }
    // Setter for taskId
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    // Getter for taskName
    public String getTaskName() {
        return taskName;
    }
    // Setter for taskName
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    // Getter for employeeAssigned
    public String getEmployeeAssigned() {
        return employeeAssigned;
    }
    // Setter for employeeAssigned
    public void setEmployeeAssigned(String employeeAssigned) {
        this.employeeAssigned = employeeAssigned;
    }
    // Getter for mentorAssigned
    public String getMentorAssigned() {
        return mentorAssigned;
    }
    
}
