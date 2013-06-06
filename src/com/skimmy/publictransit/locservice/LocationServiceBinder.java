package com.skimmy.publictransit.locservice;

import android.os.Binder;

public class LocationServiceBinder extends Binder {
	private PTLocationService service = null;
	
	public LocationServiceBinder(PTLocationService service) {
		this.service = service;
	}
	
	public PTLocationService getService() {
		return this.service;
	}
	
}
