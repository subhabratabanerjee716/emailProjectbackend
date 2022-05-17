package com.email.emailapi.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.email.emailapi.entity.Email;
import com.email.emailapi.entity.Users;

@Component
public class EmailDao {
	@Autowired
	JdbcTemplate jdbctemplate;
// Interaction with userdb 	
	public boolean insertUsers(String username,String email, String password) {
	    
	    String sql="insert into userdb(username,user_email, password) values ('"+username+"','"+email+"','"+password+"')";
	    
	    boolean status=false;
	    try
	    {
	      jdbctemplate.execute(sql);
	      status=true;
	    }
	    catch(Exception e) {
	      System.out.println(e);
	    }
	    return status;
	  }
	public List<Users> getUserDetails() {
	    
	    List<Users> userList = new ArrayList<>();
	    String sql="select * from userdb";
	    //System.out.println(sql);
	    
	    return jdbctemplate.query(sql, new ResultSetExtractor<List<Users>>() {
	      
	      public List<Users> extractData(ResultSet rs)throws SQLException, DataAccessException{

	        while(rs.next()) {
	          
	          Users e=new Users();
	          e.setUser_email(rs.getString("user_email"));
	          e.setUsername(rs.getString("username"));
	          e.setPassword(rs.getString("password"));
	          e.setStatus(rs.getBoolean("status"));
	          userList.add(e);
	          
	        }
	        return userList;
	      }
	    });

	  }
	public void updateStatus(Users user) {

	    
	    String email="'"+user.getUser_email()+"'";
	    String sql="update userdb set status=true where user_email="+email;
	    
	    try
	    {
	      jdbctemplate.execute(sql);
	    }
	    catch(Exception e) {
	      System.out.println(e);
	    }
	    
	    
	  }
	
	public void updateStatusOffline(String mail) {
	
		    
		    String email="'"+mail+"'";
		    String sql="update userdb set status=false where user_email="+email;
		    
		    try
		    {
		      jdbctemplate.execute(sql);
		    }
		    catch(Exception e) {
		      System.out.println(e);
		    }
		    
		    
		  }
//interaction with emaildb
	
	public void insertEmail(String user_email,String recipient_email,String message,String subject) {
		
		
		String sql="insert into emaildb (user_email,recipient_email,message,subject) values('"+user_email+"','"+recipient_email+"','"+message+"','"+subject+"')";
		
		boolean status=false;
	    try
	    {
	      jdbctemplate.execute(sql);
	      status=true;
	    }
	    catch(Exception e) {
	      System.out.println(e);
	    }
	    System.out.println(status);
		
	}
	
public List<Email> getEmailDetails() {
	    
	    List<Email> emailList = new ArrayList<>();
	    String sql="select * from emaildb";
	    //System.out.println(sql);
	    
	    return jdbctemplate.query(sql, new ResultSetExtractor<List<Email>>() {
	      
	      public List<Email> extractData(ResultSet rs)throws SQLException, DataAccessException{

	        while(rs.next()) {
	          
	          Email e=new Email();
	          
	          e.setUser_email(rs.getString("user_email"));
	          
	          e.setRecipient_email(rs.getString("recipient_email"));
	          
	          e.setMessage(rs.getString("message"));
	          
	          e.setSubject(rs.getString("subject"));
	          
	          emailList.add(e);
	          
	        }
	        return emailList;
	      }
	    });

	  }
}
