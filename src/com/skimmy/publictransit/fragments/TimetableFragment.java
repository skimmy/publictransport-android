package com.skimmy.publictransit.fragments;

import com.actionbarsherlock.app.SherlockListFragment;

import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TimetableFragment extends SherlockListFragment {

	public TimetableFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] values = { "A", "C", "F" };
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(mAdapter);
	}
}
