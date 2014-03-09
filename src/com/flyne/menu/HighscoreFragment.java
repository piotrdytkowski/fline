package com.flyne.menu;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.flyne.R;

public class HighscoreFragment extends ListFragment {

	private ArrayAdapter<String> aa;
	private List<String> todoItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		todoItems = new ArrayList<String>();
		todoItems.add("1. John 20000");
		todoItems.add("2. John 20000");
		todoItems.add("3. John 20000");
		todoItems.add("4. John 20000");
		todoItems.add("5. John 20000");
		todoItems.add("6. John 20000");
		todoItems.add("7. John 20000");
		todoItems.add("8. John 20000");
		todoItems.add("9. John 20000");
		todoItems.add("10. John 20000");
		
		aa = new ArrayAdapter<String>(container.getContext(), R.layout.hi_score_item, todoItems);
		this.setListAdapter(aa);
		return view;
	}
	
}
