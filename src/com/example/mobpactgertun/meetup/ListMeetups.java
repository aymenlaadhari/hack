package com.example.mobpactgertun.meetup;

import com.example.mobpactgertun.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListMeetups extends Fragment {
	public ListMeetups(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_list_meetup, container,
				false);
		return rootView;
	}

}
