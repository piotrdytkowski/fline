package com.flyne.listener;

import com.flyne.GameState;
import com.flyne.ship.Ship;

import java.util.Iterator;

public class EnemyHandler implements GameListener {
    @Override
    public void onGameEvent(GameState gameState) {
        Iterator<Ship> iterator = gameState.getEnemyShips().iterator();
        while(iterator.hasNext()) {
            Ship ship = iterator.next();
            if(ship.isDead()){
                gameState.scorePoints(100);
                iterator.remove();
            } else if (ship.isOffScreen()) {
                iterator.remove();
            }
        }
    }
}
