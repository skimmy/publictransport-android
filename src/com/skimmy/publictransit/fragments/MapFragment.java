package com.skimmy.publictransit.fragments;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.publictransit.remote.RemoteServiceProxy;

public class MapFragment extends SupportMapFragment {
	
	private GoogleMap gMap;

	public MapFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.gMap = getMap();
		
		TileOverlayOptions tOpts = new TileOverlayOptions().tileProvider(new OSMTileProvider());
//		this.gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
//		this.gMap.setTrafficEnabled(true);
//		this.gMap.addTileOverlay(tOpts);

	}
	
	private class OSMTileProvider extends UrlTileProvider {
		
		public OSMTileProvider() {
			super(256,256);
		}

		@Override
		public URL getTileUrl(int x, int y, int zoom) {
			try {
				return new URL("http://a.tile.openstreetmap.org/" + zoom + "/" + x + "/" + y + ".png");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}

}
