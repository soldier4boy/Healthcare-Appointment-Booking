# Healthcare-Appointment-Booking
A desktop application for scheduling and managing healthcare appointments.


A full-featured Java Swing-based desktop application for managing healthcare appointments efficiently. This project supports role-based access for Admins, Doctors, and Patients with secure login, interactive dashboards, CRUD operations, and real-time data validation.

🔧 Technologies Used

Programming Language: Java

GUI Framework: Java Swing

Database: MySQL

Connectivity: JDBC (Java Database Connectivity)

IDE: Eclipse IDE for Java Developers

Version Control: Git & GitHub

📂 Project Structure

src/
 ├── com.healthcare.ui              # UI Components (Swing Forms)
 ├── com.healthcare.util            # Helper Classes and DAOs
 ├── DBConnection.java              # Manages MySQL DB connections
 ├── Main.java                      # Entry point (if needed)
resources/
 └── (e.g., SQL scripts, theme files)

🚀 Getting Started

1. Clone the Repository

git clone https://github.com/soldier4boy/Healthcare-Appointment-Booking.git

2. Setup MySQL Database

Open MySQL Workbench or CLI.

Create a database named:

CREATE DATABASE healthcare_appointment;

Import the table schema using provided scripts:

USE healthcare_appointment;
-- Run SQL scripts for `patient`, `doctor`, `appointment`, `notification`, `admin` tables.

3. Configure Database Connection

Edit DBConnection.java:

private static final String URL = "jdbc:mysql://localhost:3306/healthcare_appointment";
private static final String USER = "root";
private static final String PASSWORD = "*******";

4. Build and Run the Project

Open the project in Eclipse IDE

Right-click on LoginForm.java

Choose Run As > Java Application

👨‍💼 User Roles and Dashboard Features

1. Admin

Login Credentials:

Username: admin
Password: admin123

Dashboard Functionalities:

Manage Appointments

Add/Edit/Delete Patients and Doctors

Export Appointment Reports (CSV)

Role-based Navigation

2. Doctor

View Appointments Assigned

Update Appointment Status

3. Patient

Self Registration

Login & Dashboard Access

Book Appointments (by doctor specialization)

View/Cancel Appointments

View Notifications

📦 UI Components

Forms in com.healthcare.ui:

LoginForm.java, PatientLoginForm.java, PatientSelfRegistrationForm.java

MainDashboard.java, DoctorDashboard.java, PatientDashboard.java

BookAppointmentForm.java, MyAppointmentsForm.java, EditAppointmentForm.java

DoctorForm.java, DoctorList.java

PatientForm.java, PatientList.java, EditPatientForm.java

NotificationForm.java, MyNotificationsForm.java, SendNotificationForm.java

Utilities in com.healthcare.util:

DBConnection.java, ThemeUtil.java, Validator.java, UIEffects.java

PatientDAO, DoctorDAO, AppointmentDAO, NotificationDAO

📁 Reports and Export

To export all appointments to a .csv file:

boolean success = ReportExporter.exportAppointmentsToCSV("appointments.csv");

Output will include:

ID,Patient ID,Doctor ID,Appointment Date,Status

✅ Best Practices Followed

Modular Java Swing Design

Real-time Form Validation

Role-Based Access Control

Consistent Color Theme & UI Effects

Secure Password Handling (JPasswordField)

Git-based Version Control with Author History Rewrite

📌 Final Notes

Java 8+ is recommended for compatibility.

All required .java files are under src/.

Ensure MySQL server is running before launching the app.

To contribute or fork this project, raise a pull request.

🔗 License

This project is for academic purposes and released under the MIT License.

