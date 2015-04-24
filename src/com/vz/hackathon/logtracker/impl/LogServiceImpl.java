package com.vz.hackathon.logtracker.impl;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.vz.hackathon.logtracker.LogProcessor;
import com.vz.hackathon.logtracker.service.LogService;

public class LogServiceImpl implements LogService{
	
	public static void main(String[] args) {
		System.out.println(new LogServiceImpl().getSearchResponse("a2hlogo.jpg"));
	}

	@Override
	public String getSearchResponse(String keyword) {
		LogProcessor processor = new LogProcessor();
		return convertToJson(processor.getResponse(keyword));
	}
	
	private String convertToJson(Object obj){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
