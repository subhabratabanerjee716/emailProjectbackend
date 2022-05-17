package com.email.emailapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.emailapi.constants.ControllerConstants;
import com.email.emailapi.entity.Email;
import com.email.emailapi.entity.Users;
import com.email.emailapi.service.EmailService;

@RestController
@CrossOrigin
public class MyController {
   @Autowired
	private EmailService  emailservice;
	
	@RequestMapping("/Welcome")
	public String welcome() {
		
		return "Email Api";
		
	}
	@RequestMapping(value="/sendemail",method=RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody Email req){
		
		System.out.println(req);
	boolean res=	this.emailservice.sendEmail(req.getUser_email(), req.getPass(), req.getRecipient_email(), req.getSubject(), req.getMessage());
		
		
		if(res) {
			
			return ResponseEntity.ok("Email is sent successfully !!");
			
		}else {
			
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent..");
			
		}
		
		
	}
	
	
		@RequestMapping(value=ControllerConstants.POST_INSERT_VALUE, method = RequestMethod.POST,
		      consumes=MediaType.APPLICATION_JSON_VALUE)
		  @ResponseBody
		  public String insertUsers(@RequestBody Users user)
		  {
		    boolean insertstatus=false;
		    boolean checkEmailPresent=false;
		    
		    /*
		     * If user enters no value to User name, password or email
		     */
		    if(user.getUsername()==null||user.getPassword()==null||user.getUser_email()==null)
		      
		      throw new IllegalArgumentException(ControllerConstants.INSERT_VALID_CREDENTIALS);
		    
		    //checks if email already exists in database 
		    checkEmailPresent = emailservice.checkEmailId(user);

		    if(checkEmailPresent)
		      
		      return ControllerConstants.DATA_EMAIL_PRESENT;
		    //Insertion of data
		    else
		    {
		      
		      insertstatus=emailservice.insertUsers(user);
		            
		    if(insertstatus==true)
		      
		      return ControllerConstants.DATA_INSERTION_SUCCESS;
		    
		    else
		      return ControllerConstants.DATA_INSERTION_FAILURE;
		    }
		  }
		
		@RequestMapping(value=ControllerConstants.POST_LOGIN_VALUE, method = RequestMethod.POST, 
			      consumes=MediaType.APPLICATION_JSON_VALUE)
			  @ResponseBody
			  public String checkUser(@RequestBody Users user)
			  {  
			    
			    String check= emailservice.checkUser(user);
			    
			    if(check.equals(user.getUser_email()))
			      return check;
			    else
			      return ControllerConstants.LOGIN_FAILED;
			    
			  }
	
		@RequestMapping(value=ControllerConstants.GET_EMAIL_DETAILS, method = RequestMethod.GET)
		  public List<Email> getEmailDetails() {
		    
		    List<Email> emailList= emailservice.getEmailDetails();
		    
		    return emailList;
		  }
		
		@RequestMapping(value=ControllerConstants.GET_DETAILS_BY_EMAIL, method=RequestMethod.GET)
		  public List<Email> getUserByEmail(@RequestParam(ControllerConstants.CONSTANT_EMAIL) String email)
		  {
		    if(email==null) {
		      throw new IllegalArgumentException(ControllerConstants.CANNOT_BE_NULL);
		    }
		    
		    else
		      return emailservice.getDetailsByEmail(email);
		  }
		@RequestMapping(value=ControllerConstants.POST_UPDATE_STATUS, method=RequestMethod.POST,
			      consumes=MediaType.ALL_VALUE)
			  public void updateStatusOFFLINE(@RequestParam (ControllerConstants.CONSTANT_EMAIL) String email) {
			    
			     emailservice.updateStatusOffline(email);

			    
			  }
}
