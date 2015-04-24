package com.vz.hackathon.logtracker;

import java.util.TimerTask;

public class SearchTimerTask extends TimerTask{

	@Override
	public void run() {
		new LogProcessor().getResponse("a2hlogo.jpg");
	}

}
