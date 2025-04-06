package com.healthcare.ui;

import javax.swing.*;
import java.awt.*;

public class MainDoctor extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainDoctor() {
        setTitle("Healthcare Appointment Booking - Doctor Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton addDoctorBtn = new JButton("Add Doctor");
        JButton viewDoctorsBtn = new JButton("View Doctors");

        addDoctorBtn.addActionListener(e -> new DoctorForm());
        viewDoctorsBtn.addActionListener(e -> new DoctorList());

        add(addDoctorBtn);
        add(viewDoctorsBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainDoctor();
    }
}
