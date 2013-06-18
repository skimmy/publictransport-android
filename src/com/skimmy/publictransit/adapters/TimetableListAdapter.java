package com.skimmy.publictransit.adapters;

import java.util.List;

import com.skimmy.publictransit.R;
import com.skimmy.publictransit.model.TimetableItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TimetableListAdapter extends ArrayAdapter<TimetableItem> {
	
	private Context ctx;
	
	private List<TimetableItem> items;

	public TimetableListAdapter(Context ctx, int textViewResourceId,
			List<TimetableItem> items) {
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
		TimetableItem tti = this.items.get(position);
		if (tti != null) {
			TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
			nameTextView.setText(tti.getName());
			TextView descTextView = (TextView) v.findViewById(R.id.descTextView);
			descTextView.setText(tti.getShortDescription());
			ImageView iconImageView = (ImageView) v.findViewById(R.id.iconImageView);
			iconImageView.setImageBitmap(tti.getIcon());
		}
		return v;
	}
}
