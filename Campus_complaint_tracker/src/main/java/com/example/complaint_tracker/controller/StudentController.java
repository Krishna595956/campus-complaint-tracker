package com.example.complaint_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.complaint_tracker.DTO.AdminLogin;
import com.example.complaint_tracker.DTO.Login;
import com.example.complaint_tracker.model.ComplaintDetails;
import com.example.complaint_tracker.model.StaffDetails;
import com.example.complaint_tracker.model.StudentDetails;
import com.example.complaint_tracker.repository.ComplaintsRepo;
import com.example.complaint_tracker.repository.StaffDetailsRepo;
import com.example.complaint_tracker.repository.StudentDetailsRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	@Autowired
	StudentDetailsRepo studentRepo;

	@Autowired
	StaffDetailsRepo staffRepo;

	@Autowired
	ComplaintsRepo complaintsRepo;

	@GetMapping("/")
	public String dashboard() {
		// This method handles the root URL request and returns the "dashboard" view.
		// It's likely used to display the main dashboard or home page of the
		// application.
		return "dashboard";
	}

	@GetMapping("/usertype")
	public String usertype() {
		// This method handles the "/usertype" URL request and returns the
		// "userConfirmation" view.
		// It's probably used to present a page where the user can select their user
		// type (student, staff, admin).
		return "userConfirmation";
	}

	@GetMapping("/studentlogin")
	public String student() {
		// This method handles the "/studentlogin" URL request and returns the
		// "studentLogin" view.
		// It's likely used to display the login page for students.
		return "studentLogin";
	}

	@GetMapping("/stafflogin")
	public String staff() {
		// This method handles the "/stafflogin" URL request and returns the
		// "staffLogin" view.
		// It's probably used to display the login page for staff members.
		return "staffLogin";
	}

	@GetMapping("/filecomplaint")
	public String filecomplaint() {
		return "complaintForm";
	}

	@GetMapping("/adminlogin")
	public String adminlogin() {
		// This method handles the "/adminLogin" URL request and returns the
		// "adminLogin" view.
		// It's likely used to display the login page for administrators.
		return "adminLogin";
	}

	@GetMapping("/studentregister")
	public String studentRegister() {
		// This method handles the "/studentregister" URL request and returns the
		// "studentRegister" view.
		// It's probably used to display the registration page for new students.
		return "studentRegister";
	}

	@GetMapping("/staffregister")
	public String staffregister() {
		return "staffregister";
	}

	@PostMapping("/studentregistration")
	public String studentregister(StudentDetails student, Model model) {
		// BCryptPasswordEncoder for password encryption
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (studentRepo.findByEmail(student.getEmail()) != null) {
			model.addAttribute("status", "Email already exists!");
			return "studentRegister"; // Replace with your error handling logic
		}

		// Get the plain-text password from the student object
		String plainTextPassword = student.getPassword();

		// Encode the password using BCrypt
		String encodedPassword = passwordEncoder.encode(plainTextPassword);

		System.out.println(encodedPassword);

		// Set the encoded password back into the student object
		student.setPassword(encodedPassword);
		student.setUsertype("student");

		// Save the student to the database (assuming studentRepo is a repository)
		studentRepo.save(student);

		// Redirect to the login page (replace "studentlogin" with the actual URL)
		return "redirect:studentlogin";
	}

	@PostMapping("/staffregistration")
	public String staffregistration(StaffDetails staff, Model model) {
		// BCryptPasswordEncoder for password encryption
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (staffRepo.findByEmail(staff.getEmail()) != null) {
			model.addAttribute("status", "Email already exists!");
			return "staffRegister"; // Replace with your error handling logic
		}

		// Get the plain-text password from the staff object
		String plainTextPassword = staff.getPassword();

		// Encode the password using BCrypt
		String encodedPassword = passwordEncoder.encode(plainTextPassword);

		// Print the encoded password for debugging purposes (optional)
		System.out.println(encodedPassword);

		// Set the encoded password back into the staff object
		staff.setPassword(encodedPassword);
		staff.setUsertype("staff");

		// Save the staff member details to the database (assuming staffRepo is a
		// repository)
		staffRepo.save(staff);

		// Redirect to the staff login page (replace "stafflogin" with the actual URL)
		return "redirect:stafflogin";
	}

	@PostMapping("/loginstudent")
	public String loginStudent(Login studentDetails, Model model, HttpSession session) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// Get the roll number and password from the request body
		String rollNo = studentDetails.getId();
		System.out.println(rollNo);
		String password = studentDetails.getPassword();

		// Find the student by roll number
		StudentDetails existingStudent = studentRepo.findByRollNo(rollNo);

		// Check if the student exists
		if (existingStudent == null) {
			// Student does not exist, return an error message or redirect to an error page
			model.addAttribute("status", "User does not exist!");
			return "studentlogin";
		}

		// Check if the password matches
		if (!passwordEncoder.matches(password, existingStudent.getPassword())) {
			// Password does not match, return an error message or redirect to an error page
			model.addAttribute("status", "Incorrect password");
			return "studentlogin";
		}

		// Login successful, redirect to the student dashboard or a protected area
		session.setAttribute("studentRollNo", existingStudent.getRollNo());
		return "studentdashboard";
	}

	@PostMapping("/loginstaff")
	public String loginstaff(Login studentDetails, Model model, HttpSession session) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// Get the roll number and password from the request body
		String id = studentDetails.getId();
		System.out.println(id);
		String password = studentDetails.getPassword();

		// Find the student by roll number
		StaffDetails existingStudent = staffRepo.findByStaffid(id);

		// Check if the student exists
		if (existingStudent == null) {
			// Student does not exist, return an error message or redirect to an error page
			model.addAttribute("status", "User does not exist!");
			return "studentlogin";
		}

		// Check if the password matches
		if (!passwordEncoder.matches(password, existingStudent.getPassword())) {
			// Password does not match, return an error message or redirect to an error page
			model.addAttribute("status", "Incorrect password");
			return "stafflogin";
		}

		// Login successful, redirect to the student dashboard or a protected area
		session.setAttribute("staffId", existingStudent.getId());
		return "staffdashboard";
	}

	@PostMapping("/loginadmin")
	public String loginadmin(AdminLogin admin, Model model) {
		String username = admin.getUsername();
		String password = admin.getPassword();
		System.out.println(username);
		System.out.println(password);
		if (!(username == "admin" && password == "nimda")) {
			return "adminDashboard";
		}
		model.addAttribute("status", "Invalid credentials");
		return "adminLogin";

	}
	
	@PostMapping("/submitcomplaint")
	public String filecomplaint1(ComplaintDetails complaint,@RequestParam("evidence") MultipartFile evidenceFile) {
		System.out.print(evidenceFile);
		complaintsRepo.save(complaint);
		return "complaintForm";
	}

}
