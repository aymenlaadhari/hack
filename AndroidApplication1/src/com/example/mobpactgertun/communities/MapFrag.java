package com.example.mobpactgertun.communities;

import java.util.ArrayList;
import java.util.List;

import model.Members;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import app.AppController;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobpactgertun.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFrag extends Fragment implements ConnectionCallbacks,
		OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {
	private MapView mMapView;
	private GoogleMap googleMap;
	private LatLng myPosition;
    private List<Members> members;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
	private static final String TAG = MapFrag.class.getSimpleName();
	private Location mLastLocation;
	private ProgressDialog pDialog;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	private String uri = "http//kjkfjkqjd";

	// boolean flag to toggle periodic location updates
	private boolean mRequestingLocationUpdates = false;

	private LocationRequest mLocationRequest;

	// Location updates intervals in sec
	private static int UPDATE_INTERVAL = 10000; // 10 sec
	private static int FATEST_INTERVAL = 5000; // 5 sec
	private static int DISPLACEMENT = 10; // 10 meters
	private Button btnShowLocation, btnStartLocationUpdates;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflate and return the layout
		View v = inflater.inflate(R.layout.fragment_map, container, false);
		mMapView = (MapView) v.findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);

		mMapView.onResume();// needed to get the map to display immediately

		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		googleMap = mMapView.getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setBuildingsEnabled(true);
		googleMap.setIndoorEnabled(true);
		members = new ArrayList<Members>();
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Searching...");

		// First we need to check availability of play services
		if (checkPlayServices()) {

			// Building the GoogleApi client
			buildGoogleApiClient();

			createLocationRequest();
		}
		//togglePeriodicLocationUpdates();

		displayLocation();
		

		return v;
	}
	
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
	    inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_map, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();

		if (id == R.id.action_updatePosition) {
			togglePeriodicLocationUpdates();
		
		}
		if (id == R.id.action_getMembersPositions) {
			makeGetMembers();
		}
		return super.onOptionsItemSelected(item);
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
			Toast.makeText(getActivity(), latitude + ", " + longitude,
					Toast.LENGTH_LONG).show();
			MarkerOptions markerOptions = new MarkerOptions();
			myPosition = new LatLng(latitude, longitude);
			markerOptions.position(myPosition);
			googleMap.addMarker(markerOptions);

		} else {
			Toast.makeText(
					getActivity(),
					"(Couldn't get the location. Make sure location is enabled on the device)",
					Toast.LENGTH_LONG).show();

		}
	}

	/**
	 * Method to toggle periodic location updates
	 * */
	private void togglePeriodicLocationUpdates() {
		if (!mRequestingLocationUpdates) {

			mRequestingLocationUpdates = true;

			// Starting the location updates
			startLocationUpdates();

			Log.d(TAG, "Periodic location updates started!");

		} else {

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
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getActivity(), "This device is not supported.",
						Toast.LENGTH_LONG).show();
				// finish();
			}
			return false;
		}
		return true;
	}

	
	/**
	 * get members  
	 * @return 
	 */
       public void makeGetMembers() {
   		pDialog.show();
   		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, uri,
   				 new Response.Listener<JSONObject>() {

   					@Override
   					public void onResponse(JSONObject response) {
   						try {

   							JSONArray feedArray = response.getJSONArray("participants");

   							for (int i = 0; i < feedArray.length(); i++) {
   								JSONObject feedObj = (JSONObject) feedArray.get(i);

   								Members item = new Members();
   								item.setName(feedObj.getString("name"));
   								item.setCommunity(feedObj.getString("community"));

   								// Image might be null sometimes
   								String image = feedObj.isNull("image") ? null : feedObj
   										.getString("image");
   								
   								item.setImage(image);
   								double lat,longt;
   								lat = feedObj.getDouble("lat");
   								longt = feedObj.getDouble("longt");
   								LatLng position =new LatLng(lat, longt);
   								item.setPosition(position);
   								members.add(item);
   							}

   						} catch (JSONException e) {
   							// TODO Auto-generated catch block
   							e.printStackTrace();
   						}

   						  pDialog.hide();
   					}
   				}, new Response.ErrorListener() {

   					@Override
   					public void onErrorResponse(VolleyError error) {
   						

   						// hide the progress dialog
   						pDialog.hide();
   						Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
   					}
   				});

   		// Adding request to request queue
   		AppController.getInstance().addToRequestQueue(jsonObjReq);
   		

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

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
		// Resuming the periodic location updates
		checkPlayServices();

		// Resuming the periodic location updates
		if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
			startLocationUpdates();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
		stopLocationUpdates();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	public static MapFrag newInstance(String text) {
		MapFrag f = new MapFrag();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);

		return f;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mGoogleApiClient.reconnect();

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		displayLocation();

		if (mRequestingLocationUpdates) {
			startLocationUpdates();
		}

	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();

	}

	
}
