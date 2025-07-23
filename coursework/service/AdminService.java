package com.coursework.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.coursework.config.DbConfig;
import com.coursework.model.RegisterPatientModel;
import com.coursework.model.DoctorModel;

/**
 * Service class for interacting with the database to retrieving data related to admin
 * dashboard. This class handles database connections and performs queries to fetch
 * the doctor and patient information.
 */
public class AdminService {
	
	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor that initializes the database connection. Sets the connection
	 * error flag if the connection fails.
	 */
	public AdminService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			// Log and handle exceptions related to database connection
			ex.printStackTrace();
			isConnectionError = true;
		}
	}
	/**
	 * Retrieves all dotcor and patient information from the database.
	 * 
	 * It returns s list of RegisterPatientModel objects containing patient data. Returns null
	 *         if there is a connection error or if an exception occurs during query
	 *         execution.
	 */
	public List<DoctorModel> getAllDoctorsInfo() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}
		// SQL query to fetch doctor details
		String query = "SELECT doctorId, doctorName, Specialty, department FROM doctor";
		List<DoctorModel> doctorList = new ArrayList<>();
				
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
		ResultSet result = stmt.executeQuery();
			while (result.next()) {
				DoctorModel doctor = new DoctorModel();
				doctor.setDoctorId(result.getInt("doctorId"));
				doctor.setDoctorName(result.getString("doctorName"));
				doctor.setSpecialty(result.getString("specialty"));
	            doctor.setDepartment(result.getString("department"));
	            doctorList.add(doctor);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return doctorList;
	}
	public List<RegisterPatientModel> getAllPatientInfo() {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}
		// SQL query to fetch patient details
		String query = "SELECT patientId, patientName, bloodGroup, contactNumber FROM patient";
		List<RegisterPatientModel> patientList = new ArrayList<>();
				
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
		ResultSet result = stmt.executeQuery();
			while (result.next()) {
				RegisterPatientModel patient = new RegisterPatientModel();
				patient.setPatientId(result.getInt("patientId"));
				patient.setPatientName(result.getString("patientName"));
				patient.setBloodGroup(result.getString("bloodGroup"));
				patient.setContactNumber(result.getString("contactNumber"));
	            patientList.add(patient);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return patientList;
	}
	public int getDoctorCount() {
		if (isConnectionError) return 0;
		String query = "SELECT COUNT(*) AS total FROM doctor";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getPatientCount() {
		if (isConnectionError) return 0;
		String query = "SELECT COUNT(*) AS total FROM patient";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

						
}
					
					

					


