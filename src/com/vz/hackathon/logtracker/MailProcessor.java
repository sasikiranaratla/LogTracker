package com.vz.hackathon.logtracker;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vz.hackathon.db.LoggerInfoDAO;
import com.vz.hackathon.logtracker.to.SearchResponseTO;

public class MailProcessor {
	public void sendMail(SearchResponseTO response) {
		final String username = "";
		final String password = "";
		final String mailId="";
 
		Properties props = new Properties();
		props.put("mail.smtp.host", "");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			String group = new LoggerInfoDAO().getGroup(response.getKeyword());
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailId));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailId));
			message.setSubject(group+" "+response.getAlertType()+"!!! : Found "+response.getKeyword()+" in logs ");
			StringBuilder sb = new StringBuilder();
			sb.append("Dear User,"
				+ "\n\n Found "
				+ "\n\n Keyword : "+response.getKeyword()+"\n  error log : \n");
			
				sb.append(response.getErrorLog()+"\n");
			
			message.setText(sb.toString());
 
			Transport.send(message);
			
			System.out.println("Sent mail successfully...!!!");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
