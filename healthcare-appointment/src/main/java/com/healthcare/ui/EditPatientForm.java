package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;

import javax.swing.*;
import java.awt.*;

public class EditPatientForm extends JFrame {
    private JTextField nameField, contactField, addressField, admittedDateField, dischargedDateField;
    private JComboBox<String> sexComboBox;
    private JButton saveButton, cancelButton;
    private Patient patient;

    public EditPatientForm(Patient patientToEdit) {
        this.patient = patientToEdit;

        setTitle("Edit Patient - ID: " + patient.getPatientId());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Fields
        nameField = new JTextField(patient.getName());
        contactField = new JTextField(patient.getContact());
        addressField = new JTextField(patient.getAddress());
        admittedDateField = new JTextField(patient.getAdmittedDate());
        dischargedDateField = new JTextField(patient.getDischargedDate());

        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        sexComboBox.setSelectedItem(patient.getSex());

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
        saveButton.addActionListener(e -> savePatient());
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void savePatient() {
        String name = nameField.getText().trim();
        String gender = (String) sexComboBox.getSelectedItem();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String admitted = admittedDateField.getText().trim();
        String discharged = dischargedDateField.getText().trim();

        if (name.isEmpty() || gender.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        // Update patient object
        patient.setName(name);
        patient.setSex(gender);
        patient.setAddress(address);
        patient.setContact(contact);
        patient.setAdmittedDate(admitted);
        patient.setDischargedDate(discharged);

        boolean updated = new PatientDAO().updatePatient(patient);
        if (updated) {
            JOptionPane.showMessageDialog(this, "\u2714 Patient updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\u274C Failed to update patient.");
        }
    }
}
