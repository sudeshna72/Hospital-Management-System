package com.coursework.model;

public class DoctorModel {
	
	private int doctorId;
    private String doctorName;
    private int doctorAge;
    private String doctorGender;
    private int doctorWeight;
    private String bloodGroup;
    private String contactNumber;
    private String doctorEmail;
    private String panNumber;
    private String specialty;
    private String department;
    private String workingType;
    private int workingHours;
    private String userName;
    private String password;
    

    // Default constructor
    public DoctorModel() {
    }

    // Constructor with name  fields
       
    public String getDoctorName() {
        return doctorName;
    }

    // Constructor with name  fields

 public DoctorModel(int doctorId, String doctorName, int doctorAge, String doctorGender, int doctorWeight, String bloodGroup,
			String contactNumber, String doctorEmail, String panNumber, String specialty,String department, String workingType,
			int workingHours, String userName, String password) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.doctorAge = doctorAge;
		this.doctorGender = doctorGender;
		this.doctorWeight = doctorWeight;
		this.bloodGroup = bloodGroup;
		this.contactNumber = contactNumber;
		this.doctorEmail = doctorEmail;
		this.panNumber = panNumber;
		this.specialty = specialty;
		this.department = department;
		this.workingType = workingType;
		this.workingHours = workingHours;
		this.userName = userName;
		this.password = password;
	}
//Getters and Setters

	public int getDoctorId() {
		return doctorId;
	}
	
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
	public int getDoctorAge() {
		return doctorAge;
	}
	
	public void setDoctorAge(int doctorAge) {
		this.doctorAge = doctorAge;
	}
	
	public String getDoctorGender() {
		return doctorGender;
	}
	
	public void setDoctorGender(String doctorGender) {
		this.doctorGender = doctorGender;
	}
	
	public int getDoctorWeight() {
		return doctorWeight;
	}
	
	public void setDoctorWeight(int doctorWeight) {
		this.doctorWeight = doctorWeight;
	}
	
	public String getBloodGroup() {
		return bloodGroup;
	}
	
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getDoctorEmail() {
		return doctorEmail;
	}
	
	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}
	
	public String getPanNumber() {
		return panNumber;
	}
	
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
	public String getSpecialty() {
		return specialty;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getWorkingType() {
		return workingType;
	}
	
	public void setWorkingType(String workingType) {
		this.workingType = workingType;
	}
	
	public int getWorkingHours() {
		return workingHours;
	}
	
	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	
}