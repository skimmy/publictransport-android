package com.skimmy.publictransit.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.skimmy.jgis.data.GeoPointWithAccuracy;

public class GeoPositionedItem extends GeoPointWithAccuracy implements Parcelable {
	
	public static final String TYPE_STOP = "stop";

	private String id;
	private String type;
	private Bitmap icon;
	
	public GeoPositionedItem(Parcel in) {
		super(0,0,0);
		this.id = in.readString();
		this.setLat(in.readDouble());
		this.setLon(in.readDouble());
		this.setAccuracy(in.readDouble());
		this.setType(in.readString());
		this.icon = Bitmap.CREATOR.createFromParcel(in);
	}
	
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeDouble(this.getLat());
		dest.writeDouble(this.getLon());
		dest.writeDouble(this.getAccuracy());
		dest.writeString(this.type);
		dest.writeParcelable(this.icon, flags);
	}
	
	public static final Parcelable.Creator<GeoPositionedItem> CREATOR =			
			new Parcelable.Creator<GeoPositionedItem>() {
		public GeoPositionedItem createFromParcel(Parcel in) {
			return new GeoPositionedItem(in);
		}

		@Override
		public GeoPositionedItem[] newArray(int size) {
			return new GeoPositionedItem[size];
		}
	};	

}
