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
    private int task_id;

    @Column(name = "task_url")
    private String task_url;

    @Column(name = "task_number")
    private String task_number;

    @Column(name = "task_type")
    private String task_type;

    @Column(name = "total_hours")
    private BigDecimal total_hours;

    @Column(name = "employeeID")
    private int employeeID;

    public PeerCodingTasks() {
    }

    public PeerCodingTasks(int task_id, String task_url, String task_number, String task_type, BigDecimal total_hours, int employeeID) {
        this.task_id = task_id;
        this.task_url = task_url;
        this.task_number = task_number;
        this.task_type = task_type;
        this.total_hours = total_hours;
        this.employeeID = employeeID;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_url() {
        return task_url;
    }

    public void setTask_url(String task_url) {
        this.task_url = task_url;
    }

    public String getTask_number() {
        return task_number;
    }

    public void setTask_number(String task_number) {
        this.task_number = task_number;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public BigDecimal getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(BigDecimal total_hours) {
        this.total_hours = total_hours;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}
