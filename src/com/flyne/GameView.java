package com.flyne;

import java.util.Arrays;
import java.util.List;

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

import com.flyne.drawables.HealthBar;
import com.flyne.drawables.ItemDrop;
import com.flyne.drawables.Projectile;
import com.flyne.drawables.Speedometer;
import com.flyne.listener.*;
import com.flyne.menu.highscores.HighScoreOpenHelper;
import com.flyne.drawables.ship.Player;
import com.flyne.drawables.ship.Ship;

public class GameView extends View {

    public static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	public static final float GRAB_DISTANCE = 100;

    private Speedometer speedometer;
    private HealthBar healthBar;
    private GameState gameState;
    private final List<GameListener> gameListeners = Arrays.asList(new TouchStateListener(), new LineScoreListener(), new MultiplierHandler(),
         new Spawner(), new SpeedHandler(), new ProjectileHandler(), new GameEndListener(), new ItemDropHandler(), new EnemyHandler());

    public GameView(Context context) {
        super(context);
        init();
        Resources res = getResources();
        speedometer = new Speedometer(BitmapFactory.decodeResource(res, R.drawable.speedometer), gameState.getGameParameters().getMaxSpeed());
        healthBar = new HealthBar(new FPoint(400,30), Player.SHIP_MAX_HEALTH);
    }
    
    private void init() {
    	gameState = new GameState();
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
    	if (gameState.getLocalCache() == null) {
    		gameState.setWidth(canvas.getWidth());
    		gameState.setHeight(canvas.getHeight());
			gameState.setLocalCache(Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565));
			gameState.setLocalCanvas(new Canvas(gameState.getLocalCache()));
			gameState.setPlayer(new Player(new FPoint(70, canvas.getHeight()/2)));
		}
        gameState.getTrack().movePoints(gameState.getCurrentSpeed());
        for (GameListener gameListener : gameListeners) {
            gameListener.onGameEvent(gameState);
        }
        speedometer.setSpeed(gameState.getCurrentSpeed());
        healthBar.setCurrentHealth(gameState.getPlayer().getHealth());

        cleanLocalCache();
        drawGame(canvas);

        if (!gameState.isGameOver()) {
			invalidate(); // Force a re-draw
		} else {
			showEndGameDialog();
		}
    }

    private void showEndGameDialog() {
    	HighScoreOpenHelper helper = new HighScoreOpenHelper(getContext());
    	helper.checkAndUpdateHighScores("Player", gameState.getScore());
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

	private void cleanLocalCache() {
		gameState.getLocalCache().eraseColor(Color.BLACK);
	}

	private void drawGame(Canvas canvas) {
        if(gameState.isLineHit()) {
            gameState.getTrack().setElectroViewPaint(PaintProvider.activePaint(gameState.getCurrentSpeed(), gameState.getGameParameters().getMaxSpeed()));
            gameState.getTrack().setLineViewPaint(PaintProvider.PAINT_ELECTRO_ACTIVE);
        } else {
            gameState.getTrack().setElectroViewPaint(PaintProvider.inactivePaint());
            gameState.getTrack().setLineViewPaint(PaintProvider.PAINT_ELECTRO_INACTIVE);
        }
        gameState.getTrack().getLineView().draw(gameState.getLocalCanvas());
        gameState.getTrack().getLineView().draw(canvas);
        gameState.getTrack().getElectroView().draw(canvas);
        speedometer.draw(canvas);
        healthBar.draw(canvas);
    	canvas.drawText("Score: " + gameState.getScore(), TEXT_PADDING, TEXT_PADDING, PaintProvider.PAINT_TEXT);
        drawProjectiles(canvas);
        drawEnemyShips(canvas);
        drawItemDrops(canvas);
        gameState.getPlayer().draw(canvas);
	}

    private void drawItemDrops(Canvas canvas) {
        for (ItemDrop itemDrop : gameState.getItemDrops()) {
            itemDrop.draw(canvas);
        }
    }

    private void drawProjectiles(Canvas canvas) {
        for (Projectile projectile : gameState.getProjectiles()) {
            projectile.draw(canvas);
        }
    }

    private void drawEnemyShips(Canvas canvas) {
        for (Ship ship : gameState.getEnemyShips()) {
            ship.draw(canvas);
        }
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gameState.setEvent(event);
        return super.onTouchEvent(event);
    }

}
