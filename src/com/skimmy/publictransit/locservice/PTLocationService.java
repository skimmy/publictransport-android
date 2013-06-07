package com.skimmy.publictransit.locservice;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import com.skimmy.androidutillibrary.filesystem.AndroidFileHelper;
import com.skimmy.androidutillibrary.filesystem.exceptions.FilesystemMismatchException;
import com.skimmy.publictransit.conf.PTParameters;

public class PTLocationService extends Service {

	private File posFile = null;
	private PTLocationListener locationListener = null;
	private LocationServiceBinder binder = null;

	private LocationManager locationManger = null;

	private File createOrGetPositionsFile() {
		File ptSubDir = null;
		try {
			ptSubDir = AndroidFileHelper
					.getOrCreateDirectory(this, PTParameters.APP_DIRECTORY_NAME);
		} catch (FilesystemMismatchException e) {
			e.printStackTrace();
		}
		File positionsFile = new File(ptSubDir.getAbsolutePath(),
				PTParameters.LOCATION_POSITION_LOG_FILENAME);

		// TODO If file is null something wrong happened act properly
		// if the file does not exist...
		if (positionsFile != null && !positionsFile.exists()) {
			try {
				positionsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return positionsFile;
	}

	private LocationManager getLocationManager() {
		return (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i("PTLocationService", "onStart method called! Id: " + startId);
		this.posFile = this.createOrGetPositionsFile();

		// test the availability of Google Play Services
		if (GooglePlayLocationHelper.isGooglePlayServicesAvail(this)) {
			Log.i(this.getClass().getName(),
					"Using Google Play Services Location");
			this.locationListener = new PTPlayServicesLocationListener(posFile,
					this);

			// this.locationRequest = LocationRequest.create();
			// this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			// this.locationRequest.setInterval(PTParameters.UPDATE_INTERVAL);
			// this.locationRequest.setFastestInterval(PTParameters.FASTEST_UPDATE_INTERVAL);
			// this.locationClient = new LocationClient(this, playListener,
			// playListener);

		} else {
			// resort to the system location service
			Log.i(this.getClass().getName(), "Using System Location Services");
			this.locationListener = new PTLocationListener(this.posFile);
			this.locationManger = this.getLocationManager();
			this.locationManger.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER,
					PTParameters.UPDATE_INTERVAL,
					PTParameters.LOCATION_MINIMUM_UPDATE_DISTANCE,
					this.locationListener);
			this.locationManger.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, PTParameters.UPDATE_INTERVAL,
					PTParameters.LOCATION_MINIMUM_UPDATE_DISTANCE,
					this.locationListener);
		}
		Log.d(this.getClass().getName(), "Service Started");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		this.binder = new LocationServiceBinder(this);
		return this.binder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (this.locationListener instanceof PTPlayServicesLocationListener) {
			PTPlayServicesLocationListener temp = (PTPlayServicesLocationListener)this.locationListener;
			temp.disconnectClient();
		}
		if (this.locationManger != null) {
			this.locationManger.removeUpdates(this.locationListener);
		}
		Log.d(this.getClass().getName(), "Service Destroyed");
	}
}
