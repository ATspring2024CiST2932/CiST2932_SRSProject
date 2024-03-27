package com.CiST2932.SRSProject.Domain;

import java.math.BigDecimal;

public class CreatePeerCodingTasksDTO {
    private String taskUrl;
    private String taskNumber;
    private String taskType;
    private BigDecimal totalHours;
    private String assigneeName;

    //constructor
    public CreatePeerCodingTasksDTO(String taskUrl, String taskNumber, String taskType, BigDecimal totalHours, String assigneeName) {
        this.taskUrl = taskUrl;
        this.taskNumber = taskNumber;
        this.taskType = taskType;
        this.totalHours = totalHours;
        this.assigneeName = assigneeName;
    }

    //getter for taskUrl
    public String getTaskUrl() {
        return taskUrl;
    }
    //setter for taskUrl
    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }
    //getter for taskNumber
    public String getTaskNumber() {
        return taskNumber;
    }
    //setter for taskNumber
    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }
    //getter for taskType
    public String getTaskType() {
        return taskType;
    }
    //setter for taskType
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    //getter for totalHours
    public BigDecimal getTotalHours() {
        return totalHours;
    }
    //setter for totalHours
    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }
    //getter for assigneeName
    public String getAssigneeName() {
        return assigneeName;
    }
    //setter for assigneeName
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

}

