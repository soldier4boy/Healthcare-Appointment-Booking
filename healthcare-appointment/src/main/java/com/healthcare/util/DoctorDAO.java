package com.healthcare.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DoctorDAO {

    // Method to get all doctors
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String contact = rs.getString("contact");
                boolean available = rs.getBoolean("available");

                Doctor doctor = new Doctor(doctorId, name, specialization, contact, available);
                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    // Method to get doctor name by ID
    public String getDoctorNameById(int doctorId) {
        String sql = "SELECT name FROM doctor WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get doctor by name (used in appointment filtering or selection)
    public Doctor getDoctorByName(String name) {
        String sql = "SELECT * FROM doctor WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String specialization = rs.getString("specialization");
                String contact = rs.getString("contact");
                boolean available = rs.getBoolean("available");

                return new Doctor(doctorId, name, specialization, contact, available);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get appointments for a specific doctor
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setAppointmentDate(rs.getTimestamp("appointment_date"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setStatus(rs.getString("status"));

                // Set patient name using PatientDAO
                String patientName = new PatientDAO().getPatientNameById(a.getPatientId());
                a.setPatientName(patientName);

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Method to fetch doctor ID by name
    public int getDoctorIdByName(String name) {
        String sql = "SELECT doctor_id FROM doctor WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("doctor_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }
    
    

    public Doctor authenticateDoctor(String username, String password) {
        String sql = "SELECT * FROM doctor WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (password.equals(storedHashedPassword)) {

                    int doctorId = rs.getInt("doctor_id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String contact = rs.getString("contact");
                    boolean available = rs.getBoolean("available");

                    Doctor doctor = new Doctor(doctorId, name, specialization, contact, available);
                    doctor.setUsername(username); 
                    return doctor;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, specialization, contact, available, username, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getContact());
            stmt.setBoolean(4, doctor.isAvailable());
            stmt.setString(5, doctor.getUsername());
            stmt.setString(6, doctor.getPassword());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Doctor getDoctorByCredentials(String username, String password) {
        String sql = "SELECT * FROM doctor WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("contact"),
                    rs.getBoolean("available")
                );
                doctor.setDoctorId(rs.getInt("doctor_id"));
                doctor.setUsername(rs.getString("username"));
                doctor.setPassword(rs.getString("password"));
                return doctor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
