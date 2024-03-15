// src/main/java/com/CiST2932/SRSProject/Domain/TaskDTO.java

package com.CiST2932.SRSProject.Domain;

public class TaskDTO {
    private int taskId;
    private String taskUrl;
    private int taskNumber;
    private String taskType;
    private int employeeId;

    // Constructor
    public TaskDTO() {}

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getTaskType() {
        return taskType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    // Setters
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}

