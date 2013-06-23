package com.skimmy.publictransit.fragments;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.gms.internal.eq.e;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.publictransit.TabActivity;
import com.skimmy.publictransit.R;
import com.skimmy.publictransit.adapters.TimetableListAdapter;
import com.skimmy.publictransit.model.TimetableItem;
import com.skimmy.publictransit.remote.RemoteServiceProxy;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class TimetableFragment extends SherlockListFragment implements OnItemClickListener {
	
	TimetableListAdapter mAdapter = null;

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
		mAdapter = new TimetableListAdapter(getActivity(),
				R.layout.timetable_list_item, items);
		setListAdapter(mAdapter);
		this.getListView().setOnItemClickListener(this);
		(new ItemsDownloader(0)).execute();		
	}
	
	private class ItemsDownloader extends AsyncTask<Void, Void, List<GeoPointWithAccuracy>> {
		
		public ItemsDownloader(int itemType) {
			super();
		}

		@Override
		protected List<GeoPointWithAccuracy> doInBackground(Void... params) {
			List<GeoPointWithAccuracy> results = RemoteServiceProxy.getTestStops(new GeoPointWithAccuracy(0, 0, 0));
			for (GeoPointWithAccuracy p : results) {
				Log.d("RemoteTest", "(" + p.getLat() + "," + p.getLon() + ") " + p.getAccuracy());
			}
			return results;			
		}
		
		@Override
		protected void onPostExecute(List<GeoPointWithAccuracy> result) {
			super.onPostExecute(result);
			mAdapter.clear();
			for (GeoPointWithAccuracy p : result) {
				String posString = "(" + p.getLat() + "," + p.getLon() + ")";
				String accString = "" + p.getAccuracy();
				TimetableItem tti = new TimetableItem(posString,
						accString, BitmapFactory.decodeResource(
								getResources(),
								R.drawable.ic_traffic_light_small));
				mAdapter.add(tti);
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TabActivity tabAct = (TabActivity)this.getSherlockActivity();
		tabAct.setTab(TabActivity.MAP_TAB);
	}
}
