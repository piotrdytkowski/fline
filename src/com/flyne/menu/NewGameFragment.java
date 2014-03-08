package com.flyne.menu;

import com.flyne.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NewGameFragment extends Fragment {

	private MenuActionListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.new_game_fragment, container, false);
		
		Button infinity = (Button)view.findViewById(R.id.infinityButton);
		attachInfinityListener(infinity);
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (MenuActionListener)activity;
	}

	private void attachInfinityListener(Button newGame) {
		newGame.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.onInfinitySelected();
			}
		});
	}
}
