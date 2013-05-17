package ch.fhnw.emoba.madstorm.robothandler;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import ch.fhnw.edu.mad.com.bluetooth.BluetoothChannel;
import ch.fhnw.edu.mad.mindstorm.LegoBrickSensorListener;
import ch.fhnw.edu.mad.mindstorm.nxt.NXT;
import ch.fhnw.edu.mad.mindstorm.robot.model.NXTShotBot;

public class NXTRobotHandler implements RobotHandler, LegoBrickSensorListener {

	private static final String LOG_NAME = NXTRobotHandler.class.getSimpleName();
	private final Context applicationContext;
	private final NXT context;
	private boolean connected = false;
	private NXTShotBot bot;
	private volatile boolean isShooting = false;
	private float lastX = 0, lastY = 0;
	
	public NXTRobotHandler(Context context, String macAddress) {
		this.applicationContext = context;
		this.context = new NXT();
		this.context.addSensorListener(this);
		this.context.connectAndStart(macAddress);
	}

	@Override
	public void startShoot() {
		Log.v(LOG_NAME, "Robot received shoot command");
		if(isConnected()) {
			isShooting = true;
			bot.action(true);
		}
	}
	
	@Override
	public void stopShoot() {
		Log.v(LOG_NAME, "Robot received shoot command");
		if(isConnected()) {
			bot.action(false);
			isShooting = false;
		}
	}

	@Override
	public void setVelocity(float x, float y) {
		if(isConnected() && !isShooting && (lastX != x || lastY != y)) {
			Log.v(LOG_NAME, "Robot received setVelocity command: ("+ x + ", " + y+")");
			bot.setVelocity(x,y);
			lastX = x; lastY = y;
		}
	}

	@Override
	public void close() {
		Log.v(LOG_NAME, "Robot connection closing.");
		if(isConnected()) {
			bot.setVelocity(0,0);
			bot.action(false);
			bot.stop();
		}
	}
	
	@Override
	public void handleLegoBrickMessage(Message message) {
		switch(message.getData().getInt("message")) {
		case BluetoothChannel.DISPLAY_TOAST:
			Log.v(LOG_NAME, "Received toast display command: " + message.toString());
			Toast.makeText(applicationContext, message.toString(), Toast.LENGTH_LONG).show();
			break;
		case BluetoothChannel.STATE_CONNECTERROR:
			Log.e(LOG_NAME, "Could not connect to device: " + message.toString());
			connected = false;
			Toast.makeText(applicationContext, "Could not connect to device!", Toast.LENGTH_LONG).show();
			break;
		case BluetoothChannel.STATE_CONNECTED:
			Log.v(LOG_NAME, "Connected to device: " + message.toString());
			connected = true;
			bot = new NXTShotBot(context);
			bot.start();
			bot.setVelocity(0,0);
			break;
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	
}
