package com.CiST2932.SRSProject.Domain;

import java.math.BigDecimal;

public class UpdatePeerCodingTasksDTO {
    private String taskUrl;
    private String taskNumber;
    private String taskType;
    private BigDecimal totalHours;
    private String assigneeName;

    // Constructors, getters, and setters

    public UpdatePeerCodingTasksDTO() {
    }

    public UpdatePeerCodingTasksDTO(String taskUrl, String taskNumber, String taskType, BigDecimal totalHours, String assigneeName) {
        this.taskUrl = taskUrl;
        this.taskNumber = taskNumber;
        this.taskType = taskType;
        this.totalHours = totalHours;
        this.assigneeName = assigneeName;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public BigDecimal getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

   
}
