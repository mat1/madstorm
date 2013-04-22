package ch.fhnw.emoba.madstorm;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	public static final boolean IS_EMULATED = Build.PRODUCT.startsWith("sdk");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
