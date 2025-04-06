package com.healthcare.util;

public class Patient {
    private int patientId;
    private String username;       
    private String password;   
    private String name;
    private String sex;
    private String contact;
    private String address;
    private String admittedDate;
    private String dischargedDate;

    // Default constructor
    public Patient() {}

    // Full constructor
    public Patient(int patientId, String username, String password, String name, String sex,
                   String contact, String address, String admittedDate, String dischargedDate) {
        this.patientId = patientId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.contact = contact;
        this.address = address;
        this.admittedDate = admittedDate;
        this.dischargedDate = dischargedDate;
    }

    // Getters and setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getUsername() {             
        return username;
    }

    public void setUsername(String username) {  
        this.username = username;
    }

    public String getPassword() {             
        return password;
    }

    public void setPassword(String password) {  
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(String admittedDate) {
        this.admittedDate = admittedDate;
    }

    public String getDischargedDate() {
        return dischargedDate;
    }

    public void setDischargedDate(String dischargedDate) {
        this.dischargedDate = dischargedDate;
    }
}
