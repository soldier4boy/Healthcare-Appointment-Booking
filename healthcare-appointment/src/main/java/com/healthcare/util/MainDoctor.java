package com.healthcare.util;

public class MainDoctor {
    public static void main(String[] args) {
        // Create a new doctor object
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Priya");
        doctor.setSpecialization("Cardiologist");
        doctor.setContact("8888888888");
        doctor.setAvailable(true); // Assuming availability is a boolean field

        // DAO instance to handle DB operation
        DoctorDAO doctorDAO = new DoctorDAO();
        boolean success = doctorDAO.addDoctor(doctor); // Call the DAO method to add doctor

        // Print result
        if (success) {
            System.out.println("\u2714 Doctor added successfully");
        } else {
            System.out.println("\u274C Failed to add doctor");
        }
    }
}
