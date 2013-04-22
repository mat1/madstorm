package ch.fhnw.emoba.madstorm;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class ControlActivity extends Activity {

	private static final String ACTIVITY_NAME = ControlActivity.class.getSimpleName();
	
	private SurfaceHolder holder;
	
	private ControlState state;
	private ControlDrawer drawer;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(getLayoutInflater().inflate(R.layout.control_view, null));

		state = new ControlState();
		holder = ((SurfaceView) findViewById(R.id.controlSurface)).getHolder();
		registerListeners();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		drawer = new ControlDrawer(holder, state);
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
		((SurfaceView) findViewById(R.id.controlSurface)).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				SurfaceView sv = (SurfaceView)view;
				state.setPosition((int)event.getX()-sv.getWidth()/2, (int)event.getY()-sv.getHeight()/2);
				return true;
			}
		});
	}

	private static final class ControlDrawer extends Thread {

		private final SurfaceHolder holder;
		private final ControlState state;
		private final Paint green;
		private final Paint black;

		private volatile boolean running = true;
		
		public ControlDrawer(SurfaceHolder holder, ControlState state) {
			this.holder = holder;
			this.state = state;
			this.green = new Paint();
			green.setAntiAlias(false);
			green.setARGB(255, 0, 255, 0);
			this.black = new Paint();
			black.setAntiAlias(false);
			black.setARGB(255, 0, 0, 0);
		}

		@Override
		public void run() {
			Log.v(ACTIVITY_NAME, "Drawer started");
			
			while (running) {
				Canvas c = null;
				try {
					c = holder.lockCanvas();
					if(c != null) draw(c);
					
					Thread.sleep(20); // reduce CPU pressure
				} catch (InterruptedException ex) {
					stopDrawing();
				} finally {
					if (c != null) {
						holder.unlockCanvasAndPost(c);
					}
				}
			}
			Log.v(ACTIVITY_NAME, "Drawer stopped");
		}

		public void stopDrawing() {
			running = false;
		}
		
		private void draw(Canvas c) {
			clearCanvas(c);
			drawCenter(c);
			drawPosition(c);
		}

		private void clearCanvas(Canvas c) {
			c.drawARGB(255, 0, 0, 0);
		}
		
		private void drawCenter(Canvas c) {
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, 25, green);
			c.drawCircle(c.getWidth()/2, c.getHeight()/2, 20, black);
		}
		
		private void drawPosition(Canvas c) {
			Position pos = state.getPosition();
			c.drawCircle(pos.x + c.getWidth()/2, pos.y + c.getHeight()/2, 10, green);
		}
		
	}

	/**
	 * Maintains a threadsafe value for the position.
	 * 
	 * @author Florian Luescher
	 */
	private static final class ControlState {
		private volatile Position curPosition;

		public ControlState() {
			curPosition = new Position(0, 0);
		}

		public void setPosition(final int x, final int y) {
			curPosition = new Position(x, y);
		}

		public Position getPosition() {
			return curPosition;
		}
	}

	/**
	 * Immutable value for a position.
	 * 
	 * @author Florian Luescher
	 */
	private static final class Position {
		public final int x;
		public final int y;

		public Position(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
	}
}
