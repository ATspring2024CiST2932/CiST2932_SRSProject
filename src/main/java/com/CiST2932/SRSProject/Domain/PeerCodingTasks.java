// src/main/java/com/CiST2932/SRSProject/Domain/PeerCodingTasks.java
package com.CiST2932.SRSProject.Domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "peercodingtasks")
public class PeerCodingTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    @Column(name = "task_url")
    private String taskUrl;

    @Column(name = "task_number")
    private String taskNumber;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "total_hours")
    private BigDecimal totalHours;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @JsonBackReference
    private NewHireInfo assignee;

    // Constructors, getters, and setters
    public PeerCodingTasks() {
    }

    public PeerCodingTasks(int taskId, String taskUrl, String taskNumber, String taskType, BigDecimal totalHours, NewHireInfo assignee) {
        this.taskId = taskId;
        this.taskUrl = taskUrl;
        this.taskNumber = taskNumber;
        this.taskType = taskType;
        this.totalHours = totalHours;
        this.assignee = assignee;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public NewHireInfo getAssignee() {
        return assignee;
    }

    public void setAssignee(NewHireInfo assignee) {
        this.assignee = assignee;
    }

    public String getAssigneeName() {
        return this.assignee != null ? this.assignee.getName() : null;
    }

    public void setAssigneeName(String assigneeName) {
        if (this.assignee == null) {
            this.assignee = new NewHireInfo();
        }
        this.assignee.setName(assigneeName);
    }
}