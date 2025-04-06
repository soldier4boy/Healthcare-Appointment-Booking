package com.healthcare.ui;

import com.healthcare.ui.Notification;
import com.healthcare.util.NotificationDAO;

import javax.swing.*;
import java.awt.*;

public class SendNotificationForm extends JFrame {
    private JTextField patientIdField;
    private JTextArea messageArea;
    private JComboBox<String> typeCombo;
    private JButton sendButton;

    public SendNotificationForm() {
        setTitle("Send Notification");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        patientIdField = new JTextField();
        messageArea = new JTextArea(5, 20);
        typeCombo = new JComboBox<>(new String[]{"Email", "SMS"});
        sendButton = new JButton("Send Notification");

        add(new JLabel("Patient ID:"));
        add(patientIdField);
        add(new JLabel("Message:"));
        add(new JScrollPane(messageArea));
        add(new JLabel("Notification Type:"));
        add(typeCombo);
        add(sendButton);

        sendButton.addActionListener(e -> sendNotification());

        setVisible(true);
    }

    private void sendNotification() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            String message = messageArea.getText().trim();
            String type = typeCombo.getSelectedItem().toString();

            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty.");
                return;
            }

            Notification notification = new Notification();
            notification.setPatientId(patientId);
            notification.setMessage(message);
            notification.setNotificationType(type);
            notification.setStatus("Sent");

            boolean success = new NotificationDAO().addNotification(notification);

            if (success) {
                JOptionPane.showMessageDialog(this, "\u2714 Notification sent!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "\u274C Failed to send notification.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SendNotificationForm::new);
    }
}
