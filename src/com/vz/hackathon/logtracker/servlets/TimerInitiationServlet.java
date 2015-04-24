package com.vz.hackathon.logtracker.servlets;

import java.io.IOException;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.hackathon.logtracker.SearchTimerTask;


@WebServlet("/TimerInitiationServlet")
public class TimerInitiationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Timer timer= new Timer();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String key = req.getParameter("key");
		 
		if(key.equals("start")){
			timer.scheduleAtFixedRate(new SearchTimerTask(),0L,60000L);
			System.out.println("Timer started...!!!");
		}
		else{
			timer.cancel();
			System.out.println("Timer stopped...!!!");
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
