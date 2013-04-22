package ch.fhnw.emoba.madstorm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ch.fhnw.emoba.madstorm.controller.Controller;
import ch.fhnw.emoba.madstorm.controller.Controller.Position;
import ch.fhnw.emoba.madstorm.controller.ControllerListener;
import ch.fhnw.emoba.madstorm.controller.SensorController;
import ch.fhnw.emoba.madstorm.controller.TouchController;

public class ControlActivity extends Activity {

	private Controller controller;
	private ControlThread drawer;
	private List<ControllerListener> positionListeners = new ArrayList<ControllerListener>(2);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(getLayoutInflater().inflate(R.layout.control_view, null));

		registerListeners();
		setupController();
		setupControllerListeners();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		drawer = new ControlThread(controller, positionListeners);
		drawer.start();
	}
	
	@Override
	protected void onStop() {
		drawer.stopDrawing();
		super.onStop();
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

	private void setupController() {
		if(MainActivity.IS_EMULATED) {
			controller = new TouchController(((SurfaceView) findViewById(R.id.controlSurface)));
		} else {
			controller = new SensorController((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		}
	}
	
	private void setupControllerListeners() {
		positionListeners.add(new SurfaceDrawer(((SurfaceView) findViewById(R.id.controlSurface)).getHolder()));
	}
	
	private static final class SurfaceDrawer implements ControllerListener {
		private final SurfaceHolder holder;
		private final Paint green, black;
		
		public SurfaceDrawer(SurfaceHolder holder) {
			this.holder = holder;
			this.green = new Paint();
			green.setAntiAlias(false);
			green.setARGB(255, 0, 255, 0);
			this.black = new Paint();
			black.setAntiAlias(false);
			black.setARGB(255, 0, 0, 0);
		}
		
		@Override
		public void update(Position position) {
			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if(c != null) draw(c, position);
			} finally {
				if (c != null) {
					holder.unlockCanvasAndPost(c);
				}
			}
		}
		
		private void draw(Canvas c, Position pos) {
			clearCanvas(c);
			drawCenter(c);
			drawPosition(c, pos);
		}

		private void clearCanvas(Canvas c) {
			c.drawARGB(255, 0, 0, 0);
		}
		
		private void drawCenter(Canvas c) {
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, 25, green);
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, 20, black);
		}
		
		private void drawPosition(Canvas c, Position pos) {
			c.drawCircle(pos.x + c.getWidth()/2, pos.y + c.getHeight()/2, 10, green);
		}
	}
	
	private static final class ControlThread extends Thread {

		private static final int DRAWER_WAITTIME = 10;
		private static final String LOG_NAME = "Control thread";
		
		private final Controller controller;
		private final List<ControllerListener> listeners;

		private volatile boolean running = true;
		
		public ControlThread(Controller controller, List<ControllerListener> listeners) {
			this.controller = controller;
			this.listeners = listeners;
		}

		@Override
		public void run() {
			Log.v(LOG_NAME, "Control thread started");
			while (running) {
				try {
					Position pos = controller.getPosition();
					
					for(ControllerListener lis: listeners) {
						try {
							lis.update(pos);
						} catch (Exception ex) {
							Log.e(LOG_NAME, ex.toString());
						}
					}
					
					Thread.sleep(DRAWER_WAITTIME); // reduce CPU pressure
				} catch (InterruptedException ex) {
					stopDrawing();
				}
			}
			Log.v(LOG_NAME, "Control thread  stopped");
		}

		public void stopDrawing() {
			running = false;
		}
		
	}

}
