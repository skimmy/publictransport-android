package com.skimmy.publictransit.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListFragment;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.publictransit.R;
import com.skimmy.publictransit.TabActivity;
import com.skimmy.publictransit.adapters.PositionedItemAdapter;
import com.skimmy.publictransit.model.GeoPositionedItem;
import com.skimmy.publictransit.remote.RemoteServiceProxy;

public class TimetableFragment extends SherlockListFragment implements
		OnItemClickListener {

	private static final String BUNDLE_ITEMS_LIST_NAME = "DisplayItems";

	private PositionedItemAdapter mAdapter = null;
	private ItemsDownloader itemsDownalodTask = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<GeoPositionedItem> items = new ArrayList<GeoPositionedItem>();
		if (savedInstanceState != null) {
			GeoPositionedItem[] array = (GeoPositionedItem[]) savedInstanceState
					.getParcelableArray(BUNDLE_ITEMS_LIST_NAME);
			items = new ArrayList<GeoPositionedItem>(Arrays.asList(array));
			Log.i(this.getClass().getName(), "List loaded from saved instance");
		}
		mAdapter = new PositionedItemAdapter(getActivity(),
				R.layout.timetable_list_item, items);
		setListAdapter(mAdapter);
		this.getListView().setOnItemClickListener(this);
		this.itemsDownalodTask = new ItemsDownloader(0);
		this.itemsDownalodTask.execute();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (this.mAdapter != null && outState != null) {
			outState.putParcelableArray(BUNDLE_ITEMS_LIST_NAME,
					this.mAdapter.toArray());
		}
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onDestroy() {

		super.onDestroy();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TabActivity tabAct = (TabActivity) this.getSherlockActivity();
		tabAct.showPositionedItemOnMap(this.mAdapter.getItem(arg2));
	}

	// The ItemDownlaoder private (Handler) class
	private class ItemsDownloader extends
			AsyncTask<Void, Void, List<GeoPositionedItem>> {

		public ItemsDownloader(int itemType) {
			super();
		}

		@Override
		protected List<GeoPositionedItem> doInBackground(Void... params) {
			// TODO: ATTENTION fake call
//			List<GeoPositionedItem> results = RemoteServiceProxy
//					.getTestStops(new GeoPointWithAccuracy(48.8, 2.29, 5000000));
			List<GeoPositionedItem> results = RemoteServiceProxy
					.fakeTestStops(new GeoPointWithAccuracy(48.8, 2.29, 5000000));
			if (isCancelled()) {
				return null;
			}
			for (GeoPositionedItem p : results) {
				Log.d("RemoteTest", "(" + p.getLat() + "," + p.getLon() + ") "
						+ p.getAccuracy());
			}
			return results;
		}

		@Override
		protected void onPostExecute(List<GeoPositionedItem> result) {
			super.onPostExecute(result);
			if (result == null) {
				return;
			}
			mAdapter.clear();
			for (GeoPositionedItem p : result) {
				GeoPositionedItem pItem = new GeoPositionedItem(p.getLat(),
						p.getLon(), p.getAccuracy());
				pItem.setId(p.getId());
				pItem.setIcon(BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_traffic_light_small));
				mAdapter.add(pItem);
			}
		}

	}	
}
