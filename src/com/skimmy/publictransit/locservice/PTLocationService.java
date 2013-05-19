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

	public static final String APP_SUBDIR_NAME = "PublicTransport/";
	public static final String POSITIONS_FILE_NAME = "pos.dat";

	private File posFile = null;
	private LocationManager locationManger = null;
	private PTLocationListener locationListener = null;

	private File createOrGetPositionsFile() {
		File ptSubDir = null;
		try {
			ptSubDir = AndroidFileHelper.getOrCreateDirectory(APP_SUBDIR_NAME);
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
		this.locationManger = this.getLocationManager();
		this.locationListener = new PTLocationListener(this.posFile);
		this.locationManger.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER,
				PTParameters.LOCATION_MINIMUM_UPDATE_TIME,
				PTParameters.LOCATION_MINIMUM_UPDATE_DISTANCE,
				this.locationListener);
		this.locationManger.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				PTParameters.LOCATION_MINIMUM_UPDATE_TIME,
				PTParameters.LOCATION_MINIMUM_UPDATE_DISTANCE,
				this.locationListener);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
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
	}

}
