package com.healthcare.ui;

import com.healthcare.util.Appointment;
import com.healthcare.util.AppointmentDAO;
import com.healthcare.util.Doctor;
import com.healthcare.util.DoctorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DoctorAppointmentsForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private Doctor doctor;

    public DoctorAppointmentsForm(Doctor doctor) {
        this.doctor = doctor;

        setTitle("Appointments for Dr. " + doctor.getName());
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table columns
        String[] columns = {"ID", "Date", "Patient", "Status", "Edit", "Cancel"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        loadAppointments();

        setVisible(true);
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        DoctorDAO dao = new DoctorDAO();
        List<Appointment> list = dao.getAppointmentsByDoctorId(doctor.getDoctorId());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Appointment a : list) {
            String dateFormatted = sdf.format(a.getAppointmentDate());

            JButton editBtn = new JButton("Edit");
            JButton cancelBtn = new JButton("Cancel");

            tableModel.addRow(new Object[]{
                a.getAppointmentId(),
                dateFormatted,
                a.getPatientName(),
                a.getStatus(),
                editBtn,
                cancelBtn
            });

            int row = tableModel.getRowCount() - 1;

            editBtn.addActionListener(e -> new EditAppointmentForm(a.getAppointmentId()));
            cancelBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Cancel appointment ID: " + a.getAppointmentId() + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                	AppointmentDAO appointmentDAO = new AppointmentDAO();
                	boolean success = appointmentDAO.updateAppointmentStatus(a.getAppointmentId(), "Cancelled");
                    JOptionPane.showMessageDialog(this, success ? "Appointment cancelled" : "Failed");
                    loadAppointments();
                }
            });
        }
    }
}
