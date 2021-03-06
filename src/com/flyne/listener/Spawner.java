package com.flyne.listener;

import com.flyne.FPoint;
import com.flyne.GameState;
import com.flyne.drawables.ItemDrop;
import com.flyne.drawables.ship.Flyekazee;
import com.flyne.drawables.ship.Flyser;
import com.flyne.drawables.ship.Flyter;

public class Spawner implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		if (Math.random() < 0.005) {
			gameState.getEnemyShips().add(new Flyter(gameState.getProjectiles(), new FPoint(gameState.getWidth() + 100, gameState.getHeight() / 2)));
		}
		if (Math.random() < 0.001) {
			int y = Math.random() < 0.5 ? - 100 : gameState.getHeight() + 100;
			gameState.getEnemyShips().add(new Flyekazee(new FPoint(gameState.getWidth() - 100, y), gameState.getPlayer()));
		}
        if(Math.random() < 0.001) {
            gameState.getItemDrops().add(new ItemDrop(new FPoint(gameState.getWidth() + 100, (float)Math.random()*gameState.getHeight())));
        }
        if (Math.random() < 0.001) {
            if(Math.random() < 0.5) {
        	    gameState.getEnemyShips().add(new Flyser(new FPoint(gameState.getWidth() + 100, 100), gameState.getPlayer(), false));
            } else {
                gameState.getEnemyShips().add(new Flyser(new FPoint(gameState.getWidth() + 100, gameState.getHeight()-100), gameState.getPlayer(), true));
            }
        }
	}
}
