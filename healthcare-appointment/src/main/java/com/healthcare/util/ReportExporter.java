package com.healthcare.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReportExporter {

    public static boolean exportAppointmentsToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,Patient ID,Doctor ID,Appointment Date,Status");

            AppointmentDAO dao = new AppointmentDAO();
            List<Appointment> appointments = dao.getAllAppointments();  // Fetch all appointments

            for (Appointment a : appointments) {
                writer.printf("%d,%d,%d,%s,%s%n",
                        a.getAppointmentId(),
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getAppointmentDate().toString(), 
                        a.getStatus());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
