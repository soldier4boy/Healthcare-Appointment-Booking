package com.healthcare.util;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String contact;
    private boolean available;

    // Fields for login
    private String username;
    private String password;

    // Constructors
    public Doctor() {
    }

    public Doctor(int doctorId, String name, String specialization, String contact, boolean available) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.available = available;
    }

    public Doctor(String name, String specialization, String contact, boolean available) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.available = available;
    }

    // Constructor for login authentication use-case
    public Doctor(int doctorId, String name, String specialization, String contact, boolean available,
                  String username, String password) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.available = available;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
}
