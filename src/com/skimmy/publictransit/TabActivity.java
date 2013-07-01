package com.skimmy.publictransit;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.skimmy.publictransit.fragments.MapFragment;
import com.skimmy.publictransit.fragments.ServiceStateFragment;
import com.skimmy.publictransit.fragments.TimetableFragment;
import com.skimmy.publictransit.interfaces.LocationServiceManager;
import com.skimmy.publictransit.locservice.PTLocationService;
import com.skimmy.publictransit.model.GeoPositionedItem;

import com.skimmy.androidutillibrary.runtime.RuntimeInfoHelper;

public class TabActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener, LocationServiceManager {

	// identify tabs
	public static final int MAP_TAB = 0;
	public static final int TIMETABLE_TAB = 1;
	public static final int SERVICE_TAB = 2;

	// Bundle state keys
	private static final String BUNDLE_CURRENT_TAB_INDEX_NAME = "ForegroundTab";

	private ShareActionProvider mshareAction;

	// tab fragments
	private MapFragment mapFragment = null;
	private TimetableFragment timetableFragment = null;
	private ServiceStateFragment serviceFragment = null;

	private ActionBar.Tab[] tabsArray;

	private Fragment foregroundFragment = null;
	private int foregroundFragmentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setTheme(R.style.Theme_Pt_light);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab);
		ActionBar aBar = getSupportActionBar();
		aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		aBar.setIcon(R.drawable.ic_launcher);
		aBar.setTitle(R.string.app_name);
		this.addTabsToActionBar(aBar);
		this.foregroundFragmentIndex = MAP_TAB;

	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Object obj = savedInstanceState.get(BUNDLE_CURRENT_TAB_INDEX_NAME);
		if (obj != null) {
			this.setTab((Integer) obj);
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment nextFragment = null;

		// NOTE: To get more responsive activity creation, fragments
		// are created "on-demand" as the corresponding tab are selected
		if (tab.getPosition() == MAP_TAB) {
			if (this.mapFragment == null) {
				this.mapFragment = new MapFragment();
				ft.replace(android.R.id.content, this.mapFragment);
			}
			nextFragment = this.mapFragment;
			foregroundFragmentIndex = MAP_TAB;
		}

		if (tab.getPosition() == TIMETABLE_TAB) {
			if (this.timetableFragment == null) {
				this.timetableFragment = new TimetableFragment();
				ft.replace(android.R.id.content, this.timetableFragment);
			}
			nextFragment = this.timetableFragment;
			foregroundFragmentIndex = TIMETABLE_TAB;
		}

		if (tab.getPosition() == SERVICE_TAB) {
			if (this.serviceFragment == null) {
				this.serviceFragment = new ServiceStateFragment();
				ft.replace(android.R.id.content, this.serviceFragment);
			}
			nextFragment = this.serviceFragment;
			foregroundFragmentIndex = SERVICE_TAB;
		}
		if (nextFragment != null) {
			ft.attach(nextFragment);
		}
		this.foregroundFragment = nextFragment;
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (this.foregroundFragment != null) {
			ft.detach(this.foregroundFragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		this.mshareAction = new ShareActionProvider(this);
		this.mshareAction.setShareIntent(shareIntent);
		menu.add(R.string.action_share)
				.setIcon(R.drawable.ic_action_share)
				.setActionProvider(mshareAction)
				.setEnabled(true)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(BUNDLE_CURRENT_TAB_INDEX_NAME,
				this.foregroundFragmentIndex);
		super.onSaveInstanceState(outState);
	}

	public void setTab(int tab) {
		this.getSupportActionBar().selectTab(this.tabsArray[tab]);
	}

	public void showPositionedItemOnMap(GeoPositionedItem item) {
		this.getSupportActionBar().selectTab(this.tabsArray[MAP_TAB]);
		this.mapFragment.animateToPositionedItem(item);
	}

	private void addTabsToActionBar(ActionBar aBar) {
		this.tabsArray = new ActionBar.Tab[3];

		// The map (default) tab
		ActionBar.Tab mapTab = aBar.newTab().setIcon(R.drawable.ic_action_map)
				.setTabListener(this);

		// The timetables tab
		ActionBar.Tab timetableTab = aBar.newTab()
				.setIcon(R.drawable.ic_action_timetable).setTabListener(this);

		// The service tab
		ActionBar.Tab serviceTab = aBar.newTab()
				.setIcon(R.drawable.ic_action_location_service)
				.setTabListener(this);

		// add all tabs
		aBar.addTab(mapTab, 0);
		aBar.addTab(timetableTab, 1);
		aBar.addTab(serviceTab, 2);

		// maintain references to all tabs
		this.tabsArray[MAP_TAB] = mapTab;
		this.tabsArray[TIMETABLE_TAB] = timetableTab;
		this.tabsArray[SERVICE_TAB] = serviceTab;
	}

	/* Implementation of the LocationServiceManager interface */

	@Override
	public boolean startLocationService() {
		Intent locationServiceIntent = new Intent(this, PTLocationService.class);
		ComponentName cName = startService(locationServiceIntent);
		return (cName != null);
	}

	@Override
	public boolean stopLocationService() {
		Log.i("ServiceStarterActivity", "Stop Button Pressed");
		Intent locationServiceIntent = new Intent(this, PTLocationService.class);
		stopService(locationServiceIntent);
		return false;
	}

	@Override
	public boolean isServiceRunning() {
		return RuntimeInfoHelper.isServiceRunning(this,
				PTLocationService.class.getName());
	}
}
