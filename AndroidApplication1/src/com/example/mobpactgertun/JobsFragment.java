package com.example.mobpactgertun;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class JobsFragment extends Fragment {
	private TextView textView;
	

	
	public JobsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
        textView = (TextView) rootView.findViewById(R.id.txtLabel);
        
        
        return rootView;
    }
	
	
}
