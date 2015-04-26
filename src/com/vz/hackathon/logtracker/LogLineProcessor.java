package com.vz.hackathon.logtracker;

import com.vz.hackathon.db.LoggerInfoDAO;
import com.vz.hackathon.logtracker.to.SearchResponseTO;

public class LogLineProcessor extends Thread{
	
	private String line;
	private String keyword;

	
	public LogLineProcessor(String line,String keyword) {
		super();
		this.line = line;
		this.keyword=keyword;
	}


	public void run(){
		LoggerInfoDAO dao =new LoggerInfoDAO();
		SearchResponseTO response = new SearchResponseTO();
		response.setErrorLog(line);
		response.setKeyword(keyword);
		response.setAlertType(dao.getAlertType(keyword));
		String alert=response.getAlertType();
		if(alert.equals("ERROR") || alert.equals("WARN")){
		try {
			new MailProcessor().sendMail(response);
			response.setMailSent(true);
		} catch (Exception e) {
			e.printStackTrace();			
		}
		}
		dao.persistLogDetails(response);
		
		
	}

}
