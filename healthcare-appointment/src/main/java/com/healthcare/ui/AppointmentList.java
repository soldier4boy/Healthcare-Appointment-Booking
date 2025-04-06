package com.healthcare.ui;

import com.healthcare.util.Appointment;
import com.healthcare.util.AppointmentDAO;
import com.healthcare.util.DoctorDAO;
import com.healthcare.util.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AppointmentList extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JCheckBox showCancelledCheckBox;
    private JTextField searchField;

    public AppointmentList() {
        setTitle("Appointment List");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        topPanel.add(new JLabel("Search (Doctor/Patient): "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchBtn, BorderLayout.EAST);

        // Checkbox panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        showCancelledCheckBox = new JCheckBox("Show Cancelled");
        filterPanel.add(showCancelledCheckBox);

        // Table
        String[] columns = {"ID", "Date", "Doctor", "Patient", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton editBtn = new JButton("Edit");
        JButton cancelBtn = new JButton("Cancel");
        JButton completeBtn = new JButton("Mark Completed");
        buttonPanel.add(editBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(completeBtn);

        // Add components
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        loadAppointments(null);

        searchBtn.addActionListener(e -> loadAppointments(searchField.getText().trim()));
        showCancelledCheckBox.addActionListener(e -> loadAppointments(searchField.getText().trim()));

        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int appointmentId = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Cancel appointment?");
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = new AppointmentDAO().cancelAppointment(appointmentId);
                    JOptionPane.showMessageDialog(this, success ? "Cancelled" : "Failed");
                    loadAppointments(searchField.getText().trim());
                }
            }
        });

        completeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int appointmentId = (int) tableModel.getValueAt(row, 0);
                boolean success = new AppointmentDAO().updateAppointmentStatus(appointmentId, "Completed");
                JOptionPane.showMessageDialog(this, success ? "Marked Completed" : "Failed");
                loadAppointments(searchField.getText().trim());
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int appointmentId = (int) tableModel.getValueAt(row, 0);
                new EditAppointmentForm(appointmentId);
            }
        });

        setVisible(true);
    }

    private void loadAppointments(String searchQuery) {
        tableModel.setRowCount(0);
        List<Appointment> list = new AppointmentDAO().getAllAppointments();
        boolean showCancelled = showCancelledCheckBox.isSelected();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Appointment a : list) {
            if (!showCancelled && "Cancelled".equalsIgnoreCase(a.getStatus())) continue;

            String doctorName = new DoctorDAO().getDoctorNameById(a.getDoctorId());
            String patientName = new PatientDAO().getPatientNameById(a.getPatientId());

            if (searchQuery != null && !searchQuery.isEmpty()) {
                String combined = (doctorName + patientName).toLowerCase();
                if (!combined.contains(searchQuery.toLowerCase())) continue;
            }

            Object[] row = {
                a.getAppointmentId(),
                sdf.format(a.getAppointmentDate()),
                doctorName,
                patientName,
                a.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppointmentList::new);
    }
}
