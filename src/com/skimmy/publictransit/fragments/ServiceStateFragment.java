package com.skimmy.publictransit.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skimmy.publictransit.LocationServiceManager;
import com.skimmy.publictransit.R;

public class ServiceStateFragment extends Fragment {

	private LocationServiceManager serviceManager;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button startServiceButton = (Button) getActivity().findViewById(
				R.id.service_start_button);
		Button stopServiceButton = (Button) getActivity().findViewById(
				R.id.service_stop_button);
		
		final TextView serviceStateTextView = (TextView) getActivity().findViewById(R.id.serviceStateSummuryTextView);
		startServiceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("ServiceStateFragment", "Start Button Pressed");
				serviceManager.startLocationService();
				refreshServiceStatusTextView(serviceStateTextView);				
			}
		});
		stopServiceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("ServiceStateFragment", "Stop Button Pressed");
				serviceManager.stopLocationService();
				refreshServiceStatusTextView(serviceStateTextView);
			}
		});
		refreshServiceStatusTextView(serviceStateTextView);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_service_state, container,
				false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.serviceManager = (LocationServiceManager) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement LocationServiceManager");
		}
	}

	private void refreshServiceStatusTextView(TextView textView) {
		String status = "Stopped";
		textView.setTextColor(Color.RED);
		if (this.serviceManager.isServiceRunning()) {
			status = "Running";
			textView.setTextColor(Color.rgb(0, 102, 0));
		}
		textView.setText(status);
	}
}
