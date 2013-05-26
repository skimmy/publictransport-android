package com.skimmy.publictransit;

import com.skimmy.publictransit.locservice.GooglePlayLocationHelper;
import com.skimmy.publictransit.locservice.PTLocationService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ServiceStarterActivity extends Activity {

	private boolean playServicesAvail;

	private void servicesInit() {
		this.playServicesAvail = GooglePlayLocationHelper
				.isGooglePlayServicesAvail(this);
		if (this.playServicesAvail) {
			Log.i(this.getClass().getName(), "Google Play Services Available");
			// sue the google play services location update
						
		} else {
			Log.w(this.getClass().getName(),
					"Google Play Services NOT available");
			// use the standard android location service
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// pre layout initializations
		this.servicesInit();
		
		setContentView(R.layout.activity_service_starter);

		// setting the buttons listeners
		Button serviceStartButton = (Button) this
				.findViewById(R.id.service_start_button);
		Button serviceStopButton = (Button) this
				.findViewById(R.id.service_stop_button);

		serviceStartButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startLocationService();
			}
		});

		serviceStopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopLocationService();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.service_starter, menu);
		return true;
	}

	private boolean startLocationService() {
		Log.i("ServiceStarterActivity", "Start Button Pressed");
		Intent locationServiceIntent = new Intent(this, PTLocationService.class);
		startService(locationServiceIntent);
		return false;
	}

	private boolean stopLocationService() {
		Log.i("ServiceStarterActivity", "Stop Button Pressed");
		Intent locationServiceIntent = new Intent(this, PTLocationService.class);
		stopService(locationServiceIntent);
		return false;
	}

}
