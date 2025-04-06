package com.healthcare.ui;

import com.healthcare.util.Doctor;
import com.healthcare.util.DoctorDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorList extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadBtn;

    public DoctorList() {
        setTitle("Doctor List");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Table setup
        String[] columns = {"Doctor ID", "Name", "Specialization", "Contact"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load Button
        loadBtn = new JButton("Load Doctors");
        loadBtn.addActionListener(e -> loadDoctors());

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loadBtn);

        // Add to frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Auto-load doctors on open
        loadDoctors();

        setVisible(true);
    }

    private void loadDoctors() {
        tableModel.setRowCount(0); // Clear previous data
        DoctorDAO dao = new DoctorDAO();
        List<Doctor> list = dao.getAllDoctors();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No doctors found.");
            return;
        }

        for (Doctor d : list) {
            Object[] row = {
                d.getDoctorId(),
                d.getName(),
                d.getSpecialization(),
                d.getContact()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoctorList::new);
    }
}
