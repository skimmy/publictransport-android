package com.skimmy.publictransit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This {@link Activity} contains the map fragment.
 * 
 * @author Michele Schimd
 * 
 */
public class MapActivity extends FragmentActivity {

	private GoogleMap gMap;

	private void createTestOverlay() {
		if (this.gMap != null) {
			MarkerOptions opts = new MarkerOptions();
			opts.position(new LatLng(0, 0));
			opts.title("Hello Map (0,0)");

			this.gMap.addMarker(opts);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		this.gMap = mapFragment.getMap();
		this.createTestOverlay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}	

}
