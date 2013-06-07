package com.skimmy.publictransit.locservice;

import java.io.File;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.skimmy.publictransit.conf.PTParameters;

public class PTPlayServicesLocationListener extends PTLocationListener
		implements GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private Context appContext;
	private LocationRequest mRequest;
	private LocationClient mClient;
	private boolean updatingLocation;

	public PTPlayServicesLocationListener(File posFile, Context ctx) {
		super(posFile);
		this.appContext = ctx;
		this.mRequest = LocationRequest.create();
		// TODO: Change based on the power status (advanced feature)
		this.mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		this.mRequest.setInterval(PTParameters.UPDATE_INTERVAL);
		this.mRequest
				.setFastestInterval(PTParameters.FASTEST_UPDATE_INTERVAL);

		this.mClient = new LocationClient(this.appContext, this, this);
		this.conncetClient();
		this.startRequestUpdate();
	}

	public void startRequestUpdate() {
		this.updatingLocation = true;
		if (this.mClient.isConnected()) {
			this.mClient.requestLocationUpdates(this.mRequest, this);
		}
	}

	public void stopUpdateLocation() {
		if (this.mClient.isConnected()) {
			this.mClient.removeLocationUpdates(this);
		}
		this.updatingLocation = false;
	}
	
	public void conncetClient() {
		if (!this.mClient.isConnected()) {
			this.mClient.connect();
		}
	}
	
	public void disconnectClient() {
		if (this.mClient.isConnected() && this.updatingLocation) {
			this.mClient.removeLocationUpdates(this);
			this.mClient.disconnect();
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		Log.d(this.getClass().getName(), "New Location from google play services");
		super.onLocationChanged(location);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.d(this.getClass().getName(), "Google Play Services Connection Failed!");
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.d(this.getClass().getName(), "Google Play Services Connected!");
		if (this.updatingLocation) {
			this.mClient.requestLocationUpdates(this.mRequest, this);
		}
	}

	@Override
	public void onDisconnected() {
		Log.d(this.getClass().getName(), "Google Play Services Disconnected!");
	}

}
