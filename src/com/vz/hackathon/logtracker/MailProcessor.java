package com.vz.hackathon.logtracker;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vz.hackathon.logtracker.to.SearchResponseTO;

public class MailProcessor {
	public void sendMail(SearchResponseTO response) {
		final String username = "v823365";
		final String password = "R@gh@vendr@99";
 
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.verizon.com");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sasi.aratla@verizon.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("sasi.aratla@verizon.com"));
			message.setSubject("Attention Required!!! : Found "+response.getKeyword()+" in logs ");
			StringBuilder sb = new StringBuilder();
			sb.append("Dear User,"
				+ "\n\n Found "
				+ "\n\n Keyword : "+response.getKeyword()+"\n Count : "+response.getCount()+"\n error log : \n");
			for(String str : response.getErrorLog()){
				sb.append(str+"\n");
			}
			message.setText(sb.toString());
 
			Transport.send(message);
			
			System.out.println("Sent mail successfully...!!!");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
