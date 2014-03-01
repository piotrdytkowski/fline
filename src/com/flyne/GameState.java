package com.flyne;

import java.util.ArrayList;
import java.util.List;

import com.flyne.ship.Player;
import com.flyne.ship.Ship;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class GameState {
	
	private GameParameters gameParameters = new GameParameters();

    private Track track = new Track();
	private MotionEvent event;
	private boolean touchOne, touchTwo, shipGrabbed, lineHit;
	private int score;
	private double multiplier;
	private float currentSpeed = gameParameters.getStartSpeed();
    private int shieldTimer;
	
	private List<Projectile> projectiles = new ArrayList<Projectile>();;
    private List<Ship> enemyShips = new ArrayList<Ship>();
    private List<ItemDrop> itemDrops = new ArrayList<ItemDrop>();
    
    private boolean gameOver;
    
    private Player player;
    
    private int width, height;
    
    private Bitmap localCache;
    private Canvas localCanvas;
	
	public MotionEvent getEvent() {
		return event;
	}
	public void setEvent(MotionEvent event) {
		this.event = event;
	}
	public boolean isTouchOne() {
		return touchOne;
	}
	public void setTouchOne(boolean touchOne) {
		this.touchOne = touchOne;
	}
	public boolean isTouchTwo() {
		return touchTwo;
	}
	public void setTouchTwo(boolean touchTwo) {
		this.touchTwo = touchTwo;
	}
	
	public void scorePoints(int points) {
		this.score += points * multiplier;
	}
	public boolean isShipGrabbed() {
		return shipGrabbed;
	}
	public void setShipGrabbed(boolean shipGrabbed) {
		this.shipGrabbed = shipGrabbed;
	}
	public boolean isLineHit() {
		return lineHit;
	}
	public void setLineHit(boolean lineHit) {
		this.lineHit = lineHit;
	}
	public double getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	public int getScore() {
		return score;
	}
	public float getCurrentSpeed() {
		return currentSpeed;
	}
	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
	public List<Ship> getEnemyShips() {
		return enemyShips;
	}
	public void setEnemyShips(List<Ship> enemyShips) {
		this.enemyShips = enemyShips;
	}
	public List<ItemDrop> getItemDrops() {
		return itemDrops;
	}
	public void setItemDrops(List<ItemDrop> itemDrops) {
		this.itemDrops = itemDrops;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public GameParameters getGameParameters() {
		return gameParameters;
	}
	public void setGameParameters(GameParameters gameParameters) {
		this.gameParameters = gameParameters;
	}
	public Bitmap getLocalCache() {
		return localCache;
	}
	public void setLocalCache(Bitmap localCache) {
		this.localCache = localCache;
	}
	public Canvas getLocalCanvas() {
		return localCanvas;
	}
	public void setLocalCanvas(Canvas localCanvas) {
		this.localCanvas = localCanvas;
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
    public int getShieldTimer() {
        return shieldTimer;
    }
    public void setShieldTimer(int shieldTimer) {
        this.shieldTimer = shieldTimer;
    }
    public Track getTrack() {
        return track;
    }
    public void setTrack(Track track) {
        this.track = track;
    }
}
