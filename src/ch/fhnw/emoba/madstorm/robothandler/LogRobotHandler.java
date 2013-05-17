package ch.fhnw.emoba.madstorm.robothandler;

import android.util.Log;

public class LogRobotHandler implements RobotHandler {

	private final String LOG_NAME = LogRobotHandler.class.getSimpleName();
	
	@Override
	public void startShoot() {
		Log.i(LOG_NAME, "Robot received start shoot command");
	}

	@Override
	public void stopShoot() {
		Log.i(LOG_NAME, "Robot received stop shoot command");
	}
	
	@Override
	public void setVelocity(float x, float y) {
		Log.v(LOG_NAME, "Robot received setVelocity command: ("+ x + ", " + y+")");
	}

	@Override
	public void close() {
		Log.v(LOG_NAME, "Robot connection closing.");
	}
}
