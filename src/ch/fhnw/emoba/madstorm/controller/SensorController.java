package ch.fhnw.emoba.madstorm.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorController implements Controller, SensorEventListener {
	private final SensorManager manager;
	
	private final float[] valuesMagnet      = new float[3];
    private final float[] valuesAccel       = new float[3];
    private final float[] valuesOrientation = new float[3];
    private final float[] rotationMatrix    = new float[9];
	
	public SensorController(SensorManager manager) {
		this.manager = manager;
		registerAsListenerAt(this.manager);
	}

	private void registerAsListenerAt(SensorManager manager) {
		manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
        case Sensor.TYPE_ACCELEROMETER:
            System.arraycopy(event.values, 0, valuesAccel, 0, 3);
            break;
        case Sensor.TYPE_MAGNETIC_FIELD:
            System.arraycopy(event.values, 0, valuesMagnet, 0, 3);
            break;
		}
	}
	
	@Override
	public Position getPosition() {
		SensorManager.getRotationMatrix(rotationMatrix, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(rotationMatrix, valuesOrientation);
        
		return new Position(0,0); /* TODO calculate position from orientation */
	}

}
