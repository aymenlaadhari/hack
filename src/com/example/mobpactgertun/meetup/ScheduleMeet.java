package com.example.mobpactgertun.meetup;

import com.example.mobpactgertun.R;

import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Scroller;

public class ScheduleMeet extends Fragment{
	

	public ScheduleMeet(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_schedule_meetup, container,
				false);

		return rootView;
	}
	
	
}
