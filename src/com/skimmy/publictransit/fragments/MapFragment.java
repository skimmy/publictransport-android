package com.skimmy.publictransit.fragments;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.skimmy.publictransit.conf.PTParameters;
import com.skimmy.publictransit.model.GeoPositionedItem;

public class MapFragment extends SupportMapFragment {
	
	private GoogleMap gMap;
	private TileOverlay osmTiles = null;

	private void setMapTiles(int tileType) {
		switch (tileType) {
		case PTParameters.MAP_TILE_GOOGLE: {
			this.gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			if (osmTiles != null) {
				this.osmTiles.remove();
				this.osmTiles = null;
			}
			break;
		}
		case PTParameters.MAP_TILE_OPENSTREETMAP: {
			TileOverlayOptions tOpts = new TileOverlayOptions().tileProvider(new OSMTileProvider());
			this.gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			this.osmTiles = this.gMap.addTileOverlay(tOpts);
			break;
		}
		
		default:
			break;
	
		}
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
		setMapTiles(PTParameters.MAP_TILE_GOOGLE);
	}
	
	public void animateToPositionedItem(GeoPositionedItem pItem) {
		MarkerOptions mOpts = new MarkerOptions();
		LatLng p = new LatLng(pItem.getLat(), pItem.getLon());
		mOpts.position(p).title(pItem.getId());
		CameraUpdate cUpdate = CameraUpdateFactory.newLatLngZoom(p, 17);
		gMap.animateCamera(cUpdate);
		gMap.addMarker(mOpts);
		
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
