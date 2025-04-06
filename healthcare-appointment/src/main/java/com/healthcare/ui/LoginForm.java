package com.healthcare.ui;

import com.healthcare.util.User;
import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;
import com.healthcare.util.Doctor;
import com.healthcare.util.DoctorDAO;
import com.healthcare.util.ThemeUtil;
import com.healthcare.util.UIEffects;
import com.healthcare.util.Validator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginBtn;

    public LoginForm() {
        setTitle("Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        ThemeUtil.applyTheme();

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Role
        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Patient", "Doctor"});
        add(roleComboBox);

        // Login button
        loginBtn = new JButton("Login");
        UIEffects.applyHoverEffect(loginBtn);
        add(loginBtn);

        // Add real-time field validation
        addValidation(usernameField);
        addValidation(passwordField);

        // Action
        loginBtn.addActionListener(e -> handleLogin());

        setVisible(true);
    }

    private void addValidation(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { Validator.highlightIfEmpty(field); }
            public void removeUpdate(DocumentEvent e) { Validator.highlightIfEmpty(field); }
            public void changedUpdate(DocumentEvent e) { Validator.highlightIfEmpty(field); }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (role.equalsIgnoreCase("Admin")) {
            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Welcome Admin!");
                User adminUser = new User(1, username, "Admin", password, "Admin Name");
                new MainDashboard(adminUser).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials", "\u274C Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (role.equalsIgnoreCase("Patient")) {
            PatientDAO dao = new PatientDAO();
            Patient patient = dao.authenticatePatient(username, password);

            if (patient != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + patient.getName() + "!");
                new PatientDashboard(patient);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid patient credentials", "\u274C Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (role.equalsIgnoreCase("Doctor")) {
            Doctor doctor = new DoctorDAO().authenticateDoctor(username, password);
            if (doctor != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + doctor.getName() + "!");
                new DoctorDashboard(doctor);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid doctor credentials", "\u274C Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        else {
            JOptionPane.showMessageDialog(this, "Invalid role selected", "\u274C Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
