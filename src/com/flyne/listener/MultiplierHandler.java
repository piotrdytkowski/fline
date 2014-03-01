package com.flyne.listener;

import com.flyne.GameState;

public class MultiplierHandler implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		float currentSpeed = gameState.getCurrentSpeed();
		if(currentSpeed < gameState.getGameParameters().getMaxSpeed()*0.25) {
            gameState.setMultiplier(0.5);
        } else if (currentSpeed < gameState.getGameParameters().getMaxSpeed()*0.5) {
        	gameState.setMultiplier(1);
        } else if (currentSpeed < gameState.getGameParameters().getMaxSpeed()*0.75) {
        	gameState.setMultiplier(1.5);
        } else {
        	gameState.setMultiplier(3);
        }
	}

}
