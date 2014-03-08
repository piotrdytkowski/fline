package com.flyne.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flyne.R;

public class MainMenuFragment extends Fragment {
	
	private MenuActionListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.main_menu_fragment, container, false);
		
		Button newGame = (Button)view.findViewById(R.id.newGameButton);
		attachNewGameListener(newGame);
		Button options = (Button)view.findViewById(R.id.optionsButton);
		attachOptionsListener(options);
		Button exitGame = (Button)view.findViewById(R.id.exitGameButton);
		attachExitGameListener(exitGame);
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (MenuActionListener)activity;
	}

	private void attachNewGameListener(Button newGame) {
		newGame.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.onNewGameSelected();
			}
		});
	}
	
	private void attachOptionsListener(Button options) {
		options.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.onOptionsSelected();
			}
		});
	}
	
	private void attachExitGameListener(Button exitGame) {
		exitGame.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.onExitSelected();
			}
		});
	}

}
