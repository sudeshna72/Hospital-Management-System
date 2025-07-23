package com.coursework.model;

import java.sql.Time;
import java.util.Date;


public class AppointmentModel {
	private Date bookingDate;
	private Time bookingTime;
	private String issues;
	
    // Default constructor
	public AppointmentModel() {
	}

	public AppointmentModel(Date bookingDate, Time bookingTime, String issues) {
		super();
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.issues = issues;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Time getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Time bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getIssues() {
		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;
	}


}
