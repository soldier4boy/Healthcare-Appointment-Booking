package com.healthcare.util;

public class User {
    private int userId;
    private String username;
    private String role;
    private String password;
    private String fullName;

    // Constructor
    public User(int userId, String username, String role, String password, String fullName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    // Role-based utility methods
    public boolean isAdmin() {
        return role != null && role.equalsIgnoreCase("Admin");
    }

    public boolean isPatient() {
        return role != null && role.equalsIgnoreCase("Patient");
    }

    public boolean isDoctor() {
        return role != null && role.equalsIgnoreCase("Doctor");
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
