package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.ThemeUtil;
import com.healthcare.util.UIEffects;

import javax.swing.*;
import java.awt.*;

public class PatientDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private Patient patient;

    public PatientDashboard(Patient patient) {
        this.patient = patient;

        // Frame setup
        setTitle("Patient Dashboard - Welcome " + patient.getName());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        ThemeUtil.applyTheme();

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + patient.getName(), SwingConstants.CENTER);
        add(welcomeLabel);

        // Buttons
        JButton bookAppointmentBtn = new JButton("Book Appointment");
        JButton myAppointmentsBtn = new JButton("My Appointments");
        JButton viewNotificationsBtn = new JButton("View Notifications");
        JButton logoutBtn = new JButton("Logout");

        // Hover effects
        UIEffects.applyHoverEffect(bookAppointmentBtn);
        UIEffects.applyHoverEffect(myAppointmentsBtn);
        UIEffects.applyHoverEffect(viewNotificationsBtn);
        UIEffects.applyHoverEffect(logoutBtn);

        // Add to layout
        add(bookAppointmentBtn);
        add(myAppointmentsBtn);
        add(viewNotificationsBtn);
        add(logoutBtn);

        // Button actions
        bookAppointmentBtn.addActionListener(e -> new BookAppointmentForm(patient).setVisible(true));
        myAppointmentsBtn.addActionListener(e -> new MyAppointmentsForm(patient).setVisible(true));
        viewNotificationsBtn.addActionListener(e -> new MyNotificationsForm(patient.getPatientId()).setVisible(true));
        logoutBtn.addActionListener(e -> handleLogout());

        setVisible(true);
    }

    private void handleLogout() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
            new PatientLoginForm();
        }
    }

    public static void main(String[] args) {
        // called after login with a real patient
    }
}
