package com.healthcare.ui;

import javax.swing.*;
import java.awt.*;
import com.healthcare.util.User;
import com.healthcare.util.ThemeUtil;
import com.healthcare.util.UIEffects;

public class MainDashboard extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainDashboard(User receptionistUser) {
        setTitle("Main Dashboard - Admin Name");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Apply Theme
        ThemeUtil.applyTheme();

        // Buttons for actions
        JButton appointmentBtn = new JButton("Manage Appointments");
        JButton doctorBtn = new JButton("Manage Doctors");
        JButton patientBtn = new JButton("Manage Patients");
        JButton logoutBtn = new JButton("Logout");
        JButton addDoctorBtn = new JButton("Add Doctor");
        JButton addPatientBtn = new JButton("Add Patient");

        // Apply hover effects
        UIEffects.applyHoverEffect(appointmentBtn);
        UIEffects.applyHoverEffect(doctorBtn);
        UIEffects.applyHoverEffect(patientBtn);
        UIEffects.applyHoverEffect(addDoctorBtn);
        UIEffects.applyHoverEffect(addPatientBtn);
        UIEffects.applyHoverEffect(logoutBtn);

        // Add buttons to the frame
        add(appointmentBtn);
        add(doctorBtn);
        add(patientBtn);
        add(addDoctorBtn);
        add(addPatientBtn);
        add(logoutBtn);

        // Action listeners
        appointmentBtn.addActionListener(e -> new AppointmentList());
        doctorBtn.addActionListener(e -> new DoctorList());
        patientBtn.addActionListener(e -> new PatientList());
        addDoctorBtn.addActionListener(e -> new DoctorForm());
        addPatientBtn.addActionListener(e -> new PatientForm());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new MainDashboard(new User(1, "admin", "admin123", "Admin", "admin"))
        );
    }
}
