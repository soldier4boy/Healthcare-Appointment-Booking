package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;
import com.healthcare.util.ThemeUtil;
import com.healthcare.util.UIEffects;

import javax.swing.*;
import java.awt.*;

public class PatientSelfRegistrationForm extends JFrame {
    private JTextField nameField, contactField, addressField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> sexComboBox;
    private JButton saveBtn;

    public PatientSelfRegistrationForm() {
        setTitle("Patient Registration");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Apply Theme
        ThemeUtil.applyTheme();

        // Form fields
        nameField = new JTextField();
        contactField = new JTextField();
        addressField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        saveBtn = new JButton("Register");

        // Apply hover effect
        UIEffects.applyHoverEffect(saveBtn);

        // Add components
        add(new JLabel("Full Name:"));
        add(nameField);
        add(new JLabel("Contact:"));
        add(contactField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Sex:"));
        add(sexComboBox);
        add(new JLabel()); // spacer
        add(saveBtn);

        // Action listener for Save button
        saveBtn.addActionListener(e -> handleSave());

        setVisible(true);
    }

    private void handleSave() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String sex = (String) sexComboBox.getSelectedItem();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty() || username.isEmpty() || password.isEmpty() || sex.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit contact number.");
            return;
        }

        Patient patient = new Patient();
        patient.setName(name);
        patient.setContact(contact);
        patient.setAddress(address);
        patient.setUsername(username);
        patient.setPassword(password);
        patient.setSex(sex);
        patient.setAdmittedDate("");
        patient.setDischargedDate("");

        PatientDAO dao = new PatientDAO();
        boolean success = dao.registerPatient(patient);

        if (success) {
            JOptionPane.showMessageDialog(this, "\u2714 Patient registered successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\u274C Registration failed. Try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PatientSelfRegistrationForm::new);
    }
}
