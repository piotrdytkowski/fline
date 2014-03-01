package com.flyne.listener;

import java.util.Iterator;

import com.flyne.FPoint;
import com.flyne.GameState;
import com.flyne.drawables.Projectile;
import com.flyne.drawables.ship.Ship;

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
        Iterator<Projectile> projectileIterator = gameState.getProjectiles().iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if(projectile.getLocation().x > gameState.getWidth() || projectile.getLocation().x < 0
                    || projectile.getLocation().y > gameState.getHeight() || projectile.getLocation().y < 0) {
                projectileIterator.remove();
            } else {
                if(projectile.isFriendly()) {
                    for (Ship flyter : gameState.getEnemyShips()) {
                        if(detectProjectileHit(flyter.getLocation(), 40, projectile)) {
                            flyter.takeDamage(projectile.getDamage());
                            projectileIterator.remove();
                            break;
                        }
                    }
                } else {
                    float distance = gameState.getPlayer().getLocation().distance(projectile.getLocation());
                    if(distance < 20) {
                        gameState.getPlayer().takeDamage(projectile.getDamage());
                        projectileIterator.remove();
                    } else if(gameState.getShieldTimer() > 0 && distance < 50) {
                        projectile.setFriendly(true);
                        projectile.setAngle(FPoint.calculateAngleBetweenPoints(projectile.getLocation(), gameState.getPlayer().getLocation()));
                    }
                }
            }
        }
    }

    private boolean detectProjectileHit(FPoint location, int searchRadius, Projectile projectile) {
        return location.distance(projectile.getLocation()) < searchRadius;
    }

}
