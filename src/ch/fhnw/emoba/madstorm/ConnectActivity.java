package ch.fhnw.emoba.madstorm;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.fhnw.emoba.madstorm.detector.BluetoothDetector;
import ch.fhnw.emoba.madstorm.detector.DummyRobotDetector;
import ch.fhnw.emoba.madstorm.detector.RobotDetector;
import ch.fhnw.emoba.madstorm.detector.RobotDetector.LegoDevice;

public class ConnectActivity extends ListActivity {

	public static final String ACTIVITY_NAME = MainActivity.class.getSimpleName();
	
	private RobotDetector detector;
	private LegoDeviceListAdapter devicesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		detector = getRobotDetector();
		loadRobots();
		
		setListAdapter(devicesAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connect, menu);
		return true;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		startActivity(Intents.createControlDeviceIntent(devicesAdapter.getItem(pos).getMac()));
	}
	
	private void loadRobots() {
		Log.v(ACTIVITY_NAME, "Loading devices");
		devicesAdapter = new LegoDeviceListAdapter(this, detector.getConnectedLegoDevices());
	}

	private RobotDetector getRobotDetector() {
		if(MainActivity.IS_EMULATED) {
			Log.v(ACTIVITY_NAME, "Dummy detector chosen");
			return new DummyRobotDetector();
		} else {
			Log.v(ACTIVITY_NAME, "Bluetooth detector chosen");
			BluetoothDetector detector = new BluetoothDetector();
			if(!detector.isBluetoothSupported()) {
				Toast.makeText(getApplicationContext(), "Bluetooth not supported", Toast.LENGTH_LONG).show();
			}
			return detector;
		}
	}
	
	private static class LegoDeviceListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private final List<LegoDevice> devices;
		
		public LegoDeviceListAdapter(Context context, List<LegoDevice> devices) {
			inflater = LayoutInflater.from(context);
			this.devices = devices;
		}

		@Override
		public int getCount() {
			return devices.size();
		}

		@Override
		public LegoDevice getItem(int pos) {
			return devices.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 if (convertView == null) {
		            convertView = inflater.inflate(R.layout.device_list_item, parent, false);
		        }
		        TextView title = (TextView) convertView.findViewById(R.id.title);
		        TextView sub = (TextView) convertView.findViewById(R.id.sub_title);
		 
		        LegoDevice device = devices.get(position);
		        title.setText(device.getName());
		        sub.setText(device.getMac());
		        return convertView;
		}
	}
	
}
