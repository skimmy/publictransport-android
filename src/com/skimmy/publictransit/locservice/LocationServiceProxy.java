package com.skimmy.publictransit.locservice;

import android.location.Location;

/**
 * {@link LocationServiceProxy} is the class used to buffer, process and present
 * location data gathered by the background service. This class is responsible
 * for maintaining basic information like, for example, last location, actual
 * speed. The class should also trigger local computation (using worker threads)
 * in order to locally elaborate the gathered data and it is also responsible
 * for the calls to remote services. 
 * This class is also the enter point for listeners on new locations, 
 * 
 * 
 * @author Michele Schimd
 * 
 */
public class LocationServiceProxy {
	public static Location lastLocation = null;
	
	
	
}
