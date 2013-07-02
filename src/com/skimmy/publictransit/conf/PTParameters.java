package com.skimmy.publictransit.conf;

import java.io.File;

import com.skimmy.androidutillibrary.time.TimeConstants;

public class PTParameters {
	/*
	 * General App Settings
	 */
	public static String APP_DIRECTORY_NAME = "PublicTransport"
			+ File.separatorChar;
	public static String APP_PACKAGE_NAME = "com.skimmy.publictransit";

	/*
	 * Location service parameters
	 */
	public static long LOCATION_MINIMUM_UPDATE_DISTANCE = 10;
	public static String LOCATION_POSITION_LOG_FILENAME = "logpos.txt";

	public static final int UPDATE_INTERVAL_IN_SECONDS = 7;
	public static final int UPDATE_INTERVAL = UPDATE_INTERVAL_IN_SECONDS
			* TimeConstants.MILLISECONDS_PER_SECOND;
	public static final int FASTEST_INTERVAL_IN_SECONDS = 3;
	public static final int FASTEST_UPDATE_INTERVAL = FASTEST_INTERVAL_IN_SECONDS
			* TimeConstants.MILLISECONDS_PER_SECOND;

	public static final long GEOFENCE_DEFAULT_EXPIRATION_HOURS = 12;
	public static final long GEOFENCE_DEFAULT_EXPIRATION_MSECS = GEOFENCE_DEFAULT_EXPIRATION_HOURS
			* TimeConstants.SECONDS_PER_HOUR
			* TimeConstants.MILLISECONDS_PER_SECOND;

	/*
	 * Map Parameters
	 */
	// CONSTANTS
	public static final int MAP_TILE_GOOGLE = 1;
	public static final int MAP_TILE_OPENSTREETMAP = 2;
}
