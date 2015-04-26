package com.vz.hackathon.logtracker.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vz.hackathon.logtracker.log.LogSearch;
import com.vz.hackathon.logtracker.log.LogTailerListener;


@WebServlet("/logSearch")
public class InitiateLogSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		if(null!=key){
			if(key.equals("start")){
				LogSearch.getInstance().instantiateTailer();
			}
			else if(key.equals("stop")){
				List<LogTailerListener> listeners = LogSearch.getInstance().getListeners();
				if(null!=listeners){
					for(LogTailerListener listener : listeners){
						if(null!=listener){
							listener.stop();
						}
					}
				}
			}
		}
	}

}
