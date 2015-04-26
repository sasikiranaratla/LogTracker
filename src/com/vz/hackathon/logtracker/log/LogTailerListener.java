package com.vz.hackathon.logtracker.log;

import java.util.Date;
import java.util.Set;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import com.vz.hackathon.db.LoggerInfoDAO;
import com.vz.hackathon.logtracker.LogLineProcessor;

public class LogTailerListener implements TailerListener{

	private static LoggerInfoDAO dao =new LoggerInfoDAO();
	private static Set<String> keys = dao.getKeys();
	
	private Tailer tailer;
	@Override
	public void fileNotFound() {
		
	}

	@Override
	public void fileRotated() {
		
	}

	@Override
	public void handle(String line) {
		System.out.println(new Date()+ " : "+line);
		if(null!=keys&&!keys.isEmpty()){
			for(String key : keys){
				if(line.contains(key)){			
					new LogLineProcessor(line,key).start();
					break;
				}
			}
		}
	}

	@Override
	public void handle(Exception arg0) {
		
	}

	@Override
	public void init(Tailer tailer) {
		this.tailer=tailer;
	}
	
	public final void stop(){
		if(tailer!=null){
			tailer.stop();
		}
	}

}
