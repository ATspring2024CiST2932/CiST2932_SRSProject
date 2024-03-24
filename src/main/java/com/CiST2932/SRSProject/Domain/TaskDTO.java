// src/main/java
package com.CiST2932.SRSProject.Domain;

import java.math.BigDecimal;

public class TaskDTO {
    private int taskId;
    private String taskUrl;
    private String taskNumber;
    private String taskType;
    private BigDecimal totalHours;
    private String assigneeName; // Name of the person assigned to the task

   // Getter for taskId
    public int getTaskId() {
        return taskId;
    }

    // Setter for taskId
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    // Getter for taskUrl
    public String getTaskUrl() {
        return taskUrl;
    }

    // Setter for taskUrl
    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    // Getter for taskNumber
    public String getTaskNumber() {
        return taskNumber;
    }

    // Setter for taskNumber
    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    // Getter for taskType
    public String getTaskType() {
        return taskType;
    }

    // Setter for taskType
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    // Getter for totalHours
    public BigDecimal getTotalHours() {
        return totalHours;
    }

    // Setter for totalHours
    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
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
