package com.coursework.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursework.model.DoctorModel;
import com.coursework.config.DbConfig;

/**
 * Service class for interacting with the database to retrieving data related to patient
 * dashboard. This class handles database connections and performs queries to fetch
 * the doctor and patient information.
 */

public class PatientDashboardService {
	
    private Connection dbConn;
    private boolean isConnectionError = false;
    
    /**
	 * Constructor that initializes the database connection. Sets the connection
	 * error flag if the connection fails.
	 */
    public PatientDashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    public List<DoctorModel> getAllDoctorsInfo() {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return new ArrayList<>();
        }
     // SQL query to fetch doctor details
        String query = "SELECT doctorName, specialty FROM Doctor";
        List<DoctorModel> doctorList = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                DoctorModel doctor = new DoctorModel();
                doctor.setDoctorName(result.getString("doctorName"));
                doctor.setSpecialty(result.getString("specialty"));
                doctorList.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorList;
    }
}
