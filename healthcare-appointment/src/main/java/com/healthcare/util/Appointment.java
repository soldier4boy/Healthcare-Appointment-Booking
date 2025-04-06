package com.healthcare.util;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private String doctorName;
    private String specialization;
    private String status;
    private String patientName;


    // Constructor for booking appointments
    public Appointment(int patientId, int doctorId, Date appointmentDate, String doctorName, String specialization, String status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.status = status;
    }

    // Constructor for viewing appointments
    public Appointment(int appointmentId, int patientId, int doctorId, Date appointmentDate, String doctorName, String specialization, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.status = status;
    }
    
    public Appointment() {
        // Default constructor
    }


    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getStatus() {
        return status;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

}

