package com.healthcare.util;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Register a new patient
    public boolean registerPatient(Patient patient) {
        String sql = "INSERT INTO patient (username, password, name, sex, contact, address, admitted_date, discharged_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(patient.getPassword(), BCrypt.gensalt());

            stmt.setString(1, patient.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, patient.getName());
            stmt.setString(4, patient.getSex());
            stmt.setString(5, patient.getContact());
            stmt.setString(6, patient.getAddress());
            stmt.setString(7, patient.getAdmittedDate());
            stmt.setString(8, patient.getDischargedDate());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

 // Authenticate patient login
    public Patient authenticatePatient(String username, String plainPassword) {
        Patient patient = null;
        String sql = "SELECT * FROM patient WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                boolean isValid = false;

                // Try BCrypt comparison
                try {
                    isValid = BCrypt.checkpw(plainPassword, storedPassword);
                } catch (IllegalArgumentException ex) {
                    // Possibly plain text password (not hashed)
                    isValid = plainPassword.equals(storedPassword);
                }

                if (isValid) {
                    patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setUsername(rs.getString("username"));
                    patient.setPassword(storedPassword);
                    patient.setName(rs.getString("name"));
                    patient.setSex(rs.getString("sex"));
                    patient.setContact(rs.getString("contact"));
                    patient.setAddress(rs.getString("address"));
                    patient.setAdmittedDate(rs.getString("admitted_date"));
                    patient.setDischargedDate(rs.getString("discharged_date"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }


    // Get all patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getString("contact"),
                        rs.getString("address"),
                        rs.getString("admitted_date"),
                        rs.getString("discharged_date")
                );
                patients.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }

    // Update patient
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patient SET name = ?, sex = ?, contact = ?, address = ?, admitted_date = ?, discharged_date = ? WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getSex());
            stmt.setString(3, patient.getContact());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getAdmittedDate());
            stmt.setString(6, patient.getDischargedDate());
            stmt.setInt(7, patient.getPatientId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get patient by ID
    public Patient getPatientById(int patientId) {
        Patient patient = null;
        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("sex"),
                        rs.getString("contact"),
                        rs.getString("address"),
                        rs.getString("admitted_date"),
                        rs.getString("discharged_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patient;
    }

    // Get patient name by ID
    public String getPatientNameById(int patientId) {
        String name = null;
        String sql = "SELECT name FROM patient WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    // Add patient from admin's side
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, sex, contact, address, admitted_date, discharged_date, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getSex());
            stmt.setString(3, patient.getContact());
            stmt.setString(4, patient.getAddress());
            stmt.setString(5, patient.getAdmittedDate());
            stmt.setString(6, patient.getDischargedDate());

            String username = patient.getUsername() != null ? patient.getUsername() : "admin_patient_" + System.currentTimeMillis();
            String rawPassword = patient.getPassword() != null ? patient.getPassword() : "default123";
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

            stmt.setString(7, username);
            stmt.setString(8, hashedPassword);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
