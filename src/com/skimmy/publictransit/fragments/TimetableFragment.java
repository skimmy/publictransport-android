package com.skimmy.publictransit.fragments;

import java.util.ArrayList;
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

	PositionedItemAdapter mAdapter = null;

	public TimetableFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<GeoPositionedItem> items = new ArrayList<GeoPositionedItem>();// this.getTimetableItmes();
		mAdapter = new PositionedItemAdapter(getActivity(),
				R.layout.timetable_list_item, items);
		setListAdapter(mAdapter);
		this.getListView().setOnItemClickListener(this);
		(new ItemsDownloader(0)).execute();
	}

	private class ItemsDownloader extends
			AsyncTask<Void, Void, List<GeoPositionedItem>> {

		public ItemsDownloader(int itemType) {
			super();
		}

		@Override
		protected List<GeoPositionedItem> doInBackground(Void... params) {
			List<GeoPositionedItem> results = RemoteServiceProxy
					.getTestStops(new GeoPointWithAccuracy(48.8, 2.29, 5000000));
			for (GeoPositionedItem p : results) {
				Log.d("RemoteTest", "(" + p.getLat() + "," + p.getLon() + ") "
						+ p.getAccuracy());
			}
			return results;
		}

		@Override
		protected void onPostExecute(List<GeoPositionedItem> result) {
			super.onPostExecute(result);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TabActivity tabAct = (TabActivity) this.getSherlockActivity();
		tabAct.showPositionedItemOnMap(this.mAdapter.getItem(arg2));
	}
}
