package com.coursework.controller;

import com.coursework.model.RegisterPatientModel;
import com.coursework.service.RegisterService;
import com.coursework.Util.PasswordUtil;
import com.coursework.Util.ValidationUtil;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * registerpatientController handles patient registration requests and form submissions.
 *  @author sochinagurung
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/register"})
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,   // 2MB
    maxFileSize = 10 * 1024 * 1024,        // 10MB
    maxRequestSize = 50 * 1024 * 1024      // 50MB
)
public class registerpatientController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService svc = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register_patient.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Validate form input
            String err = validateRegistrationForm(req);
            if (err != null) {
                handleError(req, resp, err);
                return;
            }

            // Extract patient model from request
            RegisterPatientModel patient = extractPatientModel(req);

            // Encrypt password before saving
            String encryptedPassword = PasswordUtil.encrypt(patient.getUserName(), patient.getPassword());
            if (encryptedPassword == null) {
                handleError(req, resp, "Password encryption failed. Please try again.");
                return;
            }
            patient.setPassword(encryptedPassword);

            // Add patient using service
            boolean ok = svc.addPatient(patient);

            if (ok) {
                handleSuccess(req, resp, "Patient has been successfully registered!");
            } else {
                handleError(req, resp, "Could not register patient. Check logs and retry.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred. Please try again later.");
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        if (ValidationUtil.isNullOrEmpty(req.getParameter("patientName")))
            return "Patient name is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("patientAge")))
            return "Patient age must be numeric.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("userName")))
            return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("password")))
            return "Password is required.";

        try {
            Integer.parseInt(req.getParameter("patientAge"));
            Integer.parseInt(req.getParameter("patientWeight"));
            Integer.parseInt(req.getParameter("insuranceId"));
        } catch (NumberFormatException ex) {
            return "Age, Weight, and Insurance ID must be numeric.";
        }
        return null;
    }

    private RegisterPatientModel extractPatientModel(HttpServletRequest req) {
        return new RegisterPatientModel(
        	Integer.parseInt(req.getParameter("patientId")),
            req.getParameter("patientName"),
            Integer.parseInt(req.getParameter("patientAge")),
            req.getParameter("patientSex"),
            Integer.parseInt(req.getParameter("patientWeight")),
            req.getParameter("bloodGroup"),
            req.getParameter("contactNumber"),
            req.getParameter("emailId"),
            req.getParameter("patientAddress"),
            req.getParameter("allergies"),
            Integer.parseInt(req.getParameter("insuranceId")),
            req.getParameter("attendantName"),
            req.getParameter("attendantContact"),
            req.getParameter("registrarName"),
            req.getParameter("userName"),
            req.getParameter("password")
        );
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher("/WEB-INF/pages/register_patient.jsp").forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);

        // Re-populate all form fields
        for (String p : new String[]{
            "petientId","patientName", "patientAge", "patientSex", "patientWeight",
            "bloodGroup", "contactNumber", "emailId", "patientAddress",
            "allergies", "insuranceId", "attendantName", "attendantContact",
            "registrarName", "userName", "password"
        }) {
            req.setAttribute(p, req.getParameter(p));
        }

        req.getRequestDispatcher("/WEB-INF/pages/register_patient.jsp").forward(req, resp);
    }
}
