package ch.fhnw.emoba.madstorm.robothandler;

public interface RobotHandler {
	void startShoot();
	void stopShoot();
	void setVelocity(float x, float y);
	void close();
}
