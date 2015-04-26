package com.vz.hackathon.logtracker.log;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.vz.hackathon.db.LoggerInfoDAO;
import com.vz.hackathon.logtracker.to.ConfigInfoTO;

public class LogGenerator extends Thread {

	private int nano = 0;
	private long milli = 0L;
	private int errorPerMsg = 1000;
	private static final LogGenerator INSTANCE = new LogGenerator();
	private boolean run = false;

	public static void main(String[] args) {
		// LogGenerator.getInstance().generateLog();
	}

	public LogGenerator() {
		ConfigInfoTO configInfo = new LoggerInfoDAO().getConfigValues();
		nano = configInfo.getNano();
		milli = configInfo.getMilli();
		errorPerMsg = configInfo.getErrorCount();
	}

	public final static Logger serverLogger = Logger.getLogger("com.hack.server");
	public final static Logger networkLogger = Logger.getLogger("com.hack.network");

	public void run() {
		run = true;
		int errorCount=0;
		int alertCount=0;
		int warnCount=0;
		for (int i = 0; run; i++) {
			try {
				Thread.sleep(milli, nano);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (i % errorPerMsg == 0) {
				networkLogger.error("\"CLIENTCOMP\",\"IAS\","
						+ new Timestamp(System.currentTimeMillis())
						+ ",1,\"client\",,,,,,,,,9,\"10.10.10.10\",\"npsclient\",,,,,,,1,,0,,,,,,,,,,IAS_UNEXPECTED_REQUEST,,,,,,,,,,,,,,,,,,,,,,,, ");
				errorCount++;
			}
			else if (i % 1500 == 0) {
				serverLogger.error("Dead lock Identified Order Table .");
				errorCount++;
			}
			else if (i % 2500 == 0) {
				networkLogger.warn("\"CLIENTCOMP\",\"IAS\","
						+ new Timestamp(System.currentTimeMillis())
						+ ",1,\"client\",,,,,,,,,9,\"10.10.10.10\",\"npsclient\",,,,,,,1,,0,,,,,,,,,,IAS_SERVER_UNAVAILABLE,,,,,,,,,,,,,,,,,,,,,,,, ");
				warnCount++;
			}
			else if (i % 1300 == 0) {
				serverLogger.warn("Stuck Thread found on Managed Server abd4rf567 ");
				warnCount++;
			}

			else if (i % 1800 == 0) {
				networkLogger.warn("\"CLIENTCOMP\",\"IAS\","
						+ new Timestamp(System.currentTimeMillis())
						+ ",1,\"client\",,,,,,,,,9,\"10.10.10.10\",\"npsclient\",,,,,,,1,,0,,,,,,,,,,IAS_MALFORMED_REQUEST,,,,,,,,,,,,,,,,,,,,,,,, ");
				alertCount++;
			}
			else if (i % 5000 == 0) {
				serverLogger.warn("Reaching Maximum rows for table SERVICE_DETAILS ");
				alertCount++;
			}
			else if (i % 2000 == 0) {
				serverLogger.warn("Unable to connect address 183.234.56.34:8685 Host Unreachable...");
				alertCount++;
			}
			else if (i % 2600 == 0) {
				serverLogger.warn("Order held due to task fallout...");
				alertCount++;
			}
			else if (i % 600 == 0) {
				serverLogger.info("Mandatory Details missing in request xml... This Order may fail..");
				alertCount++;
			}
			else if (i % 3900 == 0) {
				serverLogger.error("Severity 1 Internal Exception occured...");
				alertCount++;
			}

			
				networkLogger.info("\"CLIENTCOMP\",\"IAS\","
						+ new Timestamp(System.currentTimeMillis())
						+ ",1,\"client\",,,,,,,,,9,\"10.10.10.10\",\"npsclient\",,,,,,,1,,0,,,,,,,,,,IAS_SUCCESS,,,,,,,,,,,,,,,,,,,,,,,, ");
			
				serverLogger.info("\"GET /pics/wpaper.gif HTTP/1.0\" 200 6248 \"http://www.jafsoft.com/asctortf/\" \"Mozilla/4.05 (Macintosh; I; PPC)\"");
			
		}
		System.out.println("Total Alerts : "+alertCount);
		System.out.println("Total Warnings : "+warnCount);
		System.out.println("Total Errors : "+errorCount);
	}

	public void stopLogGenerator() {
		run = false;
	}

	public static LogGenerator getInstance() {
		return INSTANCE;
	}

}
