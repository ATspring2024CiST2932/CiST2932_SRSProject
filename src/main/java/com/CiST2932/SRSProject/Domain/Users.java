// src/main/java/com/CiST2932/SRSProject/Domain/Users.java
package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "EmployeeID", referencedColumnName = "employeeId")
    private NewHireInfo developer;  // this links back to NewHireInfo
    

    public Users() {
    }

    public Users(int userId, String username, String passwordHash, String email, Timestamp registrationDate, NewHireInfo developer) {
        this.user_id = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationDate = registrationDate;
        this.developer= developer;
        
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
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

    public NewHireInfo getDeveloper() {
        return developer;
    }

    public void setDeveloper(NewHireInfo developer) {
        this.developer= developer;
    }

     public String getDeveloperName () {
        return this.developer!= null ? this.developer.getName(): null;
    }
    
    public void setDeveloperName(String developerName) {
        if (this.developer== null) {
            this.developer= new NewHireInfo();
        }
        this.developer.setName(developerName);
    }

}