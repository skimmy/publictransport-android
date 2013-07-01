package com.skimmy.publictransit.fragments;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.skimmy.publictransit.R;
import com.skimmy.publictransit.interfaces.LocationServiceManager;
import com.skimmy.publictransit.locservice.LocationServiceProxy;

/**
 * This fragment contains the UI to control the location service, it must be
 * attached to an {@link Activity} that implements
 * {@link LocationServiceManager} interface otherwise a
 * {@link ClassCastException} is thrown
 * 
 * @author Michele Schimd
 * 
 */
public class ServiceStateFragment extends Fragment {

	private LocationServiceManager serviceManager;

	private Handler speedHandler;
	private TimerTask speedUpdateTimerTask;

	// this timer is used to schedule tasks that refresh UI
	private Timer refreshTimer;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("SF_LC", "onActivityCreated");
		super.onCreate(savedInstanceState);

		// obtain views
		Button startServiceButton = (Button) getActivity().findViewById(
				R.id.service_start_button);
		Button stopServiceButton = (Button) getActivity().findViewById(
				R.id.service_stop_button);
		final TextView serviceStateTextView = (TextView) getActivity()
				.findViewById(R.id.serviceStateSummuryTextView);
		
		try {
			this.serviceManager = (LocationServiceManager) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString()
					+ " must implement LocationServiceManager");
		}

		// register listeners
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

		// refresh the status message in the UI
		refreshServiceStatusTextView(serviceStateTextView);

		// create the handler for user speed update
		final TextView speedTextView = (TextView) getActivity().findViewById(
				R.id.userSpeedTextView);
		this.speedHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.obj != null) {
					speedTextView.setText(msg.obj.toString());
				}
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_service_state, container,
				false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		Activity activity = this.getActivity();
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//		
		Log.d("SF_LC", "onResume");
//		super.onAttach(activity);

		this.refreshTimer = new Timer();
		this.speedUpdateTimerTask = (new TimerTask() {
			@Override
			public void run() {

				Location lastLocation = LocationServiceProxy.lastLocation;
				Message msg = new Message();
				msg.obj = null;
				if (lastLocation != null && lastLocation.hasSpeed()) {
					msg.obj = "" + Math.round(lastLocation.getSpeed() * 3.6f)
							+ " Km/h";
				}
				speedHandler.sendMessage(msg);
			}
		});
		this.refreshTimer.schedule(speedUpdateTimerTask, 4000, 4000);

	}
	
	@Override
	public void onPause() {
		Log.d("SF_LC", "onPause");
		// TODO Auto-generated method stub
		this.speedUpdateTimerTask.cancel();
		this.refreshTimer.cancel();
		super.onPause();
	}

	@Override
	public void onDetach() {
		Log.d("SF_LC", "onDetach");
//		if (this.refreshTimer != null) {
//			this.refreshTimer.cancel();
//		}
//		this.refreshTimer = null;
//		this.speedUpdateTimerTask.cancel();
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		Log.d("SF_LC", "onDestroy");
//		if (this.refreshTimer != null) {
//			this.refreshTimer.cancel();
//		}
//		this.refreshTimer = null;
//		this.speedUpdateTimerTask.cancel();
		super.onDestroy();
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
