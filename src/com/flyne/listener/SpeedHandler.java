package com.flyne.listener;

import com.flyne.GameState;

public class SpeedHandler implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		if(gameState.isTouchOne() && gameState.isShipGrabbed()) {
            if(gameState.isLineHit()) {
            	float currentSpeed = gameState.getCurrentSpeed();
            	if (currentSpeed < gameState.getGameParameters().getMaxSpeed()) {
					gameState.setCurrentSpeed(currentSpeed + gameState.getGameParameters().getAcceleration());
				}
            } else {
            	decreaseSpeed(gameState);
            }
        } else {
        	decreaseSpeed(gameState);
        }
	}

	private void decreaseSpeed(GameState gameState) {
		if (gameState.getEvent() != null) {
			float currentSpeed = gameState.getCurrentSpeed();
			if (currentSpeed > 0) {
				gameState.setCurrentSpeed(currentSpeed - gameState.getGameParameters().getDeceleration());
			} else if (currentSpeed < gameState.getGameParameters().getDeceleration()) {
				gameState.setCurrentSpeed(0);
			}
		}
	}
}
