package com.vz.hackathon.logtracker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.hackathon.logtracker.log.LogGenerator;
import com.vz.hackathon.logtracker.log.LogSearch;


@WebServlet(value="/StartUp",loadOnStartup=1)
public class StartUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
//		super.init();
//		LogGenerator.getInstance().start();
//		LogSearch.getInstance().instantiateTailer();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
		LogGenerator.getInstance().start();
		LogSearch.getInstance().instantiateTailer();
	}
}
