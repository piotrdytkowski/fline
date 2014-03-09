package com.flyne.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.flyne.FPoint;
import com.flyne.R;
import com.flyne.drawables.Projectile;
import com.flyne.drawables.ship.Flyter;

public class MenuLayout extends LinearLayout {
	
	private List<Flyter> flyters;
	private List<Projectile> projectiles;

	public MenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.main_menu, this, true);
		
		flyters = new ArrayList<Flyter>();
		projectiles = new ArrayList<Projectile>();
		int x = -50;
		int y = 400;
		flyters.add(new Flyter(projectiles, new FPoint(x, y)));
		flyters.add(new Flyter(projectiles, new FPoint(x, y)));
		flyters.add(new Flyter(projectiles, new FPoint(x, y)));
		flyters.add(new Flyter(projectiles, new FPoint(x, y)));
		flyters.add(new Flyter(projectiles, new FPoint(x, y)));
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		for(Flyter flyter : flyters) {
			flyter.draw(canvas);
		}
		for(Projectile projectile : projectiles) {
			projectile.draw(canvas);
		}
		invalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
	    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
	    this.setMeasuredDimension(parentWidth, parentHeight);
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}
