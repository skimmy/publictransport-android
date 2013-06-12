package com.skimmy.publictransit;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.publictransit.locservice.LocationServiceProxy;

/**
 * This {@link Activity} contains the map fragment.
 * 
 * @author Michele Schimd
 * 
 */
public class MapActivity extends FragmentActivity {

	private GoogleMap gMap;
	
	private Handler lastLocationHandler;

	private static int BLUE_POINT_STROKE = Color.BLUE;
	private static int BLUE_POINT_FILL = Color.argb(45, 0, 0, 255);
	private static float DEFAULT_MOVE_ZOOM = 16;
	private static float POINT_STROKE_WIDTH = 2;

	public void addGeoPointWithAccuracyToMap(GeoPointWithAccuracy geoPt,
			boolean moveTo) {
		if (this.gMap == null) {
			return;
		}
		LatLng p = new LatLng(geoPt.getLat(), geoPt.getLon());
		double acc = geoPt.getAccuracy();
		CircleOptions cOpts = new CircleOptions();
		cOpts.center(p).radius(acc).strokeWidth(POINT_STROKE_WIDTH)
				.strokeColor(BLUE_POINT_STROKE).fillColor(BLUE_POINT_FILL);
		this.gMap.addCircle(cOpts);
		if (moveTo) {
			this.gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p,
					DEFAULT_MOVE_ZOOM));
		}

	}

	private void createTestOverlay() {
		if (this.gMap != null) {
			// this.gMap.setTrafficEnabled(true);
			LatLng p = new LatLng(51.48362, 0);
			LatLng me = new LatLng(51.4821, -0.00123);

			this.gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 16));

			MarkerOptions opts = new MarkerOptions();
			opts.position(p);
			opts.title("Traffic Light");
			opts.snippet("Dammend stuck traffic!!");
			opts.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_traffic_light_small));
			opts.anchor(0.5f, 0.5f);

			CircleOptions cOpts = new CircleOptions();
			cOpts.center(p).radius(30);
			cOpts.strokeWidth(2.0f);
			cOpts.strokeColor(Color.RED);
			cOpts.fillColor(Color.argb(45, 255, 0, 0));
			this.gMap.addMarker(opts);
			this.gMap.addCircle(cOpts);

			opts = new MarkerOptions();
			opts.position(me);
			opts.title("You");
			opts.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

			PolylineOptions polyOpts = new PolylineOptions();
			polyOpts.add(p).add(me).color(Color.MAGENTA).width(2.0f);

			this.gMap.addMarker(opts);
			this.gMap.addPolyline(polyOpts);

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		this.gMap = mapFragment.getMap();
		// this.createTestOverlay();
		Location lastLoc = LocationServiceProxy.lastLocation;
		if (lastLoc != null) {
			this.addGeoPointWithAccuracyToMap(new GeoPointWithAccuracy(lastLoc.getLatitude(), lastLoc.getLongitude(),
					lastLoc.getAccuracy()), true);
		}
		this.lastLocationHandler = new Handler(Looper.getMainLooper()) {
			
		};
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
