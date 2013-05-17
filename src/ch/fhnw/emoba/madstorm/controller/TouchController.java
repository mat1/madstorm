package ch.fhnw.emoba.madstorm.controller;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchController implements Controller, OnTouchListener {

	private volatile Position curPos = new Position(0,0);
	
	public TouchController(SurfaceView view) {
		view.setOnTouchListener(this);
	}
	
	@Override
	public Position getPosition() {
		return curPos;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		SurfaceView sv = (SurfaceView)view;
		curPos = new Position(
					-(event.getX()-sv.getWidth()/2)/(sv.getWidth()/2),
					-(event.getY()-sv.getHeight()/2)/(sv.getHeight()/2));
		return true;
	}
	
}
