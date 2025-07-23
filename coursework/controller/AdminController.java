package com.coursework.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.coursework.service.DoctorService;
import com.coursework.service.PatientDashboardService;
import com.coursework.service.AdminService;

/**
 * Servlet implementation class AdminController
 * @author sochinagurung
 */
@WebServlet (asyncSupported = true, urlPatterns = {"/admin"} )
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdminService adminService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public AdminController() {
    	this.adminService = new AdminService();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setAttribute("doctorList", adminService.getAllDoctorsInfo());
		request.setAttribute("patientList", adminService.getAllPatientInfo());
		request.getRequestDispatcher("/WEB-INF/pages/admin_portal.jsp").forward(request,response);
		
		AdminService adminService = new AdminService();

		int doctorCount = adminService.getDoctorCount();
		int patientCount = adminService.getPatientCount();

		request.setAttribute("doctorCount", doctorCount);
		request.setAttribute("patientCount", patientCount);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
