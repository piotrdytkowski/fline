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
                    gameState.getPlayer().setShieldTimer(1000);
                    iterator.remove();
                } else {
                    itemDrop.move(gameState.getCurrentSpeed());
                }
            }
        }
    }
}
