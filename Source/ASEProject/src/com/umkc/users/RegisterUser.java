package com.umkc.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.umkc.dao.RegisterDAO;
import com.umkc.pojo.RegisterPojo;

public class RegisterUser extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4248292147431998064L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//Creating the object to POJO Class 
		RegisterPojo registerPoJO = new RegisterPojo();
		//Receiving the parameters from the request to process
		registerPoJO.setUserName(req.getParameter("username"));
		registerPoJO.setFirstName(req.getParameter("firstName"));
		registerPoJO.setLastName(req.getParameter("lastName"));
		registerPoJO.setPassword(req.getParameter("password"));
		registerPoJO.setConfirmPassword(req.getParameter("confirmPassword"));
		registerPoJO.setPhoneNumber(Long.parseLong(req.getParameter("phoneNumber")));
		registerPoJO.setEmailID(req.getParameter("emailId"));
		registerPoJO.setUserType(req.getParameter("userType"));

		
		//Creating the Basic DB object Mongo Labs
		
		BasicDBObject basicDBObject = new BasicDBObject();
	
			basicDBObject.put("username", registerPoJO.getUserName());
			basicDBObject.put("First Name", registerPoJO.getFirstName());
			basicDBObject.put("Last Name", registerPoJO.getLastName());
			basicDBObject.put("Password", registerPoJO.getPassword());
			basicDBObject.put("Phone Number", registerPoJO.getPhoneNumber());
			basicDBObject.put("Email", registerPoJO.getEmailID());
			basicDBObject.put("usertype", registerPoJO.getUserType());
		
		//Creating DAO Object to send and passsing JSON Object to Mongo Labs
		RegisterDAO registerDAO = new RegisterDAO();
	
		//Calling the method sendDataToMongoLabs by passing prepared JSON to MongoLabs
		boolean success=registerDAO.sendDataToMongoDB(basicDBObject);
		
		if (success){
			//Navingating the page to login.jsp if success
			req.setAttribute("status", "Registration successful");
		req.getRequestDispatcher("Login.jsp").forward(req, resp);	
		}
		else{
			//Navigating the page to Registration.jsp if failed
			req.setAttribute("status", "Registration Failed");
			req.getRequestDispatcher("Register.jsp").forward(req, resp);
		}
		
	}

}
