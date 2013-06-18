package com.skimmy.publictransit.fragments;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.gms.internal.eq.e;
import com.skimmy.publictransit.R;
import com.skimmy.publictransit.adapters.TimetableListAdapter;
import com.skimmy.publictransit.model.TimetableItem;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class TimetableFragment extends SherlockListFragment {

	private List<TimetableItem> getTimetableItmes() {
		List<TimetableItem> list = new ArrayList<TimetableItem>();
		for (int i = 0; i < 3; i++) {
			TimetableItem tti = new TimetableItem("Name " + i,
					"Short description #" + i, BitmapFactory.decodeResource(
							this.getResources(),
							R.drawable.ic_traffic_light_small));
			list.add(tti);
		}
		return list;
	}

	public TimetableFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<TimetableItem> items = this.getTimetableItmes();
		TimetableListAdapter mAdapter = new TimetableListAdapter(getActivity(),
				R.layout.timetable_list_item, items);
		setListAdapter(mAdapter);
	}
}
