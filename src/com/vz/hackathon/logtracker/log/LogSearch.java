package com.vz.hackathon.logtracker.log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.input.Tailer;

import com.vz.hackathon.db.LoggerInfoDAO;

public class LogSearch {
	
	private static final LogSearch INSTANCE = new LogSearch();
	private static List<LogTailerListener> listeners = new ArrayList<LogTailerListener>();
	private LogSearch() {
	}
	
	public static LogSearch getInstance() {
		return INSTANCE;
	}

	public void instantiateTailer(){
		List<String> files = new LoggerInfoDAO().getFiles();
		if (null!=files&&!files.isEmpty()) {
			
			for (String file : files) {
				if (null!=file) {
					LogTailerListener listener = new LogTailerListener();
					Tailer.create(new File(file), listener, 100L, false,
							true);
					listeners.add(listener);
				}
			}
		}
	}

	public List<LogTailerListener> getListeners() {
		return listeners;
	}
	
	public void cleanListeners() {
		listeners = new ArrayList<LogTailerListener>();
	}
}
