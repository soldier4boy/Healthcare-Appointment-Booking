package com.healthcare.ui;

import com.healthcare.util.Doctor;
import com.healthcare.util.DoctorDAO;

import javax.swing.*;
import java.awt.*;

public class DoctorForm extends JFrame {
    private JTextField nameField;
    private JTextField specializationField;
    private JTextField contactField;
    private JCheckBox availableCheckBox;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public DoctorForm() {
        setTitle("Add Doctor");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Fields
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Specialization:"));
        specializationField = new JTextField();
        add(specializationField);

        add(new JLabel("Contact:"));
        contactField = new JTextField();
        add(contactField);

        add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        availableCheckBox.setSelected(true);
        add(availableCheckBox);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveDoctor());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        setVisible(true);
    }

    private void saveDoctor() {
        String name = nameField.getText().trim();
        String specialization = specializationField.getText().trim();
        String contact = contactField.getText().trim();
        boolean available = availableCheckBox.isSelected();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || specialization.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        Doctor doctor = new Doctor(name, specialization, contact, available);
        doctor.setUsername(username);
        doctor.setPassword(password);

        boolean success = new DoctorDAO().addDoctor(doctor);

        if (success) {
            JOptionPane.showMessageDialog(this, "\u2714 Doctor added successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\u274C Failed to add doctor.");
        }
    }
 // Add this main method to test the form standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoctorForm::new);
    }
    }


