package com.example.mobpactgertun;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
public class FragmentPlace extends Fragment implements ConnectionCallbacks,
OnConnectionFailedListener, LocationListener {
	// LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
 
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
 
    private Location mLastLocation;
 
 
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
 
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
 
    private LocationRequest mLocationRequest;
 
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
 
    // UI elements
    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdates;
	
    Context context;
	public FragmentPlace(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_place, container, false);
        context = getActivity().getApplicationContext();
        lblLocation = (TextView) rootView.findViewById(R.id.lblLocation);
        btnShowLocation = (Button) rootView.findViewById(R.id.btnShowLocation);
        btnStartLocationUpdates = (Button) rootView.findViewById(R.id.btnLocationUpdates);
 
        // First we need to check availability of play services
        if (checkPlayServices()) {
 
            // Building the GoogleApi client
            buildGoogleApiClient();
 
            createLocationRequest();
        }
 
        // Show location button click listener
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                displayLocation();
            }
        });
 
        // Toggling the periodic location updates
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                togglePeriodicLocationUpdates();
            }
        });
        
         
        return rootView;
    }

	 @Override
	public void onStart() {
	        super.onStart();
	        if (mGoogleApiClient != null) {
	            mGoogleApiClient.connect();
	        }
	    }
	 
	    @Override
		public void onResume() {
	        super.onResume();
	 
	        checkPlayServices();
	 
	        // Resuming the periodic location updates
	        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
	            startLocationUpdates();
	        }
	    }
	 
	    @Override
		public void onStop() {
	        super.onStop();
	        if (mGoogleApiClient.isConnected()) {
	            mGoogleApiClient.disconnect();
	        }
	    }
	 
	    @Override
		public void onPause() {
	        super.onPause();
	        stopLocationUpdates();
	    }
	 
	    
	    /**
	     * Method to display the location on UI
	     * */
	    private void displayLocation() {
	 
	        mLastLocation = LocationServices.FusedLocationApi
	                .getLastLocation(mGoogleApiClient);
	 
	        if (mLastLocation != null) {
	            double latitude = mLastLocation.getLatitude();
	            double longitude = mLastLocation.getLongitude();
	 
	            lblLocation.setText(latitude + ", " + longitude);
	 
	        } else {
	 
	            lblLocation
	                    .setText("(Couldn't get the location. Make sure location is enabled on the device)");
	        }
	    }
	 
	    /**
	     * Method to toggle periodic location updates
	     * */
	    private void togglePeriodicLocationUpdates() {
	        if (!mRequestingLocationUpdates) {
	            // Changing the button text
	            btnStartLocationUpdates
	                    .setText(getString(R.string.btn_stop_location_updates));
	 
	            mRequestingLocationUpdates = true;
	 
	            // Starting the location updates
	            startLocationUpdates();
	 
	            Log.d(TAG, "Periodic location updates started!");
	 
	        } else {
	            // Changing the button text
	            btnStartLocationUpdates
	                    .setText(getString(R.string.btn_start_location_updates));
	 
	            mRequestingLocationUpdates = false;
	 
	            // Stopping the location updates
	            stopLocationUpdates();
	 
	            Log.d(TAG, "Periodic location updates stopped!");
	        }
	    }
	 
	    /**
	     * Creating google api client object
	     * */
	    protected synchronized void buildGoogleApiClient() {
	    	
	        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
	                .addConnectionCallbacks(this)
	                .addOnConnectionFailedListener(this)
	                .addApi(LocationServices.API).build();
	    }
	 
	    /**
	     * Creating location request object
	     * */
	    protected void createLocationRequest() {
	        mLocationRequest = new LocationRequest();
	        mLocationRequest.setInterval(UPDATE_INTERVAL);
	        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
	        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
	    }
	 
	    /**
	     * Method to verify google play services on the device
	     * */
	    private boolean checkPlayServices() {
	        int resultCode = GooglePlayServicesUtil
	                .isGooglePlayServicesAvailable(getActivity());
	        if (resultCode != ConnectionResult.SUCCESS) {
	            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
	                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
	            } else {
	                Toast.makeText(getActivity(),
	                        "This device is not supported.", Toast.LENGTH_LONG)
	                        .show();
	                //finish();
	            }
	            return false;
	        }
	        return true;
	    }
	 
	    /**
	     * Starting the location updates
	     * */
	    protected void startLocationUpdates() {
	 
	        LocationServices.FusedLocationApi.requestLocationUpdates(
	                mGoogleApiClient, mLocationRequest, this);
	 
	    }
	 
	    /**
	     * Stopping location updates
	     */
	    protected void stopLocationUpdates() {
	        LocationServices.FusedLocationApi.removeLocationUpdates(
	                mGoogleApiClient, this);
	    }
	 
	    /**
	     * Google api callback methods
	     */
	    @Override
	    public void onConnectionFailed(ConnectionResult result) {
	        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
	                + result.getErrorCode());
	    }
	 
	    @Override
	    public void onConnected(Bundle arg0) {
	 
	        // Once connected with google api, get the location
	        displayLocation();
	 
	        if (mRequestingLocationUpdates) {
	            startLocationUpdates();
	        }
	    }
	 
	    @Override
	    public void onConnectionSuspended(int arg0) {
	        mGoogleApiClient.connect();
	    }
	 
	    @Override
	    public void onLocationChanged(Location location) {
	        // Assign the new location
	        mLastLocation = location;
	 
	        Toast.makeText(getActivity(), "Location changed!",
	                Toast.LENGTH_SHORT).show();
	 
	        // Displaying the new location on UI
	        displayLocation();
	    }
	 
	}