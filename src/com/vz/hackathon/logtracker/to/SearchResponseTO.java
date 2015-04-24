package com.vz.hackathon.logtracker.to;

import java.util.List;

public class SearchResponseTO {

	private List<String> errorLog;
	private String keyword;
	private int count;



	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int i) {
		this.count = i;
	}

	public List<String> getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(List<String> errorLog) {
		this.errorLog = errorLog;
	}
}
