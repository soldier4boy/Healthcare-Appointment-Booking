package com.healthcare.ui;

import com.healthcare.ui.Notification;
import com.healthcare.util.NotificationDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyNotificationsForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private int patientId;

    public MyNotificationsForm(int patientId) {
        this.patientId = patientId;

        setTitle("My Notifications");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Message", "Type", "Status", "Time"}, 0);
        table = new JTable(model);

        loadNotifications();

        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadNotifications() {
        List<Notification> list = new NotificationDAO().getNotificationsForPatient(patientId);
        for (Notification n : list) {
            model.addRow(new Object[]{
                n.getMessage(),
                n.getNotificationType(),
                n.getStatus(),
                n.getTimestamp()
            });
        }
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new MyNotificationsForm(1));
    }
}
