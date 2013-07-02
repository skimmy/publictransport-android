package com.skimmy.publictransit.locservice;

import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.skimmy.androidutillibrary.filesystem.SharedPreferencesHelper;
import com.skimmy.publictransit.conf.PTParameters;

public class GeofenceHelper {

	public static final String KEY_ID = ".id";
	public static final String KEY_LATITUDE = ".latitude";
	public static final String KEY_LONGITUDE = ".longitude";
	public static final String KEY_RADIUS = ".radius";
	public static final String KEY_TRANSITION_TYPE = ".transitiontype";

	public static final String SHARED_PREFERENCES_NAME = "STGeofences";

	public static boolean saveGeofenceToSharedPreferences(
			SimpleGeofence sGeofence, Context context) {
		boolean result = true;
		String prefix = PTParameters.APP_PACKAGE_NAME + "."
				+ SHARED_PREFERENCES_NAME + "." + sGeofence.getGeofenceId();

		// save geofence on Shared Preferences
		result &= SharedPreferencesHelper.saveString(prefix + KEY_ID,
				sGeofence.getGeofenceId(), SHARED_PREFERENCES_NAME, context);
		result &= SharedPreferencesHelper.saveFlaot(prefix + KEY_LATITUDE,
				(float) sGeofence.getLatitude(), SHARED_PREFERENCES_NAME,
				context);
		result &= SharedPreferencesHelper.saveFlaot(prefix + KEY_LONGITUDE,
				(float) sGeofence.getLongitude(), SHARED_PREFERENCES_NAME,
				context);
		result &= SharedPreferencesHelper.saveFlaot(prefix + KEY_RADIUS,
				sGeofence.getRadius(), SHARED_PREFERENCES_NAME, context);
		result &= SharedPreferencesHelper.saveInt(prefix + KEY_TRANSITION_TYPE,
				sGeofence.transitionType, SHARED_PREFERENCES_NAME, context);
		return result;
	}

	public static class SimpleGeofence {
		private final String geofenceId;
		private final double latitude;
		private final double longitude;
		private final float radius;
		private int transitionType;
		private long expirationTime;

		public SimpleGeofence(String id, double lat, double lon, float rad) {
			this.geofenceId = id;
			this.latitude = lat;
			this.longitude = lon;
			this.radius = rad;
			this.transitionType = Geofence.GEOFENCE_TRANSITION_ENTER;
			this.expirationTime = PTParameters.GEOFENCE_DEFAULT_EXPIRATION_MSECS;
		}

		public String getGeofenceId() {
			return geofenceId;
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public float getRadius() {
			return radius;
		}

		public Geofence toGeofence() {
			return (new Geofence.Builder()
					.setRequestId(this.geofenceId)
					.setCircularRegion(this.latitude, this.longitude,
							this.radius)
					.setTransitionTypes(this.transitionType)
					.setExpirationDuration(this.expirationTime)).build();
		}
	}
}
