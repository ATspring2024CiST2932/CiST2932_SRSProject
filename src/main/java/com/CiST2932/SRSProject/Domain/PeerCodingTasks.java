//src/main/java/com/CiST2932/SRSProject/Domain/PeerCodingTasks.java

package com.CiST2932.SRSProject.Domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "peercodingtasks")  // Table name in the database
@Entity
public class PeerCodingTasks {
    @Id
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

    @Column(name = "EmployeeID")
    private int employeeId;

    public PeerCodingTasks() {
    }

    public PeerCodingTasks(int taskId, String taskUrl, String taskNumber, String taskType, BigDecimal totalHours, int employeeId) {
        this.taskId = taskId;
        this.taskUrl = taskUrl;
        this.taskNumber = taskNumber;
        this.taskType = taskType;
        this.totalHours = totalHours;
        this.employeeId = employeeId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
