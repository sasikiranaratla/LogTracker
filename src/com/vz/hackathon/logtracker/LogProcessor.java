package com.vz.hackathon.logtracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogProcessor {

	private static final String LOG_FILE = "D:/logs/*.log";

	public static void main(String[] args) {
		//getResponse("IAS_UNEXPECTED_REQUEST");
	}

	public List<String> getResponse(String keyword) {
		List<String> str = new ArrayList<String>();
		long startTime = System.currentTimeMillis();
		BufferedReader input=null;
		try {
			
			ProcessBuilder builder = new ProcessBuilder(new String[] {
					"cmd",
					"/c",
					"logparser \"Select Text from " + LOG_FILE
							+ " where Text like '%" + keyword
							+ "%'\" -i:TEXTLINE -rtp:-1" });
			builder.directory(new File(
					"C:\\Program Files (x86)\\Log Parser 2.2"));
			builder.redirectErrorStream(true);
			Process pr = builder.start();
		input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			String line = null;
			
//			Stream<String> streams =input.lines();
//			System.out.println(streams.count());
//			
//			Iterator<String> itr = streams.iterator();
//			while(itr.hasNext()){
//				str.add(itr.next());
//				System.out.println(line);
//			}
			while ((line = input.readLine()) != null&&!line.contains("Statistics:")) {
				System.out.println(line);
//				if(!"".equals(line.trim())){
//					str.add(line.trim());
//				}
			}
			System.out.println("Processed File...!!!");
			if(str.size()>2){
				str.remove(0);
				str.remove(0);
			}
//			if(str.size()>0){
//			response.setKeyword(keyword);
//		//	response.setErrorLog(str);
//			response.setCount(str.size());
//		//	new MailProcessor().sendMail(response);
//			System.out.println("Sent mail...!!!");
//			response.setMailSent(true);
//			}
			System.out.println("Time taken for processing : "+(System.currentTimeMillis()-startTime)+" ms");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		finally{
			if(null!=input){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}
}
