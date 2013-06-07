package com.skimmy.publictransit;

public interface LocationServiceManager {
	
	public boolean startLocationService();	
	public boolean stopLocationService();	
	public boolean isServiceRunning();
	
}
