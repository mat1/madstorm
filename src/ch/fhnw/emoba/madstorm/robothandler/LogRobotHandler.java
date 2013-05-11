package ch.fhnw.emoba.madstorm.robothandler;

import android.util.Log;

public class LogRobotHandler implements RobotHandler {

	private final String LOG_NAME = LogRobotHandler.class.getSimpleName();
	
	@Override
	public void shoot() {
		Log.v(LOG_NAME, "Robot received shoot command");
	}

	@Override
	public void setVelocity(int x, int y) {
		Log.v(LOG_NAME, "Robot received setVelocity command: ("+ x + ", " + y+")");
	}

	@Override
	public void close() {
		Log.v(LOG_NAME, "Robot connection closing.");
	}
}
