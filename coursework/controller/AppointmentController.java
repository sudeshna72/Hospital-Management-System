package com.coursework.controller;

import com.coursework.model.AppointmentModel;
import com.coursework.service.AppointmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

/**
 * Handles appointment booking form display and submission.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/appointment"})
public class AppointmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/book_appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Validate the form inputs
            String error = validateForm(req);
            if (error != null) {
                handleError(req, resp, error);
                return;
            }

            // Extract appointment data
            AppointmentModel appointment = extractAppointmentModel(req);

            // Save the appointment
            Boolean success = appointmentService.addAppointment(appointment);
            if (success != null && success) {
                handleSuccess(req, resp, "Appointment booked successfully!");
            } else {
                handleError(req, resp, "Failed to book appointment. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "Unexpected error occurred: " + e.getMessage());
        }
    }

    private String validateForm(HttpServletRequest req) {
        String date = req.getParameter("bookingDate");
        String time = req.getParameter("bookingTime");
        String issues = req.getParameter("issues");

        if (date == null || date.isEmpty()) return "Booking date is required.";
        if (time == null || time.isEmpty()) return "Booking time is required.";
        if (issues == null || issues.trim().isEmpty()) return "Please describe your issues.";

        // Optional: Validate date/time format if needed
        return null;
    }

    private AppointmentModel extractAppointmentModel(HttpServletRequest req) {
        Date bookingDate = Date.valueOf(req.getParameter("bookingDate"));  // yyyy-MM-dd
        Time bookingTime = Time.valueOf(req.getParameter("bookingTime"));  // HH:mm:ss
        String issues = req.getParameter("issues");

        AppointmentModel model = new AppointmentModel();
        model.setBookingDate(bookingDate);
        model.setBookingTime(bookingTime);
        model.setIssues(issues);
        return model;
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher("/WEB-INF/pages/book_appointment.jsp").forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);

        // Re-populate fields
        req.setAttribute("bookingDate", req.getParameter("bookingDate"));
        req.setAttribute("bookingTime", req.getParameter("bookingTime"));
        req.setAttribute("issues", req.getParameter("issues"));

        req.getRequestDispatcher("/WEB-INF/pages/book_appointment.jsp").forward(req, resp);
    }
}
