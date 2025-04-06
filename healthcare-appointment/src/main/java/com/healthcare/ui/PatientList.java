package com.healthcare.ui;

import com.healthcare.util.Patient;
import com.healthcare.util.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientList extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public PatientList() {
        setTitle("Patient List");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Patient ID", "Full Name", "Sex", "Address", "Contact", "Admitted", "Discharged"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton loadBtn = new JButton("Load Patients");
        loadBtn.addActionListener(e -> loadPatients());

        add(scrollPane, BorderLayout.CENTER);
        add(loadBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadPatients() {
        PatientDAO dao = new PatientDAO();
        List<Patient> list = dao.getAllPatients();
        tableModel.setRowCount(0);

        for (Patient p : list) {
            Object[] row = {
                p.getPatientId(),
                p.getName(),
                p.getSex(),
                p.getAddress(),
                p.getContact(),
                p.getAdmittedDate(),
                p.getDischargedDate()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PatientList::new);
    }
}
