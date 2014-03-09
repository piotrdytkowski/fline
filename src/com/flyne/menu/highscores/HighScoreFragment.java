package com.flyne.menu.highscores;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.flyne.R;

public class HighScoreFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		HighScoreOpenHelper helper = new HighScoreOpenHelper(container.getContext());
		List<HighScoreEntry> highScores = helper.findAllHighScores();
		
		ArrayAdapter<HighScoreEntry> aa = new ArrayAdapter<HighScoreEntry>(container.getContext(), R.layout.hi_score_item, highScores);
		this.setListAdapter(aa);
		return view;
	}
	
}
