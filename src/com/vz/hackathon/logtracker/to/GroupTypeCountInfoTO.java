package com.vz.hackathon.logtracker.to;

public class GroupTypeCountInfoTO {

	private long errorCount ;
	private long warningCount ;
	private long alertCount ;
	public long getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(long errorCount) {
		this.errorCount = errorCount;
	}
	public long getWarningCount() {
		return warningCount;
	}
	public void setWarningCount(long warningCount) {
		this.warningCount = warningCount;
	}
	public long getAlertCount() {
		return alertCount;
	}
	public void setAlertCount(long alertCount) {
		this.alertCount = alertCount;
	}
	
}
