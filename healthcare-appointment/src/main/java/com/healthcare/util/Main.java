package com.healthcare.util;

public class Main {
    public static void main(String[] args) throws Exception {

        // Initializing the patient with the constructor
    	Patient testPatient = new Patient(
    		    1,
    		    "Ravi Kumar",
    		    "0000000000",
    		    "Hyderabad",
    		    "ravi.kumar",          // username
    		    "securePass123",       // password
    		    "Male",
    		    "2024-01-01",          // admit date
    		    "2024-01-02"           // discharge date
    		);


        // Assuming this method registers the patient in the system
        boolean success = registerPatient(testPatient);

        if (success) {
            System.out.println("Patient registered successfully!");
        } else {
            System.out.println("Patient registration failed.");
        }
    }

    // Method to register patient
    public static boolean registerPatient(Patient patient) {
    
        return true;
    }
}
