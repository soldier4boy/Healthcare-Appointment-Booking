package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.User;
import javax.swing.*;
import java.awt.*;

public class MainAppointment extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainAppointment(User receptionistUser) {
        setTitle("Welcome " + receptionistUser.getUsername());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Add various actions
        JButton bookAppointmentBtn = new JButton("Book Appointment");
        JButton viewAppointmentsBtn = new JButton("View Appointments");
        JButton logoutBtn = new JButton("Logout");

        add(new JLabel("Welcome, " + receptionistUser.getUsername(), SwingConstants.CENTER));
        add(bookAppointmentBtn);
        add(viewAppointmentsBtn);
        add(logoutBtn);

        // Action listeners for buttons
        bookAppointmentBtn.addActionListener(e -> {
      
        	// Dummy Patient instantiation
        	Patient dummyPatient = new Patient(1, "John Doe", "999-999-9999", "123 Fake Street", "johndoe", "password123", "Male", "2024-01-01", "2024-01-02");
        	new BookAppointmentForm(dummyPatient); // Pass Patient object

            new BookAppointmentForm(dummyPatient); // Pass Patient object
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm(); // Go back to the login screen
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppointment(new User(1, "admin", "Admin", "password", "Admin Name")));
    }
}
