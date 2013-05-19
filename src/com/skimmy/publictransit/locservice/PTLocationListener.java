package com.skimmy.publictransit.locservice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.skimmy.androidutillibrary.time.DateAndTimeHelper;

public class PTLocationListener implements LocationListener {

	private File positionsFile;

	public PTLocationListener(File posFile) {
		this.positionsFile = posFile;
	}

	private void logPosition(Location location) {
		String logString = DateAndTimeHelper.getCurrentUTCTimestampString()
				+ " " + location.getLatitude() + " " + location.getLongitude()
				+ " " + location.getAccuracy() + " " + location.getProvider()
				+ "\n";
		FileWriter fw;
		try {
			// TODO Some "Thread-Safe" buffered method may be more appropriate
			// form performance reasons
			fw = new FileWriter(this.positionsFile, true);
			fw.append(logString);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i("LocationListenr", location.toString());
		if (location != null) {
			this.logPosition(location);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
