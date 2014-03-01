package com.flyne.listener;

import java.util.Iterator;

import com.flyne.FPoint;
import com.flyne.GameState;
import com.flyne.Projectile;

public class ProjectileHandler implements GameListener {

	@Override
	public void onGameEvent(GameState gameState) {
		fireGuns(gameState);
		cleanUpProjectiles(gameState);
	}
	
	private void fireGuns(GameState gameState) {
        if(gameState.isTouchTwo() && gameState.getPlayer().getBulletTimeout() <= 0) {
            gameState.getProjectiles().add(new Projectile(
            		new FPoint(gameState.getPlayer().getLocation()),
            		new FPoint(gameState.getEvent().getX(1), gameState.getEvent().getY(1)),
            		true, gameState.getGameParameters().getPlayerDamage()));
        }
        gameState.getPlayer().tickBulletTimeout();
    }

    private void cleanUpProjectiles(GameState gameState) {
        Iterator<Projectile> iterator = gameState.getProjectiles().iterator();
        while(iterator.hasNext()) {
            Projectile next = iterator.next();
            if(next.getLocation().x > gameState.getWidth() || next.getLocation().x < 0
                    || next.getLocation().y > gameState.getHeight() || next.getLocation().y < 0) {
                iterator.remove();
            }
        }

    }

}
