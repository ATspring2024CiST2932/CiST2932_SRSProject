// src/main/java/com/CiST2932/SRSProject/Domain/Users.java
package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @OneToOne
    @JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
    @JsonManagedReference
    private NewHireInfo newHireInfo;

    public Users() {
    }

    public Users(int employeeId, String username, String passwordHash, String email, Timestamp registrationDate) {
        this.employeeId = employeeId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public NewHireInfo getNewHireInfo() {
        return newHireInfo;
    }

    public void setNewHireInfo(NewHireInfo newHireInfo) {
        this.newHireInfo = newHireInfo;
    }
}
