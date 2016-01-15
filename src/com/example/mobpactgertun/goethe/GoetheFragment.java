package com.example.mobpactgertun.goethe;

import java.util.ArrayList;

import adapter.GridviewAdapter;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.mobpactgertun.R;



public class GoetheFragment extends Fragment {

	private GridviewAdapter mAdapter;
	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;
	private GridView gridView;

	public GoetheFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_goethe, container,
				false);

		

		prepareList();

		// prepared arraylist and passed it to the Adapter class
		mAdapter = new GridviewAdapter(getActivity(), listCountry, listFlag);

		// Set custom adapter to gridview
		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		gridView.setAdapter(mAdapter);

		// Implement On Item click listener
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				 switch (position) {
				case 0:
					Intent intentVideoAlltag = new Intent(getActivity(),VideoIntro.class);
					intentVideoAlltag.putExtra("intent", "Alltag");
					startActivity(intentVideoAlltag);					
					break;
					case 1:
						Intent intentVideoBeruf = new Intent(getActivity(),VideoIntro.class);
						intentVideoBeruf.putExtra("intent", "Beruf");
						startActivity(intentVideoBeruf);
						break;
					case 2 :
						Intent intentVideoMagazine = new Intent(getActivity(),VideoIntro.class);
						intentVideoMagazine.putExtra("intent", "Magazine");
						startActivity(intentVideoMagazine);
						
						break;
				case 3:
					Intent intentDic = new Intent(getActivity(), Dictionnaire.class);
					startActivity(intentDic);
					
					break;

				default:
					break;
				}
				 
			}
		});
		return rootView;
	}
	
	public void prepareList() {
		listCountry = new ArrayList<String>();

		listCountry.add("Alltag");
		listCountry.add("Beruf");
		listCountry.add("Magazine");
		listCountry.add("Wörterbuch");

		listFlag = new ArrayList<Integer>();

		listFlag.add(R.drawable.ic_supervisor_account_white_48dp);
		listFlag.add(R.drawable.ic_work_white_48dp);
		listFlag.add(R.drawable.ic_assignment_returned_white_48dp);
		listFlag.add(R.drawable.ic_language_white_48dp);

	}

	

	

	

}
