package com.skimmy.publictransit.model;

import android.graphics.Bitmap;

public class TimetableItem {
	
	private String name;
	private String shortDescription;
	
	private Bitmap icon;

	public TimetableItem(String name, String desc, Bitmap icon) {
		this.name = name;
		this.shortDescription = desc;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

}
