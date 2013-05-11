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
	
	public NXTRobotHandler(Context context, String macAddress) {
		this.applicationContext = context;
		this.context = new NXT();
		this.context.addSensorListener(this);
		this.context.connectAndStart(macAddress);
	}

	@Override
	public void shoot() {
		Log.v(LOG_NAME, "Robot received shoot command");
		if(isConnected()) {
			// shoot!
		}
	}

	@Override
	public void setVelocity(int x, int y) {
		Log.v(LOG_NAME, "Robot received setVelocity command: ("+ x + ", " + y+")");
		if(isConnected()) {
			// set velocity on device
		}
	}

	@Override
	public void close() {
		Log.v(LOG_NAME, "Robot connection closing.");
		if(isConnected()) {
			// do Disconnect
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
			break;
		case BluetoothChannel.STATE_CONNECTED:
			Log.v(LOG_NAME, "Connected to device: " + message.toString());
			connected = true;
			bot = new NXTShotBot(context);
			bot.start();
			break;
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	
}
