package com.avinash.emailutility;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class EmailSend 
{

	
	//public static void sendEmail(String fromEmail, String toEmail,String filename)
	public static void sendEmail(String fromEmail, String toEmail,File file)
	{

		// Recipient's email ID needs to be mentioned.
	    String to = toEmail;
	    // Sender's email ID needs to be mentioned
	    String from = fromEmail;
	    //CC
	    String cc = "aavinash.pande@gmail.com";
	    
	    // Assuming you are sending email from localhost
	    String host = "localhost";
	    // Get system properties
	    Properties properties = System.getProperties();
	    // Setup mail server
	    properties.setProperty("mail.smtp.host", "smtp.gmail.com");
	    properties.setProperty("mail.smtp.starttls.enable","true");
	    properties.setProperty("mail.smtp.port", "587");
	    
	    //properties.put("mail.smtp.auth", "false");
	    //properties.put("mail.smtp.auth", "true");

	    properties.setProperty("mail.smtp.user", "aavinaspande");
	    properties.setProperty("mail.smtp.password", "your Gmail Password");
	    properties.setProperty("mail.smtp.auth", "true"); 
	    // Get the default Session object.
	    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() 
	    {
	        protected PasswordAuthentication getPasswordAuthentication() 
	        {
	            return new PasswordAuthentication("aavinashpande@gmail.com","yourGmailPassword"); // click on https://myaccount.google.com/lesssecureapps & make it Allow less secure apps: ON
	        }
	   });

	    try{
	       // Create a default MimeMessage object.
	       MimeMessage message = new MimeMessage(session);
	       // Set From: header field of the header.
	       message.setFrom(new InternetAddress(from));
	       // Set To: header field of the header.
	       message.addRecipient(Message.RecipientType.TO,
	                                new InternetAddress(to));

	       //cc
	       //message.addRecipient(Message.RecipientType.CC,
                   //new InternetAddress(cc));
	       
	       // Set Subject: header field
	       message.setSubject("This is the Subject Line!");

	       // Create the message part 
	       BodyPart messageBodyPart = new MimeBodyPart();

	       // Fill the message
	       messageBodyPart.setText("This is message body");

	       // Create a multipar message
	       Multipart multipart = new MimeMultipart();

	       // Set text message part
	       multipart.addBodyPart(messageBodyPart);

	       // Part two is attachment
	       messageBodyPart = new MimeBodyPart();
	       //String filename = "E:\\Selenium\\SeleniumResources\\SeleniumData\\Hotelapp.xlsx";
	       //DataSource source = new FileDataSource(filename);
	       DataSource source = new FileDataSource(file);
	       messageBodyPart.setDataHandler(new DataHandler(source));
	       //messageBodyPart.setFileName(filename);
	       messageBodyPart.setFileName(file.getName());
	       multipart.addBodyPart(messageBodyPart);

	             
	       
	       // Send the complete message parts
	       message.setContent(multipart );
	       Transport transport = session.getTransport("smtp");
	       transport.connect(null, "aavinashpande@gmail.com","yourgmailPassword");
	       // Send message
	       Transport.send(message);
	       System.out.println("Email Sent successfully....!!");
	    }catch (MessagingException mex) {
	  	  System.out.println(mex.getMessage());
	       mex.printStackTrace();
	    }


	}



}
