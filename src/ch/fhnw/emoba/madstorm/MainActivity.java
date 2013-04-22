package ch.fhnw.emoba.madstorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	public static final String ACTIVITY_NAME = MainActivity.class.getSimpleName();
	public static final boolean IS_EMULATED = Build.PRODUCT.startsWith("sdk");
	
	public static View connectView;
	public static View controlView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initializeViews();
		setContentView(connectView);
		registerListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	private void deviceConnectRequested() {
		Log.v(ACTIVITY_NAME, "CONNECT REQUESTED");
		startActivityForResult(Intents.CONTROL_DEVICE, RequestCodes.REQUEST_CONNECT_DEVICE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case RequestCodes.REQUEST_CONNECT_DEVICE:
			switch(resultCode) {
			case RESULT_CANCELED:
				Log.i(ACTIVITY_NAME, "Request: " + requestCode + " cancelled");
				break;
			case RESULT_OK:
				if(IS_EMULATED) {
					setContentView(controlView);
				} else {
					// TODO connect to NXT
				}
			}
			break;
		}
	}
	
	private void registerListeners() {
		((Button) findViewById(R.id.connect_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				deviceConnectRequested();
			}
		});
	}
	
	private void initializeViews() {
		connectView = getLayoutInflater().inflate(R.layout.connect_view, null);
		controlView = getLayoutInflater().inflate(R.layout.control_view, null);
	}
	
	public static class RequestCodes {
		public static final int REQUEST_CONNECT_DEVICE = 1;
	}
	
}