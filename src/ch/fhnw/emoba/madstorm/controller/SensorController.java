package ch.fhnw.emoba.madstorm.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorController implements Controller, SensorEventListener {
	private static final String LOG_NAME= SensorController.class.getSimpleName();
	private final SensorManager manager;
	private final float[] magnet = new float[3];
	private final float[] gravity = new float[3];
	private final float[] tilt = new float[3];
	
	public SensorController(SensorManager manager) {
		this.manager = manager;
		registerAsListenerAt(this.manager);
	}

	private void registerAsListenerAt(SensorManager manager) {
		manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
		Log.v(LOG_NAME, "Registered sensor listener");
	}
	
	@Override
	public void close() {
		this.manager.unregisterListener(this);
		Log.v(LOG_NAME, "Deregistered sensor listener");
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		final float[] target;
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			target = gravity; 
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			target = magnet;
			break;
		default:
			return;
		}
		System.arraycopy(event.values, 0, target, 0, 3);
	}
	
	@Override
	public Position getPosition() {
		float[] R = new float[9];
		if(SensorManager.getRotationMatrix(R, null, gravity, magnet));
			SensorManager.getOrientation(R, tilt);
			
		return new Position(tilt[1], tilt[2]); 
	}
	
}
