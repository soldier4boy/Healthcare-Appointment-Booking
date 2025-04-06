package com.healthcare.ui;

import com.healthcare.util.Doctor;
import com.healthcare.util.ThemeUtil;
import com.healthcare.util.UIEffects;

import javax.swing.*;
import java.awt.*;

public class DoctorDashboard extends JFrame {
    private Doctor doctor;

    public DoctorDashboard(Doctor doctor) {
        this.doctor = doctor;

        setTitle("Doctor Dashboard - Welcome Dr. " + doctor.getName());
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(3, 1, 10, 10));

        ThemeUtil.applyTheme();

        JLabel welcomeLabel = new JLabel("Welcome, " + doctor.getName(), SwingConstants.CENTER);

        JButton viewAppointmentsBtn = new JButton("View My Appointments");
        JButton logoutBtn = new JButton("Logout");

        // Apply hover effects
        UIEffects.applyHoverEffect(viewAppointmentsBtn);
        UIEffects.applyHoverEffect(logoutBtn);

        // Wrap buttons in center-aligned panels
        JPanel btnPanel1 = new JPanel();
        btnPanel1.add(viewAppointmentsBtn);
        JPanel btnPanel2 = new JPanel();
        btnPanel2.add(logoutBtn);

        add(welcomeLabel);
        add(btnPanel1);
        add(btnPanel2);

        // Button actions
        viewAppointmentsBtn.addActionListener(e -> new DoctorAppointmentsForm(doctor));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        // Temporary standalone test
        Doctor testDoctor = new Doctor();
        testDoctor.setDoctorId(1);
        testDoctor.setName("Test Doctor");
        testDoctor.setUsername("doc_test");
        testDoctor.setPassword("doc123");
        testDoctor.setSpecialization("Cardiology");
        testDoctor.setContact("123-456-7890");
        testDoctor.setAvailable(true);

        SwingUtilities.invokeLater(() -> new DoctorDashboard(testDoctor));
    }
}
