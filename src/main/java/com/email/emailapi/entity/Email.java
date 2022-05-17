package com.email.emailapi.entity;

public class Email {

	private String user_email;
	
	private String pass;
	
	private String recipient_email;
	
	private String subject;
	
	private String message;
	
	
	
	
	

	@Override
	public String toString() {
		return "Email [user_email=" + user_email + ", recipient_email=" + recipient_email
				+ ", subject=" + subject + ", message=" + message + "]";
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRecipient_email() {
		return recipient_email;
	}

	public void setRecipient_email(String recipient_email) {
		this.recipient_email = recipient_email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
