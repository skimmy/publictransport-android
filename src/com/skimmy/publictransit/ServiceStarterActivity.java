package com.skimmy.publictransit;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.skimmy.publictransit.fragments.ServiceStateFragment;
import com.skimmy.publictransit.interfaces.LocationServiceManager;
import com.skimmy.publictransit.locservice.PTLocationService;

/**
 * This {@link Activity} is used to show the state the location service and
 * its control UI (via {@link ServiceStateFragment}). In order to embedd the
 * mentioned fragment the activity must implement {@link LocationServiceManager}
 * 
 * @author Michele Schimd
 *
 */
public class ServiceStarterActivity extends FragmentActivity implements LocationServiceManager {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_service_starter);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.service_starter, menu);
		return true;
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
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (PTLocationService.class.getName().equals(serviceInfo.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_map: {
			Intent intent = new Intent(this, MapActivity.class);
			startActivity(intent);
		}
		default: {
			return super.onMenuItemSelected(featureId, item);
		}
		}
	}

}
