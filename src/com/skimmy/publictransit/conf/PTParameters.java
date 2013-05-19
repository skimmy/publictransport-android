package com.skimmy.publictransit.conf;

import java.io.File;

public class PTParameters {
	/*
	 * General App Settings
	 */
	public static String APP_DIRECTORY_NAME = "PublicTransport"
			+ File.separatorChar;

	/*
	 * Location service parameters
	 */
	public static long LOCATION_MINIMUM_UPDATE_TIME = 500;
	public static long LOCATION_MINIMUM_UPDATE_DISTANCE = 10;
	public static String LOCATION_POSITION_LOG_FILENAME = "logpos.txt";
}
