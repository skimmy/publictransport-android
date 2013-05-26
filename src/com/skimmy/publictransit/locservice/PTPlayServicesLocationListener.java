package com.skimmy.publictransit.locservice;

import java.io.File;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

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
		this.mRequest.setInterval(PTLocationService.UPDATE_INTERVAL);
		this.mRequest
				.setFastestInterval(PTLocationService.FASTEST_UPDATE_INTERVAL);

		this.mClient = new LocationClient(this.appContext, this, this);
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
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		if (this.updatingLocation) {
			this.mClient.removeLocationUpdates(this);
		}
	}

	@Override
	public void onDisconnected() {
		
	}

}
