package com.flyne;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.flyne.ship.Flyekazee;
import com.flyne.ship.Flyter;
import com.flyne.ship.Ryder;
import com.flyne.ship.Ship;

public class GraphicsTestView extends View {


    private static final float START_GAME_SPEED = 5;
    private static final float MAX_SPEED = 20;
    private static final float ACCELERATION = .05f;
    private static final float DECELERATION = .1f;
    private static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	private static final float GRAB_DISTANCE = 100;
    private static final int PLAYER_DAMAGE = 50;

    private int score;
    private float currentSpeed;

    private Ryder ryder;
    private Track track;
    
    private CircleScanner circleScanner;
    
    private Bitmap localCache;
    private Canvas localCanvas;

    private MotionEvent event;

    private boolean touchOne = false;
    private boolean touchTwo = false;

    private boolean shipGrabbed;
    private boolean lineHit = false;
    
    private boolean gameOver = false;

    private List<Projectile> projectiles;
    private List<Ship> enemyShips;
    private List<ItemDrop> itemDrops;
    private int bulletTimeout = 0;

    private Speedometer speedometer;
    private double multiplier;

    private int reflectionTimer = 0;

    public GraphicsTestView(Context context) {
        super(context);
        Resources res = getResources();
        speedometer = new Speedometer(BitmapFactory.decodeResource(res, R.drawable.speedometer), MAX_SPEED);
        circleScanner = new CircleScanner(40);
        init();
    }
    
    private void init() {
    	score = 0;
    	currentSpeed = START_GAME_SPEED;
    	track = new Track();
    	projectiles = new ArrayList<Projectile>();
    	enemyShips = new ArrayList<Ship>();
        itemDrops = new ArrayList<ItemDrop>();
    	gameOver = false;
    	event = null;
    	touchOne = false;
    	touchTwo = false;
    	lineHit = false;
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        speedometer.setSpeed(currentSpeed);
        manageTouchState();
        recalculateMultiplier();
        applyLineScore();
    	if (localCache == null) {
			localCache = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565);
			localCanvas = new Canvas(localCache);
			ryder = new Ryder(new FPoint(70, canvas.getHeight()/2));
		}
    	checkPlayerDead();
    	spawnStuff();
    	grabShip();
        fireGuns();
        drawGame(canvas);
        handleLineHit();
        cleanLocalCache();
        track.movePoints(currentSpeed);
        
        if (!gameOver) {
			invalidate(); // Force a re-draw
		}
    }

    private void checkPlayerDead() {
		if (ryder.isDead() || currentSpeed < 0.01) {
			gameOver = true;
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
			String message = ryder.isDead() ? "Game Over. You were destroyed." : "Game Over. You dropped. Keep flyin!";
			builder.setMessage(message)
			.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					localCache = null;
					init();
					GraphicsTestView.this.invalidate();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	private void spawnStuff() {
		if (Math.random() < 0.005) {
			enemyShips.add(new Flyter(projectiles, new FPoint(this.getWidth() + 100, this.getHeight() / 2)));
		}
		if (Math.random() < 0.001) {
			int y = Math.random() < 0.5 ? - 100 : this.getHeight() + 100;
			enemyShips.add(new Flyekazee(new FPoint(this.getWidth() - 100, y), ryder));
		}
        if(Math.random() < 0.001) {
            itemDrops.add(new ItemDrop(new FPoint(this.getWidth() + 100, (float)Math.random()*this.getHeight())));
        }
	}

	private void fireGuns() {
        if(touchTwo && bulletTimeout <= 0) {
            projectiles.add(new Projectile(new FPoint(ryder.getLocation()), new FPoint(event.getX(1), event.getY(1)), true, PLAYER_DAMAGE));
            bulletTimeout = ryder.getBulletTimeout();
        }
        bulletTimeout--;
        cleanUpProjectiles();
    }

    private void cleanUpProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while(iterator.hasNext()) {
            Projectile next = iterator.next();
            if(next.getLocation().x > getWidth() || next.getLocation().x < 0
                    || next.getLocation().y > getHeight() || next.getLocation().y < 0) {
                iterator.remove();
            }
        }

    }

    private void grabShip() {
		if (touchOne) {
			float xDistance = Math.abs(ryder.getLocation().x - event.getX(0));
			float yDistance = Math.abs(ryder.getLocation().y - event.getY(0));
			if (xDistance < GRAB_DISTANCE && yDistance < GRAB_DISTANCE) {
                ryder.setLocation(new FPoint(event.getX(0), event.getY(0)));
                shipGrabbed = true;
			} else {
                shipGrabbed = false;
            }
		} else {
            shipGrabbed = false;
        }
	}

	private void manageTouchState() {
        if(event != null) {
            if(event.getPointerCount() == 1) {
                touchOne = true;
                touchTwo = false;
            } else if(event.getPointerCount() >= 2) {
                touchTwo = event.getAction() != MotionEvent.ACTION_POINTER_2_UP;
            }
            if(event.getAction() == MotionEvent.ACTION_UP) {
                touchOne = false;
            }
        }
    }

    private void cleanLocalCache() {
		localCache.eraseColor(Color.BLACK);
	}

	private void drawGame(Canvas canvas) {
        Paint linePaint;
        Paint electroPaint;
        if(lineHit) {
            linePaint = PaintProvider.activePaint(currentSpeed, MAX_SPEED);
            electroPaint = PaintProvider.PAINT_ELECTRO_ACTIVE;
        } else {
            linePaint = PaintProvider.inactivePaint();
            electroPaint = PaintProvider.PAINT_ELECTRO_INACTIVE;
        }
    	track.getLineView().draw(canvas, linePaint);
    	track.getElectroView().draw(canvas, electroPaint);
    	track.getLineView().draw(localCanvas, linePaint);
        speedometer.draw(canvas, PaintProvider.PAINT_NEEDLE);
    	canvas.drawText("Score: " + score, TEXT_PADDING, TEXT_PADDING, PaintProvider.PAINT_TEXT);
    	canvas.drawText("Health: " + ryder.getHealth(), TEXT_PADDING + 200, TEXT_PADDING, PaintProvider.PAINT_TEXT);
        manageProjectiles(canvas);
        manageEnemyShips(canvas);
        manageItemDrops(canvas);
        ryder.draw(canvas, PaintProvider.PAINT_RYDER);
	}

    private void manageItemDrops(Canvas canvas) {
        Iterator<ItemDrop> iterator = itemDrops.iterator();
        while (iterator.hasNext()) {
            ItemDrop itemDrop = iterator.next();
            if(itemDrop.isOffScreen()) {
                iterator.remove();
            } else {
                if(itemDrop.getLocation().distance(ryder.getLocation()) < 40) {
                    reflectionTimer = 200;
                    iterator.remove();
                } else {
                    itemDrop.move(currentSpeed);
                    itemDrop.draw(canvas, PaintProvider.PAINT_ITEM_DROP_SHIELD);
                    if(reflectionTimer > 0) {
                        reflectionTimer--;
                    }
                }
            }
        }
    }

    private void manageProjectiles(Canvas canvas) {
        Iterator<Projectile> projectileIterator = projectiles.iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if(projectile.isFriendly()) {
                projectile.draw(canvas, PaintProvider.PAINT_PROJECTILE);
                for (Ship flyter : enemyShips) {
                    if(detectProjectileHit(flyter.getLocation(), 40, projectile)) {
                        flyter.takeDamage(projectile.getDamage());
                        projectileIterator.remove();
                        break;
                    }
                }
            } else {
                projectile.draw(canvas, PaintProvider.PAINT_FLYTER_PROJECTILE);
                float distance = ryder.getLocation().distance(projectile.getLocation());
                if(distance < 20) {
                    ryder.takeDamage(projectile.getDamage());
                    projectileIterator.remove();
                } else if(reflectionTimer > 0 && distance < 50) {
                    projectile.setFriendly(true);
                    projectile.setAngle(FPoint.calculateAngleBetweenPoints(projectile.getLocation(), ryder.getLocation()));
                }
            }
        }
    }

    private void manageEnemyShips(Canvas canvas) {
        Iterator<Ship> iterator = enemyShips.iterator();
        while(iterator.hasNext()) {
            Ship ship = iterator.next();
            if(ship.isDead() || ship.isOffScreen()){
                scorePoints(100);
                iterator.remove();
            } else {
                ship.draw(canvas, PaintProvider.PAINT_FLYTER);
            }
        }
    }

    private boolean detectProjectileHit(FPoint location, int searchRadius, Projectile projectile) {
        return location.distance(projectile.getLocation()) < searchRadius;
    }

    private void handleLineHit() {
        if(touchOne && shipGrabbed) {
            lineHit = circleScanner.scanCircleAtPoint(localCache, event.getX(0), event.getY(0));
            if(lineHit) {
            	if (currentSpeed < MAX_SPEED) {
					currentSpeed += ACCELERATION;
				}
            } else {
            	decreaseSpeed();
            }
        } else {
        	decreaseSpeed();
        }
	}

    private void applyLineScore() {
        if(touchOne && shipGrabbed && lineHit) {
            scorePoints(AWARD);
        }
    }

    private void scorePoints(int points) {
        score += points * multiplier;
    }

    private void recalculateMultiplier() {
        if(currentSpeed < MAX_SPEED*0.25) {
            multiplier = 0.5;
        } else if (currentSpeed < MAX_SPEED*0.5) {
            multiplier = 1;
        } else if (currentSpeed < MAX_SPEED*0.75) {
            multiplier = 1.5;
        } else {
            multiplier = 3;
        }
    }

	private void decreaseSpeed() {
		if (event != null) {
			if (currentSpeed > 0) {
				currentSpeed -= DECELERATION;
			} else if (currentSpeed < DECELERATION) {
				currentSpeed = 0;
			}
		}
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        return super.onTouchEvent(event);
    }

}
