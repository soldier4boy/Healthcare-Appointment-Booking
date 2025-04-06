package com.healthcare.ui;

import com.healthcare.util.Appointment;
import com.healthcare.util.AppointmentDAO;
import com.healthcare.util.Doctor;
import com.healthcare.util.DoctorDAO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class EditAppointmentForm extends JFrame {
    private JTextField dateTimeField;
    private JComboBox<String> doctorComboBox;
    private JComboBox<String> statusComboBox;
    private JButton updateBtn, cancelBtn;
    private int appointmentId;

    public EditAppointmentForm(int appointmentId) {
        this.appointmentId = appointmentId;

        setTitle("Edit Appointment");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Load appointment details
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

        // Load all doctors
        DoctorDAO doctorDAO = new DoctorDAO();
        List<Doctor> doctors = doctorDAO.getAllDoctors();

        // Doctor ComboBox
        doctorComboBox = new JComboBox<>();
        for (Doctor d : doctors) {
            doctorComboBox.addItem(d.getName() + " (" + d.getSpecialization() + ")");
            if (d.getDoctorId() == appointment.getDoctorId()) {
                doctorComboBox.setSelectedItem(d.getName() + " (" + d.getSpecialization() + ")");
            }
        }

        // Date & Status
        dateTimeField = new JTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(appointment.getAppointmentDate()));
        statusComboBox = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        statusComboBox.setSelectedItem(appointment.getStatus());

        // Buttons
        updateBtn = new JButton("Update Appointment");
        cancelBtn = new JButton("Cancel");

        // Layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Date & Time:"));
        panel.add(dateTimeField);
        panel.add(new JLabel("Doctor:"));
        panel.add(doctorComboBox);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(updateBtn);
        panel.add(cancelBtn);
        add(panel, BorderLayout.CENTER);

        // Action listeners
        updateBtn.addActionListener(e -> updateAppointment());
        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void updateAppointment() {
        String dateTime = dateTimeField.getText();
        String selectedDoctorStr = (String) doctorComboBox.getSelectedItem();
        String doctorName = selectedDoctorStr.split(" \\(")[0].trim();

        DoctorDAO doctorDAO = new DoctorDAO();
        Doctor selectedDoctor = doctorDAO.getDoctorByName(doctorName);
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(this, "Doctor not found.");
            return;
        }

        int doctorId = selectedDoctor.getDoctorId();
        String status = (String) statusComboBox.getSelectedItem();

        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.updateAppointment(appointmentId, dateTime, doctorId, status);
        if (success) {
            JOptionPane.showMessageDialog(this, "\u2714 Appointment updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\u274C Failed to update appointment.");
        }
    }

    public static void main(String[] args) {
        new EditAppointmentForm(1);
    }
}
