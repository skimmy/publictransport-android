package com.skimmy.publictransit.locservice;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.app.Dialog;

public class GooglePlayLocationHelper {
	public static boolean isGooglePlayServicesAvail(Activity ctx) {
		int googleServicesAvailCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(ctx);
		if (googleServicesAvailCode == ConnectionResult.SUCCESS) {
			return true;
		} else {
			// TODO: Use Fragment to show Dialog
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					googleServicesAvailCode, ctx, 9000);
			if (errorDialog != null) {
				errorDialog.show();
			}
		}
		return false;
	}
}
