package ch.fhnw.emoba.madstorm;

import android.content.Intent;

public class Intents {
	public static final String CONTROL_DEVICE = "ch.fhnw.emoba.madstorm.CONTROL_DEVICE";
	public static final String SELECT_DEVICE = "ch.fhnw.emoba.madstorm.SELECT_DEVICE";
	
	public static Intent createControlDeviceIntent(String macAddress) {
		Intent intent = new Intent(Intents.CONTROL_DEVICE);
		intent.putExtra("MAC", macAddress);
		return intent;
	}
}
