// src/main/java/com/CiST2932/SRSProject/Domain/Users.java

package com.CiST2932.SRSProject.Domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")  // Table name in the database
@Entity
public class Users {
    @Id
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String password_hash;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private Timestamp registration_date;

    public Users() {
    }

    public Users(int user_id, String username, String password_hash, String email, Timestamp registration_date) {
        this.user_id = user_id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.registration_date = registration_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Timestamp registration_date) {
        this.registration_date = registration_date;
    }
}
