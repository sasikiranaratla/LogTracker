package com.vz.hackathon.logtracker.log;

import org.apache.log4j.Logger;

public class LogGenerator {
	
	public static void main(String[] args) {
	//	generateLog();
	}
	
	public final static Logger logger = Logger.getLogger(LogGenerator.class);
	public void generateLog(){
		for(int i=0;true;i++){
			if(i%100==0){
				logger.error("A remote procedure call was attempted. Network Information: Remote IP Address: %7 Remote Port:  %8 RPC Attributes: Interface UUID:  %9 Protocol Sequence: %10 Authentication Service: %11 Authentication Level: %12");;
			}
			else{
				logger.info("\"GET /pics/wpaper.gif HTTP/1.0\" 200 6248 \"http://www.jafsoft.com/asctortf/\" \"Mozilla/4.05 (Macintosh; I; PPC)\"");
			}
		}
	}

}
