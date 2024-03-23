// src/main/java/com/CiST2932/SRSProject/Domain/TaskAssigneeDTO.java
//  For showing the assignee of a selected task:

package com.CiST2932.SRSProject.Domain;

public class TaskAssigneeDTO {
    private int taskId;
    private String taskName;
    private String assigneeName; // Could be a mentee or mentor

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
    // Getter for assigneeName
    public String getAssigneeName() {
        return assigneeName;
    }
    // Setter for assigneeName
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}

