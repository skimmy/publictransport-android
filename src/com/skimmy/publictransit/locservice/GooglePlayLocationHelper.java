package com.skimmy.publictransit.locservice;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

public class GooglePlayLocationHelper {

	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	/**
	 * Returns whether or not the Google Play Services is available
	 * 
	 * @param ctx
	 *            the current context
	 * @return <code>true</code> if play services is available,
	 *         <code>false</code> otherwise
	 */
	public static boolean isGooglePlayServicesAvail(Context ctx) {
		int googleServicesAvailCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(ctx);
		return (googleServicesAvailCode == ConnectionResult.SUCCESS);
	}

	/**
	 * Returns the {@link ConnectionResult} code for the given context. This
	 * method should be used only when such return code is needed (just for
	 * query purpose use {@link #isGooglePlayServicesAvail(Context)}.
	 * 
	 * @param ctx
	 *            the current context
	 * @return the code is returned by
	 *         {@link ConnectionResult.isGooglePlayServicesAvailable} method
	 */
	public static int googlePlayServicesAvailableCode(Context ctx) {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
	}

	/**
	 * Returns a {@link Dialog} for the code passed. The returned dialog must be
	 * associated with an {@link Activity} passed as parameter to the method.
	 * 
	 * @param googleServicesAvailCode
	 *            the code as defined in {@link ConnectionResult}
	 * @param parentActivity
	 *            the activity for which the {@link Dialog} must be created
	 * @return a {@link Dialog} ready to be shown
	 */
	public static Dialog getPlayServicesDialogForCode(
			int googleServicesAvailCode, Activity parentActivity) {
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
				googleServicesAvailCode, parentActivity,
				CONNECTION_FAILURE_RESOLUTION_REQUEST);
		return errorDialog;
	}
}
