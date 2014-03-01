package com.flyne.listener;

import com.flyne.GameState;
import com.flyne.GameView;

public class LineScoreListener implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		if(gameState.isTouchOne() && gameState.isShipGrabbed() && gameState.isLineHit()) {
            gameState.scorePoints(GameView.AWARD);
        }
	}
}
