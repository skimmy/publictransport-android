package com.skimmy.publictransit.model;

import android.graphics.Bitmap;

import com.skimmy.jgis.data.GeoPointWithAccuracy;

public class GeoPositionedItem extends GeoPointWithAccuracy {

	private String id;
	private String type;
	private Bitmap icon;
	
	public GeoPositionedItem(double lat, double lon, double acc) {
		super(lat, lon, acc);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

}
