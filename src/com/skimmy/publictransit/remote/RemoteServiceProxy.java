package com.skimmy.publictransit.remote;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.pttest.Pttest;
import com.google.api.services.pttest.model.EndpointswsMessagesGeoPointWithAccuracyMessage;
import com.google.api.services.pttest.model.EndpointswsMessagesPositionedItemListMessage;
import com.google.api.services.pttest.model.EndpointswsMessagesPositionedItemMessage;
import com.skimmy.jgis.data.GeoPointWithAccuracy;
import com.skimmy.publictransit.model.GeoPositionedItem;

public class RemoteServiceProxy {

	private static Pttest service;

	static {
		Pttest.Builder builder = new Pttest.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		service = builder.build();
	}
	
	public static List<GeoPositionedItem> fakeTestStops(
			GeoPointWithAccuracy near) {
		List<GeoPositionedItem> stops = new LinkedList<GeoPositionedItem>();
		GeoPositionedItem tmp = new GeoPositionedItem(47.621800, -122.350326, 0);
		tmp.setId("Seattle");		
		tmp.setType(GeoPositionedItem.TYPE_STOP);
		stops.add(tmp);
		tmp = new GeoPositionedItem(55.5008, -0.1245, 0);
		tmp.setId("BigBen");
		tmp.setType(GeoPositionedItem.TYPE_STOP);
		stops.add(tmp);
		tmp = new GeoPositionedItem(48.8583, 2.2943, 0);
		tmp.setId("Eiffel");
		tmp.setType(GeoPositionedItem.TYPE_STOP);
		stops.add(tmp);
		tmp = new GeoPositionedItem(52.5162, 13.3778 , 0);
		tmp.setId("Brandenburger");
		tmp.setType(GeoPositionedItem.TYPE_STOP);
		stops.add(tmp);		
		return stops;
	}

	public static List<GeoPositionedItem> getTestStops(
			GeoPointWithAccuracy near) {

		EndpointswsMessagesGeoPointWithAccuracyMessage msg = new EndpointswsMessagesGeoPointWithAccuracyMessage();
		msg.setLatitude(near.getLat());
		msg.setLongitude(near.getLon());
		msg.setAccuracy(near.getAccuracy());
		EndpointswsMessagesPositionedItemListMessage itemList = null;
		try {
			itemList = service.getstops(msg).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<GeoPositionedItem> stops = new LinkedList<GeoPositionedItem>();
		if (itemList != null) {
			List<EndpointswsMessagesPositionedItemMessage> msgList = itemList
					.getItems();
			for (EndpointswsMessagesPositionedItemMessage m : msgList) {
				Double lat = m.getPosition().getLatitude();
				Double lon = m.getPosition().getLongitude();
				Double acc = m.getPosition().getAccuracy();
				if (acc == null) {
					acc = Double.valueOf(0);
				}
				GeoPositionedItem newStop = new GeoPositionedItem(lat.doubleValue(), lon
						.doubleValue(), acc.doubleValue());
				newStop.setId(m.getItemId());
				stops.add(newStop);
			}
		}
		return stops;
	}

}
