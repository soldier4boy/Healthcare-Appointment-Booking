package com.healthcare.ui;

import com.healthcare.util.Appointment;
import com.healthcare.util.AppointmentDAO;
import com.healthcare.util.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyAppointmentsForm extends JFrame {
    private Patient patient;

    public MyAppointmentsForm(Patient patient) {
        this.patient = patient;
        initUI();
    }

    private void initUI() {
        setTitle("My Appointments - " + patient.getName());
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table columns
        String[] columnNames = {"Appointment ID", "Doctor", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        
     // Add Cancel button
        JButton cancelBtn = new JButton("Cancel Appointment");
        cancelBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int appointmentId = (int) table.getValueAt(selectedRow, 0); // Assuming appointment_id is in column 0
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    AppointmentDAO dao = new AppointmentDAO();
                    boolean cancelled = dao.cancelAppointment(appointmentId);

                    if (cancelled) {
                        JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!");
                        dispose(); // Close and reopen to refresh table
                        new MyAppointmentsForm(patient);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to cancel appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
            }
        });
        add(cancelBtn, BorderLayout.SOUTH);


        // Retrieve appointments
        AppointmentDAO dao = new AppointmentDAO();
        List<Appointment> appointments = dao.getAppointmentsByPatientId(patient.getPatientId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Appointment appointment : appointments) {
            Object[] row = {
                appointment.getAppointmentId(),
                appointment.getSpecialization() + " - " + appointment.getDoctorName(),
                sdf.format(appointment.getAppointmentDate()),
                appointment.getStatus()
            };

            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
