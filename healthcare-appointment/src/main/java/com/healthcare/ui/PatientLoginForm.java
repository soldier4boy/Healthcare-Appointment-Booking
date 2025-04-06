package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;

import javax.swing.*;
import java.awt.*;

public class PatientLoginForm extends JFrame {

    private static final long serialVersionUID = 1L;

    // Declare UI components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;

    // Constructor to set up the UI
    public PatientLoginForm() {
        setTitle("Patient Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Initialize UI components
        add(new JLabel("Username"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginBtn = new JButton("Login");
        add(loginBtn);

        // Add ActionListener for login button
        loginBtn.addActionListener(e -> handleLogin());

        // Set visibility of form
        setVisible(true);
    }

    // Handle login action
    private void handleLogin() {
        // Extract username and password from fields
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Initialize PatientDAO for database operations
        PatientDAO dao = new PatientDAO();

        // Authenticate patient using the entered credentials
        Patient patient = dao.authenticatePatient(username, password);

        if (patient != null) {
            // If patient is found, proceed to Patient Dashboard
            JOptionPane.showMessageDialog(this, "Login successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close login form
            new PatientDashboard(patient); // Open patient dashboard
        } else {
            // If authentication fails, show error message
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the login form
    public static void main(String[] args) {
        new PatientLoginForm();  // Initialize and show the login form
    }
}
