package com.vz.hackathon.logtracker.to;

public class ConfigInfoTO {
	
	private int nano;
	private long milli;
	private int errorCount;
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public int getNano() {
		return nano;
	}
	public void setNano(int nano) {
		this.nano = nano;
	}
	public long getMilli() {
		return milli;
	}
	public void setMilli(long milli) {
		this.milli = milli;
	}
	


}
