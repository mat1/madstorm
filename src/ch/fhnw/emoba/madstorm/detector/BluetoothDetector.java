package ch.fhnw.emoba.madstorm.detector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class BluetoothDetector implements RobotDetector {

	public static final String LEGO_MAC_START = "00:16:53"; 
	
	@Override
	public List<LegoDevice> getConnectedLegoDevices() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter == null) {
			Log.w(getClass().getName(), "Bluetooth not supported");
			return Collections.emptyList();
		}
		
		return fromBluetoothDevices(adapter.getBondedDevices());
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
}
