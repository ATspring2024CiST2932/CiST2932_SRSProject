// src/main/java/com/CiST2932/SRSProject/Domain/Users.java
package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    private int employeeId; // This will be populated automatically from newHireInfo's ID.

    @OneToOne
    @MapsId
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    private NewHireInfo newHireInfo;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    public Users() {
    }

    public Users(int employeeId, String username, String passwordHash, String email, Timestamp registrationDate, NewHireInfo newHireInfo) {
        this.employeeId = employeeId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationDate = registrationDate;
        this.newHireInfo = newHireInfo;
    }

    public int getEmployeeId() {
        return employeeId; // Automatically mirrors the ID of newHireInfo
    }

    public NewHireInfo getNewHireInfo() {
        return newHireInfo;
    }

    public void setNewHireInfo(NewHireInfo newHireInfo) {
        this.newHireInfo = newHireInfo;
        // The employeeId is automatically managed through the @MapsId, no need to set it explicitly
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
}