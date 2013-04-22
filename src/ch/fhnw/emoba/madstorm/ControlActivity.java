package ch.fhnw.emoba.madstorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ControlActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(MainActivity.controlView);
		registerListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

	public void shoot() {
		Toast.makeText(getApplicationContext(), "Ball shot!", Toast.LENGTH_SHORT).show();
	}
	
	private void registerListeners() {
		((Button) findViewById(R.id.shootButton)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				shoot();
			}
		});
	}
	
}
