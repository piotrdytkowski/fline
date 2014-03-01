package com.flyne.listener;

import com.flyne.GameState;
import com.flyne.drawables.ItemDrop;

import java.util.Iterator;

public class ItemDropHandler implements GameListener {
    @Override
    public void onGameEvent(GameState gameState) {
        Iterator<ItemDrop> iterator = gameState.getItemDrops().iterator();
        while (iterator.hasNext()) {
            ItemDrop itemDrop = iterator.next();
            if(itemDrop.isOffScreen()) {
                iterator.remove();
            } else {
                if(itemDrop.getLocation().distance(gameState.getPlayer().getLocation()) < 40) {
                    gameState.setShieldTimer(100);
                    iterator.remove();
                } else {
                    itemDrop.move(gameState.getCurrentSpeed());
                    if(gameState.getShieldTimer() > 0) {
                        gameState.setShieldTimer(gameState.getShieldTimer()-1);
                    }
                }
            }
        }
    }
}
