package com.coursework.controller;

import com.coursework.model.DoctorModel;
import com.coursework.service.DoctorService;
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
 * DoctorController handles doctor registration requests and processes form submissions.
 *  @author sochinagurung
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/registerDoctor"})
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024, // 2MB
    maxFileSize = 10 * 1024 * 1024,      // 10MB
    maxRequestSize = 50 * 1024 * 1024    // 50MB
)
public class DoctorController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final DoctorService doctorService = new DoctorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register_doctor.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage);
                return;
            }

            DoctorModel doctorModel = extractDoctorModel(req);
         // Encrypt password before saving
            String encryptedPassword = PasswordUtil.encrypt(doctorModel.getUserName(), doctorModel.getPassword());
            if (encryptedPassword == null) {
                handleError(req, resp, "Password encryption failed. Please try again.");
                return;
            }
            doctorModel.setPassword(encryptedPassword);
            boolean isAdded = doctorService.addDoctor(doctorModel);
            
            

            if (isAdded) {
                handleSuccess(req, resp, "Doctor has been registered successfully!");
            } else {
                handleError(req, resp, "Could not register doctor. Check logs and retry.");
            }
        } catch (Exception e) {
            handleError(req, resp, "An unexpected error occurred. Please try again later.");
            e.printStackTrace();
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        if (ValidationUtil.isNullOrEmpty(req.getParameter("doctorName")))
            return "Doctor name is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("doctorAge")))
            return "Doctor age is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("doctorGender")))
            return "Doctor gender is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("doctorWeight")))
            return "Doctor weight is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("bloodGroup")))
            return "Doctor blood group is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("contactNumber")))
            return "Doctor contact number is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("doctorEmail")))
            return "Doctor email is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("panNumber")))
            return "PAN number is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("specialty")))
            return "Doctor specialty is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("department")))
            return "Doctor department is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("workingType")))
            return "Doctor working type is required.";
        if (ValidationUtil.isNullOrEmpty(req.getParameter("workingHours")))
            return "Working hours are required.";
        return null;
    }

    private DoctorModel extractDoctorModel(HttpServletRequest req) {
        return new DoctorModel(
        	Integer.parseInt(req.getParameter("doctorId")),	
            req.getParameter("doctorName"),
            Integer.parseInt(req.getParameter("doctorAge")),
            req.getParameter("doctorGender"),
            Integer.parseInt(req.getParameter("doctorWeight")),
            req.getParameter("bloodGroup"),
            req.getParameter("contactNumber"),
            req.getParameter("doctorEmail"),
            req.getParameter("panNumber"),
            req.getParameter("specialty"),
            req.getParameter("department"),
            req.getParameter("workingType"),
            Integer.parseInt(req.getParameter("workingHours")),
            req.getParameter("userName"),
            req.getParameter("password")

        );
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher("/WEB-INF/pages/register_doctor.jsp").forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        // Preserve form values
        for (String parameter : new String[]{
            "doctorId", "doctorName", "doctorAge", "doctorGender", "doctorWeight",
            "bloodGroup", "contactNumber", "doctorEmail", "panNumber",
            "specialty","department", "workingType", "workingHours", "userName", "password"
        }) {
            req.setAttribute(parameter, req.getParameter(parameter));
        }
        req.getRequestDispatcher("/WEB-INF/pages/register_doctor.jsp").forward(req, resp);
    }
}