package com.healthcare.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.ui.Notification;

public class NotificationDAO {

    public boolean addNotification(Notification notification) {
        String sql = "INSERT INTO notification (patient_id, message, notification_type, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notification.getPatientId());
            stmt.setString(2, notification.getMessage());
            stmt.setString(3, notification.getNotificationType());
            stmt.setString(4, notification.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notification> getNotificationsForPatient(int patientId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE patient_id = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setPatientId(rs.getInt("patient_id"));
                n.setMessage(rs.getString("message"));
                n.setNotificationType(rs.getString("notification_type"));
                n.setStatus(rs.getString("status"));
                n.setTimestamp(rs.getTimestamp("timestamp"));
                list.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
