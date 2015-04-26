package com.vz.hackathon.util;

import java.util.Date;

public class LogUtil {
	
	public static void logInfo(String className, String methodName, String message){
		System.out.println(new StringBuilder("\n").append(new Date().toString()).append(":INFO:Source=").
				append(className).append(", Operation=").append(methodName).append(", Message=").append(message+".").toString());
	}
	
	public static void logTrace(Throwable error){
		error.printStackTrace();
	}

}
