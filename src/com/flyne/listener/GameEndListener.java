package com.flyne.listener;

import com.flyne.GameState;

public class GameEndListener implements GameListener  {

	@Override
	public void onGameEvent(GameState gameState) {
		checkPlayerDead(gameState);
	}

	private void checkPlayerDead(GameState gameState) {
		if (gameState.getPlayer().isDead() || gameState.getCurrentSpeed() < 0.01) {
			gameState.setGameOver(true);
		}
	}
}
