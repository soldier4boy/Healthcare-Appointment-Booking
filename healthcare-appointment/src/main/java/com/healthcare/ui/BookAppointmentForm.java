package com.healthcare.ui;

import com.healthcare.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class BookAppointmentForm extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> doctorComboBox;
    private JTextField dateTimeField;
    private Patient patient;
    private HashMap<String, Integer> doctorMap = new HashMap<>();

    public BookAppointmentForm(Patient patient) {
        this.patient = patient;

        setTitle("Book Appointment - " + patient.getName());
        setSize(450, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Apply theme
        ThemeUtil.applyTheme();

        // UI Components
        add(new JLabel("Select Doctor:"));
        doctorComboBox = new JComboBox<>();
        populateDoctors();
        add(doctorComboBox);

        add(new JLabel("Appointment Date (yyyy-MM-dd HH:mm):"));
        dateTimeField = new JTextField();
        add(dateTimeField);

        JButton bookBtn = new JButton("Book Appointment");
        JButton cancelBtn = new JButton("Cancel");

        // Hover effects
        UIEffects.applyHoverEffect(bookBtn);
        UIEffects.applyHoverEffect(cancelBtn);

        add(bookBtn);
        add(cancelBtn);

        bookBtn.addActionListener(e -> bookAppointment());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void populateDoctors() {
        DoctorDAO dao = new DoctorDAO();
        List<Doctor> doctors = dao.getAllDoctors();

        for (Doctor doctor : doctors) {
            String label = doctor.getSpecialization() + " - " + doctor.getName();
            doctorMap.put(label, doctor.getDoctorId());
            doctorComboBox.addItem(label);
        }
    }

    private void bookAppointment() {
        String selectedDoctorLabel = (String) doctorComboBox.getSelectedItem();
        String appointmentDateStr = dateTimeField.getText().trim();

        if (selectedDoctorLabel == null || appointmentDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a doctor and enter a date.");
            return;
        }

        try {
            int doctorId = doctorMap.get(selectedDoctorLabel);
            Timestamp appointmentTime = Timestamp.valueOf(appointmentDateStr + ":00");

            Appointment appointment = new Appointment();
            appointment.setPatientId(patient.getPatientId());
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDate(appointmentTime);
            appointment.setStatus("Scheduled");

            AppointmentDAO dao = new AppointmentDAO();
            boolean success = dao.addAppointment(appointment);

            if (success) {
                JOptionPane.showMessageDialog(this, "\u2714 Appointment booked successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "\u274C Failed to book appointment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error booking appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
