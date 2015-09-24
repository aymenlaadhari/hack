package com.example.mobpactgertun.meetup;

import com.example.mobpactgertun.R;
import java.util.HashMap;
import java.util.Map;
import model.Meetup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import app.AppController;

public class MeetupFragment extends Fragment {
	private Meetup meetup;
	private FragmentTabHost mTabHost;
	
	

	public MeetupFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_meetup, container,
				false);
		mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity().getBaseContext(), getChildFragmentManager(), R.id.realtabcontent);
		mTabHost.addTab(mTabHost.newTabSpec("fragmentList").setIndicator("Meetups"),
                ListMeetups.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentSchedul").setIndicator("Schedule"),
                ScheduleMeet.class, null);
        
		return rootView;
	}

	/**
	 * makePostMeet
	 */
	public void makePostMeet(String ID, String Provider) {

		final String id = ID;
		final String provider = Provider;

		StringRequest req = new StringRequest(
				Request.Method.POST,
				"http://runningchallange5950.azurewebsites.net/api/RunnerStatistics?externalID="
						+ id + "&externaProvider=" + provider,
				createMyReqSuccessListener(), createMyReqErrorListener()) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/json; charset=utf-8");
				params.put("name", meetup.getName());
				params.put("cause", meetup.getCause());
				params.put("description", meetup.getDescription());
				params.put("place", meetup.getPlace().toString());
				params.put("date", meetup.getDate().toString());

				return params;
			}

			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				// do not add anything here
				return headers;
			}
		};

		AppController.getInstance().addToRequestQueue(req);
	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT)
						.show();
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		};
	}
}
