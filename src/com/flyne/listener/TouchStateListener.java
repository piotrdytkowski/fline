package com.flyne.listener;

import android.view.MotionEvent;

import com.flyne.CircleScanner;
import com.flyne.FPoint;
import com.flyne.GameState;
import com.flyne.GameView;

public class TouchStateListener implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		MotionEvent event = gameState.getEvent();
		if(event != null) {
            if(event.getPointerCount() == 1) {
                gameState.setTouchOne(true);
                gameState.setTouchTwo(false);
            } else if(event.getPointerCount() >= 2) {
                gameState.setTouchTwo(event.getAction() != MotionEvent.ACTION_POINTER_2_UP);
            }
            if(event.getAction() == MotionEvent.ACTION_UP) {
                gameState.setTouchOne(false);
            }
        }
		grabShip(gameState);
		if (gameState.isTouchOne() && gameState.isShipGrabbed()) {
			boolean isLineHit = CircleScanner.scanCircleAtPoint(gameState.getLocalCache(), gameState.getGameParameters().getSearchRadius(), event.getX(0), event.getY(0));
			gameState.setLineHit(isLineHit);
		}
	}
	
	private void grabShip(GameState gameState) {
		if (gameState.isTouchOne()) {
			MotionEvent event = gameState.getEvent();
			float xDistance = Math.abs(gameState.getPlayer().getLocation().x - event.getX(0));
			float yDistance = Math.abs(gameState.getPlayer().getLocation().y - event.getY(0));
			if (xDistance < GameView.GRAB_DISTANCE && yDistance < GameView.GRAB_DISTANCE) {
				gameState.getPlayer().setLocation(new FPoint(event.getX(0), event.getY(0)));
                gameState.setShipGrabbed(true);
			} else {
				gameState.setShipGrabbed(false);
            }
		} else {
			gameState.setShipGrabbed(false);
        }
	}
}
