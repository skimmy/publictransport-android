package com.skimmy.publictransit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skimmy.publictransit.R;
import com.skimmy.publictransit.model.GeoPositionedItem;

public class PositionedItemAdapter extends ArrayAdapter<GeoPositionedItem> {
	
	private Context ctx;
	private List<GeoPositionedItem> items;

	public PositionedItemAdapter(Context ctx, int textViewResourceId,
			List<GeoPositionedItem> items) {
		super(ctx, textViewResourceId, items);
		this.ctx = ctx;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.timetable_list_item, null);
		}
		GeoPositionedItem pItem = this.items.get(position);
		if (pItem != null) {
			TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
			nameTextView.setText(pItem.getId());
			TextView descTextView = (TextView) v.findViewById(R.id.descTextView);
			descTextView.setText("(" + pItem.getLat() + "," + pItem.getLon() + ")");
			ImageView iconImageView = (ImageView) v.findViewById(R.id.iconImageView);
			iconImageView.setImageBitmap(pItem.getIcon());
		}
		return v;
	}

}
