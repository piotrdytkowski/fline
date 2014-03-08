package com.flyne.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import com.flyne.GameActivity;
import com.flyne.R;

public class MenuActivity extends FragmentActivity implements MenuActionListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main_menu);

		Fragment mainMenuFragment = new MainMenuFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, mainMenuFragment);
		transaction.commit();
	}

	@Override
	public void onMenuAction(MenuAction action) {
		switch(action) {
		case NEW_GAME:
			onNewGameSelected();
			break;
		case OPTIONS:
			onOptionsSelected();
			break;
		case EXIT:
			onExitSelected();
			break;
			//NEW GAME MENU
		case INFINITY:
			onInfinitySelected();
			break;
		case CAMPAIGN:
			onCampaignSelected();
			break;
			
		}
	}
	
	private void onCampaignSelected() {
		// TODO Auto-generated method stub
		
	}

	public void onNewGameSelected() {
		Fragment newGameFragment = new NewGameFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentContainer, newGameFragment);
		transaction.commit();
	}

	public void onOptionsSelected() {
		// TODO Auto-generated method stub
		
	}

	public void onExitSelected() {
		// TODO Auto-generated method stub
		
	}
	
	public void onInfinitySelected() {
		Intent intent = new Intent(MenuActivity.this, GameActivity.class);
		MenuActivity.this.startActivity(intent);
		MenuActivity.this.finish();
	}
	
}
