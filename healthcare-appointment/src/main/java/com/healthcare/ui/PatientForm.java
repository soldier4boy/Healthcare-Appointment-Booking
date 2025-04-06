package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;

import javax.swing.*;
import java.awt.*;

public class PatientForm extends JFrame {
    private JTextField nameField, contactField, addressField, admittedDateField, dischargedDateField;
    private JComboBox<String> sexComboBox;
    private JButton saveButton, cancelButton;
    private Patient patient;

    public PatientForm(Patient patientToEdit) {
        this.patient = (patientToEdit != null) ? patientToEdit : new Patient(); // Safe assignment

        setTitle((patientToEdit == null ? "Add New Patient" : "Edit Patient - ID: " + patient.getPatientId()));
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Fields
        nameField = new JTextField(patient.getName() != null ? patient.getName() : "");
        contactField = new JTextField(patient.getContact() != null ? patient.getContact() : "");
        addressField = new JTextField(patient.getAddress() != null ? patient.getAddress() : "");
        admittedDateField = new JTextField(patient.getAdmittedDate() != null ? patient.getAdmittedDate() : "");
        dischargedDateField = new JTextField(patient.getDischargedDate() != null ? patient.getDischargedDate() : "");

        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        sexComboBox.setSelectedItem(patient.getSex() != null ? patient.getSex() : "Male");

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Layout
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Sex:"));
        add(sexComboBox);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Contact:"));
        add(contactField);
        add(new JLabel("Admitted Date:"));
        add(admittedDateField);
        add(new JLabel("Discharged Date:"));
        add(dischargedDateField);
        add(saveButton);
        add(cancelButton);

        // Action Listeners
        saveButton.addActionListener(e -> savePatient(patientToEdit == null));
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    // Save logic: insert if new, update if editing
    private void savePatient(boolean isNew) {
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String address = addressField.getText().trim();
        String admittedDate = admittedDateField.getText().trim();
        String dischargedDate = dischargedDateField.getText().trim();
        String sex = (String) sexComboBox.getSelectedItem();

        if (name.isEmpty() || contact.isEmpty() || address.isEmpty() || admittedDate.isEmpty() || dischargedDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        patient.setName(name);
        patient.setContact(contact);
        patient.setAddress(address);
        patient.setSex(sex);
        patient.setAdmittedDate(admittedDate);
        patient.setDischargedDate(dischargedDate);

        boolean success;
        PatientDAO dao = new PatientDAO();
        if (isNew) {
            success = dao.addPatient(patient);  // You must have this method defined in PatientDAO
        } else {
            success = dao.updatePatient(patient);
        }

        if (success) {
            JOptionPane.showMessageDialog(this, (isNew ? "\u2714 Patient added successfully!" : "\u2714 Patient updated successfully!"));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\u274C Failed to save patient.");
        }
    }

    // For admin-side "Add Patient" button
    public PatientForm() {
        this(null);
    }

    public static void main(String[] args) {
        new PatientForm();
    }
}
