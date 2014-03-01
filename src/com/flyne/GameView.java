package com.flyne;

import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.flyne.listener.GameListener;
import com.flyne.listener.LineScoreListener;
import com.flyne.listener.MultiplierHandler;
import com.flyne.listener.Spawner;
import com.flyne.listener.SpeedHandler;
import com.flyne.listener.TouchStateListener;
import com.flyne.ship.Player;
import com.flyne.ship.Ship;

public class GameView extends View {


    private static final float START_GAME_SPEED = 5;
    public static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	public static final float GRAB_DISTANCE = 100;
    private static final int PLAYER_DAMAGE = 50;

    
    private Track track;
    
    private boolean gameOver = false;

    private int bulletTimeout = 0;

    private Speedometer speedometer;

    private int reflectionTimer = 0;
    
    private GameState gameState;
    private final GameListener touchStateListener = new TouchStateListener();
    private final GameListener lineScoreListener = new LineScoreListener();
    private final GameListener multiplierHandler = new MultiplierHandler();
    private final GameListener spawner = new Spawner();
    private final GameListener speedHandler = new SpeedHandler();

    public GameView(Context context) {
        super(context);
        init();
        Resources res = getResources();
        speedometer = new Speedometer(BitmapFactory.decodeResource(res, R.drawable.speedometer), gameState.getGameParameters().getMaxSpeed());
    }
    
    private void init() {
    	gameState = new GameState();
    	gameState.setCurrentSpeed(START_GAME_SPEED);
    	track = new Track();
    	gameOver = false;
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        speedometer.setSpeed(gameState.getCurrentSpeed());
        multiplierHandler.onGameEvent(gameState);
        lineScoreListener.onGameEvent(gameState);
    	if (gameState.getLocalCache() == null) {
    		gameState.setWidth(canvas.getWidth());
    		gameState.setHeight(canvas.getHeight());
			gameState.setLocalCache(Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565));
			gameState.setLocalCanvas(new Canvas(gameState.getLocalCache()));
			gameState.setPlayer(new Player(new FPoint(70, canvas.getHeight()/2)));
		}
    	checkPlayerDead();
    	spawner.onGameEvent(gameState);
        fireGuns();
        drawGame(canvas);
        touchStateListener.onGameEvent(gameState);
        speedHandler.onGameEvent(gameState);
        cleanLocalCache();
        track.movePoints(gameState.getCurrentSpeed());
        if (!gameOver) {
			invalidate(); // Force a re-draw
		}
    }

    private void checkPlayerDead() {
		if (gameState.getPlayer().isDead() || gameState.getCurrentSpeed() < 0.01) {
			gameOver = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
			String message = gameState.getPlayer().isDead() ? "Game Over. You were destroyed." : "Game Over. You dropped. Keep flyin!";
			builder.setMessage(message)
			.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					init();
					GameView.this.invalidate();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	private void fireGuns() {
        if(gameState.isTouchTwo() && bulletTimeout <= 0) {
            gameState.getProjectiles().add(new Projectile(new FPoint(gameState.getPlayer().getLocation()), new FPoint(gameState.getEvent().getX(1), gameState.getEvent().getY(1)), true, PLAYER_DAMAGE));
            bulletTimeout = gameState.getPlayer().getBulletTimeout();
        }
        bulletTimeout--;
        cleanUpProjectiles();
    }

    private void cleanUpProjectiles() {
        Iterator<Projectile> iterator = gameState.getProjectiles().iterator();
        while(iterator.hasNext()) {
            Projectile next = iterator.next();
            if(next.getLocation().x > getWidth() || next.getLocation().x < 0
                    || next.getLocation().y > getHeight() || next.getLocation().y < 0) {
                iterator.remove();
            }
        }

    }

    private void cleanLocalCache() {
		gameState.getLocalCache().eraseColor(Color.BLACK);
	}

	private void drawGame(Canvas canvas) {
        Paint linePaint;
        Paint electroPaint;
        if(gameState.isLineHit()) {
            linePaint = PaintProvider.activePaint(gameState.getCurrentSpeed(), gameState.getGameParameters().getMaxSpeed());
            electroPaint = PaintProvider.PAINT_ELECTRO_ACTIVE;
        } else {
            linePaint = PaintProvider.inactivePaint();
            electroPaint = PaintProvider.PAINT_ELECTRO_INACTIVE;
        }
    	track.getLineView().draw(canvas, linePaint);
    	track.getElectroView().draw(canvas, electroPaint);
    	track.getLineView().draw(gameState.getLocalCanvas(), linePaint);
        speedometer.draw(canvas, PaintProvider.PAINT_NEEDLE);
    	canvas.drawText("Score: " + gameState.getScore(), TEXT_PADDING, TEXT_PADDING, PaintProvider.PAINT_TEXT);
    	canvas.drawText("Health: " + gameState.getPlayer().getHealth(), TEXT_PADDING + 200, TEXT_PADDING, PaintProvider.PAINT_TEXT);
        manageProjectiles(canvas);
        manageEnemyShips(canvas);
        manageItemDrops(canvas);
        gameState.getPlayer().draw(canvas, PaintProvider.PAINT_RYDER);
	}

    private void manageItemDrops(Canvas canvas) {
        Iterator<ItemDrop> iterator = gameState.getItemDrops().iterator();
        while (iterator.hasNext()) {
            ItemDrop itemDrop = iterator.next();
            if(itemDrop.isOffScreen()) {
                iterator.remove();
            } else {
                if(itemDrop.getLocation().distance(gameState.getPlayer().getLocation()) < 40) {
                    reflectionTimer = 200;
                    iterator.remove();
                } else {
                    itemDrop.move(gameState.getCurrentSpeed());
                    itemDrop.draw(canvas, PaintProvider.PAINT_ITEM_DROP_SHIELD);
                    if(reflectionTimer > 0) {
                        reflectionTimer--;
                    }
                }
            }
        }
    }

    private void manageProjectiles(Canvas canvas) {
        Iterator<Projectile> projectileIterator = gameState.getProjectiles().iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if(projectile.isFriendly()) {
                projectile.draw(canvas, PaintProvider.PAINT_PROJECTILE);
                for (Ship flyter : gameState.getEnemyShips()) {
                    if(detectProjectileHit(flyter.getLocation(), 40, projectile)) {
                        flyter.takeDamage(projectile.getDamage());
                        projectileIterator.remove();
                        break;
                    }
                }
            } else {
                projectile.draw(canvas, PaintProvider.PAINT_FLYTER_PROJECTILE);
                float distance = gameState.getPlayer().getLocation().distance(projectile.getLocation());
                if(distance < 20) {
                	gameState.getPlayer().takeDamage(projectile.getDamage());
                    projectileIterator.remove();
                } else if(reflectionTimer > 0 && distance < 50) {
                    projectile.setFriendly(true);
                    projectile.setAngle(FPoint.calculateAngleBetweenPoints(projectile.getLocation(), gameState.getPlayer().getLocation()));
                }
            }
        }
    }

    private void manageEnemyShips(Canvas canvas) {
        Iterator<Ship> iterator = gameState.getEnemyShips().iterator();
        while(iterator.hasNext()) {
            Ship ship = iterator.next();
            if(ship.isDead() || ship.isOffScreen()){
                gameState.scorePoints(100);
                iterator.remove();
            } else {
                ship.draw(canvas, PaintProvider.PAINT_FLYTER);
            }
        }
    }

    private boolean detectProjectileHit(FPoint location, int searchRadius, Projectile projectile) {
        return location.distance(projectile.getLocation()) < searchRadius;
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gameState.setEvent(event);
        return super.onTouchEvent(event);
    }

}