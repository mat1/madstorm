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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(getLayoutInflater().inflate(R.layout.connect_view, null));
		
		registerListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void deviceConnectRequested() {
		Log.v(ACTIVITY_NAME, "CONNECT REQUESTED");
		startActivity(new Intent(Intents.SELECT_DEVICE));
	}
	
	private void registerListeners() {
		((Button) findViewById(R.id.connect_button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				deviceConnectRequested();
			}
		});
	}
	
}
