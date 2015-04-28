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
	
		public static void sendSms(String mobileNumber,String message) {
		
		
		
		try {
			String rsp="";
			 String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
	         data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
	         data += "&" + URLEncoder.encode("msisdn", "UTF-8") + "=" + URLEncoder.encode(mobileNumber, "UTF-8");
	         data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
	         data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode("WebSMS", "UTF-8");
	         data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			URL url = new URL("http://smslane.com/vendorsms/pushsms.aspx");
			URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
        
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
           
            //Read The Response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
                rsp += line;
            }
            wr.close();
            rd.close();
            
            System.out.println(rsp);
            
	 
			//print result
			System.out.println(rsp.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
