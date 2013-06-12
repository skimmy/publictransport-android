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
	private Location lastLocation;

	public PTLocationListener(File posFile) {
		this.positionsFile = posFile;
		this.lastLocation = null;
	}

	private void logPosition(Location location) {
		
		String distance = "-";
		String bearing = "-";
		String speed = "-";
		if (this.lastLocation != null) {
			distance = "" + this.lastLocation.distanceTo(location);
			bearing = "" + this.lastLocation.bearingTo(location);
			long difftime = (location.getTime() - this.lastLocation.getTime()) / 1000;
			float speedFloat = ((this.lastLocation.distanceTo(location)) / (float)difftime) * 3.6f;
			speed = "" + speedFloat;
		}
		String logString = DateAndTimeHelper.getCurrentUTCTimestampString()
				+ " " + location.getLatitude() + " " + location.getLongitude()
				+ " " + location.getAccuracy() + " " + location.getProvider()
				+ " " + distance + " " + bearing + " " + speed + "\n";
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
		Log.i("LocationListener", location.toString());
		if (location != null) {
			this.logPosition(location);
		}
		this.lastLocation = location;
		LocationServiceProxy.lastLocation = location;
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
