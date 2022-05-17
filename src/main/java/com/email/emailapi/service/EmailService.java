package com.email.emailapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.email.emailapi.dao.EmailDao;
import com.email.emailapi.entity.Email;
import com.email.emailapi.entity.Users;

@Service
public class EmailService {
	@Autowired
	EmailDao edao;
	public boolean sendEmail(String from,String pass,String to,String subject,String message) {
		
		boolean f=false;
		
		//Variable for gmail
		String host="smtp.gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		
		//setting important information to properties object
		
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		//Step 1: to get the session object..
		Session session=Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication(from,pass);
			}
			
			
			
		});
		
		session.setDebug(true);
		
		//Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);
		
		try {
		
		//from email
		m.setFrom(from);
		
		//adding recipient to message
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//adding subject to message
		m.setSubject(subject);
	
		
		//adding text to message
		m.setText(message);
		
		//send 
		
		//Step 3 : send the message using Transport class
		Transport.send(m);
		
		System.out.println("Sent success...................");
		
		f=true;
		if(f) {
			
			edao.insertEmail(from, to, message, subject);
			
			System.out.println("Records updated successfully");
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	public boolean insertUsers(Users user) {
	    
	    return edao.insertUsers(user.getUsername(),user.getUser_email(), user.getPassword());
	  }
	
	
	
	public List <Users> getUserDetails(){
	    
	    List<Users> userList= edao.getUserDetails();
	    
	    return userList;
	  }
	
	public List <Email> getEmailDetails(){
	    
	    List<Email> emailList= edao.getEmailDetails();
	    
	    return emailList;
	  }
	
	public  boolean checkEmailId(Users user)
	  {
	    boolean flag=false;
	    
	    /*
	     * Displaying email to check fetch from database success
	     */
	    String email=user.getUser_email();
	    //System.out.println(email);
	    
	    //try catch block to catch No Element Exception : If email id does not exist in database 
	    
	    try
	    {
	      
	      /*
	       * Checks if the email entered by the user already exists in the database 
	       */
	      
	    Users usersPresent = getUserDetails().stream().filter(use -> use.getUser_email().equals(email)).findAny().get();
	    
	    if(usersPresent.getUser_email().equals(email)) 
	      
	      flag=true;
	      
	    }
	    
	    /*
	     * If element does not exist return false
	     */
	    
	    catch(NoSuchElementException e) {
	      
	      flag=false;
	    }
	    
	    catch(Exception e) {}
	    
	    return flag;
	    
	  }
	
	public String checkUser(Users user) {
	    // TODO Auto-generated method stub
	    boolean flag=false;
	    String email=user.getUser_email();
	    try {
	      
	      
	      Users usersPresent = getUserDetails().stream().filter(emp -> emp.getUser_email().equals(email)).findAny().get();
	    
	    /*
	     * Checks if the password entered by the user is same as the one in the database
	     */
	    
	      if(user.getPassword().equals(usersPresent.getPassword()))
	      
	        flag=true;
	        edao.updateStatus(user);
	      }
	    //If no entries are found
	      catch(NoSuchElementException e) {
	      
	      flag=false;
	      }
	    
	    catch(Exception e) {
	    	
	    	return e.getMessage();
	    	
	    }
	    
	    return user.getUser_email();
	  }

	
	public List<Email> getDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		
		List<Email> emailList=edao.getEmailDetails();
	    
	    
	    if(CollectionUtils.isEmpty(emailList))
	    {
	      System.out.println("Data not found");
	    }
	    
	    /*
	     * Gets the user with the email id that matches with the one passed.
	     */
	    
//	    return emailList.stream()
//	        .filter(mail -> mail.getUser_email().equals(email))
//	            .findAny().get();
	    
	   List<Email> l=new ArrayList<>();
	    for(Email e:emailList) {
	    	
	    	if(e.getUser_email().equals(email)) {
	    		
	    		l.add(e);
	    		
	    	}
	    	
	    }
	    if(CollectionUtils.isEmpty(l))
	    {
	      System.out.println("Data not found");
	    }
	    return l;
		
		
	}
	
	public void updateStatusOffline(String email)	{
		
		 edao.updateStatusOffline(email);
		
		
	}
}
