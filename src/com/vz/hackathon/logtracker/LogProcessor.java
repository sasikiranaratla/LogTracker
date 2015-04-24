package com.vz.hackathon.logtracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.vz.hackathon.logtracker.to.SearchResponseTO;

public class LogProcessor {

	private static final String LOG_FILE = "D:/logs/sample*.log";

	public static void main(String[] args) {
	//	System.out.println(getResponse("a2hlogo.jpg"));
	}

	public SearchResponseTO getResponse(String keyword) {
		SearchResponseTO response = new SearchResponseTO();
		try {
			
			ProcessBuilder builder = new ProcessBuilder(new String[] {
					"cmd",
					"/c",
					"logparser \"Select Text from " + LOG_FILE
							+ " where Text like '%" + keyword
							+ "%'\" -i:TEXTLINE -q:Off" });
			builder.directory(new File(
					"C:\\Program Files (x86)\\Log Parser 2.2"));
			builder.redirectErrorStream(true);
			Process pr = builder.start();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));

			String line = null;

			List<String> str = new ArrayList<String>();
			while ((line = input.readLine()) != null) {
				if (line.contains("Statistics:")) {
					break;
				}
				if(!"".equals(line.trim())){
				str.add(line);
				}
			}
			str.remove(0);
			str.remove(0);
			if(str.size()>0){
			response.setKeyword(keyword);
			response.setErrorLog(str);
			response.setCount(str.size());
			new MailProcessor().sendMail(response);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return response;
	}
}
