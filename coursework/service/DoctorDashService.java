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

public class DoctorDashService {
	
	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor that initializes the database connection. Sets the connection
	 * error flag if the connection fails.
	 */
	public DoctorDashService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			// Log and handle exceptions related to database connection
			ex.printStackTrace();
			isConnectionError = true;
		}
	}
	
	public static int getDoctorCount() {
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
