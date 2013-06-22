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

public class RemoteServiceProxy {

	private static Pttest service;

	static {
		Pttest.Builder builder = new Pttest.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		service = builder.build();
	}

	public static List<GeoPointWithAccuracy> getTestStops(
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

		List<GeoPointWithAccuracy> stops = new LinkedList<GeoPointWithAccuracy>();
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
				stops.add(new GeoPointWithAccuracy(lat.doubleValue(), lon
						.doubleValue(), acc.doubleValue()));
			}
		}
		return stops;
	}

}
