package com.healthcare.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAO {

    // Method to book a new appointment
    public boolean bookAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, 'Scheduled')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(appointment.getAppointmentDate());

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, formattedDate);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to update an existing appointment
    public boolean updateAppointment(int appointmentId, String dateTime, int doctorId, String status) {
        String sql = "UPDATE appointment SET appointment_date = ?, doctor_id = ?, status = ? WHERE appointment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dateTime);
            stmt.setInt(2, doctorId);
            stmt.setString(3, status);
            stmt.setInt(4, appointmentId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to cancel an appointment
    public boolean cancelAppointment(int appointmentId) {
        String sql = "UPDATE appointment SET status = 'Cancelled' WHERE appointment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointmentId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to get all appointments (for admin)
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.status, " +
                     "d.name AS doctor_name, d.specialization AS specialization " +
                     "FROM appointment a JOIN doctor d ON a.doctor_id = d.doctor_id " +
                     "ORDER BY a.appointment_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int patientId = rs.getInt("patient_id");
                int doctorId = rs.getInt("doctor_id");
                Timestamp ts = rs.getTimestamp("appointment_date");
                Date appointmentDate = new Date(ts.getTime());
                String doctorName = rs.getString("doctor_name");
                String specialization = rs.getString("specialization");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(
                    appointmentId, patientId, doctorId,
                    appointmentDate, doctorName, specialization, status
                );
                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // Method to retrieve appointments by patient ID with doctor info
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.status, d.name, d.specialization " +
                     "FROM appointment a JOIN doctor d ON a.doctor_id = d.doctor_id " +
                     "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int doctorId = rs.getInt("doctor_id");
                Timestamp ts = rs.getTimestamp("appointment_date");
                Date appointmentDate = new Date(ts.getTime());
                String doctorName = rs.getString("name");
                String specialization = rs.getString("specialization");
                String status = rs.getString("status");

                Appointment appointment = new Appointment(
                    appointmentId,
                    patientId,
                    doctorId,
                    appointmentDate,
                    doctorName,
                    specialization,
                    status
                );

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


    
    // Get appointment by appointment ID
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT a.appointment_id, a.patient_id, a.doctor_id, a.appointment_date, a.status, d.name, d.specialization " +
                     "FROM appointment a JOIN doctor d ON a.doctor_id = d.doctor_id " +
                     "WHERE a.appointment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int patientId = rs.getInt("patient_id");
                int doctorId = rs.getInt("doctor_id");
                Timestamp ts = rs.getTimestamp("appointment_date");
                Date appointmentDate = new Date(ts.getTime());
                String doctorName = rs.getString("name");
                String specialization = rs.getString("specialization");
                String status = rs.getString("status");

                return new Appointment(
                    appointmentId,
                    patientId,
                    doctorId,
                    appointmentDate,
                    doctorName,
                    specialization,
                    status
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setTimestamp(3, new Timestamp(appointment.getAppointmentDate().getTime()));
            stmt.setString(4, appointment.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
 // Update appointment status
    public boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE appointment SET status = ? WHERE appointment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, appointmentId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
