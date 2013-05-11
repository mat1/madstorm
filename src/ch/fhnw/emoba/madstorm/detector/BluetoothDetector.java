package ch.fhnw.emoba.madstorm.detector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

public class BluetoothDetector implements RobotDetector {

	public static final int REQUEST_ENABLE_BT = 134;
	public static final String LEGO_MAC_START = "00:16:53"; 
	
	private final Activity context;
	
	public BluetoothDetector(Activity context) {
		this.context = context;
	}
	
	@Override
	public List<LegoDevice> getConnectedLegoDevices() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter == null) {
			Log.w(getClass().getSimpleName(), "Bluetooth not supported");
			return Collections.emptyList();
		}

		if(ensureBluetoothEnabled(adapter)) {
			return fromBluetoothDevices(adapter.getBondedDevices());
		} else {
			return Collections.emptyList();
		}
	}

	private List<LegoDevice> fromBluetoothDevices(Set<BluetoothDevice> bdevs) {
		List<LegoDevice> legoDevices = new ArrayList<LegoDevice>(bdevs.size());
		for(BluetoothDevice dev: bdevs) {
			legoDevices.add(new LegoDevice(dev.getName(), dev.getAddress()));
		}
		return legoDevices;
	}
	
	public boolean isBluetoothSupported() {
		return BluetoothAdapter.getDefaultAdapter() != null;
	}
	
	private boolean ensureBluetoothEnabled(BluetoothAdapter adapter) {
		if(!adapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		    return false;
		} else {
			return true;
		}
	}
}
