package com.example.mobpactgertun;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import app.AppController;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class TestWS extends Activity {
	Button postFeeds, updateFeeds, deleteFeeds, postMeetup, updateMeetup,
			deleteMeetup, postParticipant, existParticipant, updateParticipant,
			deleteParticipant, getParticipantFeeds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_ws);
		postFeeds = (Button) findViewById(R.id.postFeed);
		updateFeeds = (Button) findViewById(R.id.updateFeeds);
		deleteFeeds = (Button) findViewById(R.id.deleteFeeds);
		postMeetup = (Button) findViewById(R.id.postMeetup);
		updateMeetup = (Button) findViewById(R.id.updateMeetup);
		deleteMeetup = (Button) findViewById(R.id.deleteMeetup);
		postParticipant = (Button) findViewById(R.id.postParticipant);
		existParticipant = (Button) findViewById(R.id.existParticipant);
		updateParticipant = (Button) findViewById(R.id.updateParticipant);
		getParticipantFeeds = (Button) findViewById(R.id.getParticipantFeeds);

		// click listener

		postFeeds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postFeeds();

			}
		});

		getParticipantFeeds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFeeds();

			}
		});

		postMeetup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postMeetup();
			}
		});
	
		postParticipant.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postParticipant();
				
			}
		});
	}

	/**
	 * post feeds
	 */
	public void postFeeds() {
		final String URL = "http://192.168.1.100:8084/MobPact/ServiceWeb/Feeds/createFeeds";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Content-Type", "application/json; charset=utf-8");
		params.put("id", "");
		params.put("image", "");
		params.put("profilepic", "");
		params.put("name", "");
		params.put("status", "Alaa");
		params.put("url", "");
		params.put("timestamp", "");
		params.put("email", "aymenlaadhari@gmail.com");
		JsonObjectRequest req = new JsonObjectRequest(Method.POST, URL,
				new JSONObject(params), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {

							Toast.makeText(getApplicationContext(),
									response.toString(), Toast.LENGTH_LONG)
									.show();
							VolleyLog.v("Response:%n %s", response.toString(4));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		// add the request object to the queue to be executed
		AppController.getInstance().addToRequestQueue(req);

	}

	public void postMeetup() {

		final String URL = "http://192.168.1.100:8084/MobPact/ServiceWeb/Meetups/createMeetup";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Content-Type", "application/json; charset=utf-8");
		params.put("idMeet", "1");
		params.put("nameMeet", "Alaa");
		params.put("cause", "Msaken");
		params.put("description", "alallal");
		params.put("lat", "1.928");
		params.put("ln", "1.2982");
		params.put("email", "aymenlaadhari@gmail.com");
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String date = sqlDate.toString();
		params.put("date", date);
		JsonObjectRequest req = new JsonObjectRequest(Method.POST, URL,
				new JSONObject(params), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {

							Toast.makeText(getApplicationContext(),
									response.toString(), Toast.LENGTH_LONG)
									.show();
							VolleyLog.v("Response:%n %s", response.toString(4));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		// add the request object to the queue to be executed
		AppController.getInstance().addToRequestQueue(req);
	}

	public void postParticipant()
	{	
		final String URL = "http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/createParticipant";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Content-Type", "application/json; charset=utf-8");
		params.put("name", "Meriam Krifa");
		params.put("community", "Hamburg");
		params.put("imageProfile", "http://s0.uploads.im/JpfcP.jpg");
		params.put("email", "meriam.krifa@gmail.com");
		params.put("lat", "36.7767012");
		params.put("longt", "10.189712");
		JsonObjectRequest req = new JsonObjectRequest(Method.POST, URL,
				new JSONObject(params), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {

							Toast.makeText(getApplicationContext(),
									response.toString(), Toast.LENGTH_LONG)
									.show();
							VolleyLog.v("Response:%n %s", response.toString(4));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		// add the request object to the queue to be executed
		AppController.getInstance().addToRequestQueue(req);
	}
	/**
	 * Method to make json Array request for runners informations where json
	 * response starts wtih [
	 * */
	public void getFeeds() {

		String api = "http://192.168.1.100:8084/MobPact/ServiceWeb/Feeds/getfeeds";
		JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,api,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						Toast.makeText(getApplicationContext(),
								response.toString(), Toast.LENGTH_SHORT).show();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Toast.makeText(getApplicationContext(),
								"Network Error", Toast.LENGTH_SHORT).show();

					}
				});

		// Adding request to volley request queue
		AppController.getInstance().addToRequestQueue(jsonReq);
	}

	// private static ClientResponse updateFeeds() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Feeds/updateFeeds");
	//
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("feedsId", "2");
	// jsonObject.addProperty("status", "Alaa");
	// jsonObject.addProperty("image", "Msaken la perle");
	// jsonObject.addProperty("url", "alallal");
	// jsonObject.addProperty("timestamp", "");
	// jsonObject.addProperty("email", "");
	// jsonObject.addProperty("profilepic", "");
	// jsonObject.addProperty("name", "");
	// ClientResponse response = webResource.type("application/json")
	// .put(ClientResponse.class, jsonObject.toString());
	//
	// return response;
	// }
	//
	// private static ClientResponse deleteFeeds() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Feeds/deleteFeeds");
	//
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("feedsId", "5");
	// jsonObject.addProperty("status", "");
	// jsonObject.addProperty("image", "");
	// jsonObject.addProperty("url", "");
	// jsonObject.addProperty("timestamp", "");
	// jsonObject.addProperty("email", "");
	// jsonObject.addProperty("profilepic", "");
	// jsonObject.addProperty("name", "");
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	//
	// return response;
	// }
	//
	// private static ClientResponse updateMeetup()
	// {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Meetups/createMeetup");
	//
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("idMeet", "1");
	// jsonObject.addProperty("nameMeet", "Alaa");
	// jsonObject.addProperty("cause", "Msaken");
	// jsonObject.addProperty("description", "alallal");
	// jsonObject.addProperty("lat", "1.928");
	// jsonObject.addProperty("ln", "1.2982");
	// jsonObject.addProperty("email", "aymenlaadhari@gmail.com");
	// java.util.Date utilDate = new java.util.Date();
	// java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	//
	// String date = sqlDate.toString();
	//
	// jsonObject.addProperty("date", date);
	//
	// ClientResponse response = webResource.type("application/json")
	// .put(ClientResponse.class, jsonObject.toString());
	//
	// return response;
	// }
	//
	// private static ClientResponse deleteMeetup()
	// {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Meetups/removeMeetup");
	//
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("idMeet", "1");
	// jsonObject.addProperty("nameMeet", "");
	// jsonObject.addProperty("cause", "");
	// jsonObject.addProperty("description", "");
	// jsonObject.addProperty("lat", "");
	// jsonObject.addProperty("ln", "");
	// jsonObject.addProperty("email", "");
	// java.util.Date utilDate = new java.util.Date();
	// java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	//
	// String date = sqlDate.toString();
	//
	// jsonObject.addProperty("date", date);
	//
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	//
	// return response;
	//
	// }
	//
	// private static ClientResponse postParticipant() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/createParticipant");
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("name", "Meriam Krifa");
	// jsonObject.addProperty("community", "Hamburg");
	// jsonObject.addProperty("imageProfile", "http://s0.uploads.im/JpfcP.jpg");
	// jsonObject.addProperty("email", "meriam.krifa@gmail.com");
	// jsonObject.addProperty("lat", "36.7767012");
	// jsonObject.addProperty("longt", "10.189712");
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	// return response;
	// }
	//
	// private static ClientResponse existParticipant() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/existParticipant");
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("name", "");
	// jsonObject.addProperty("community", "");
	// jsonObject.addProperty("imageProfile", "");
	// jsonObject.addProperty("email", "aymenlaadhari@gmail.com");
	// jsonObject.addProperty("lat", "");
	// jsonObject.addProperty("longt", "");
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	// return response;
	// }
	//
	// private static ClientResponse updateParticipant() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/updateParticipant");
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("name", "Alaa");
	// jsonObject.addProperty("community", "Msaken City");
	// jsonObject.addProperty("imageProfile", "alallal");
	// jsonObject.addProperty("email", "aymenlaadhari@gmail.com");
	// jsonObject.addProperty("lat", "1.2982");
	// jsonObject.addProperty("longt", "1.2982");
	// ClientResponse response = webResource.type("application/json")
	// .put(ClientResponse.class, jsonObject.toString());
	// return response;
	// }
	//
	// private static ClientResponse deleteParticipant() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/deleteParticipant");
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("name", "");
	// jsonObject.addProperty("community", "");
	// jsonObject.addProperty("imageProfile", "");
	// jsonObject.addProperty("email", "aymenlaadhari@gmail.com");
	// jsonObject.addProperty("lat", "");
	// jsonObject.addProperty("longt", "");
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	// return response;
	// }
	//
	// private static ClientResponse getParticipantFeeds() {
	// Client client = Client.create();
	//
	// WebResource webResource = client
	// .resource("http://192.168.1.100:8084/MobPact/ServiceWeb/Participant/getFeedsParticipants");
	// JsonObject jsonObject = new JsonObject();
	// jsonObject.addProperty("name", "");
	// jsonObject.addProperty("community", "");
	// jsonObject.addProperty("imageProfile", "");
	// jsonObject.addProperty("email", "aymenlaadhari@gmail.com");
	// jsonObject.addProperty("lat", "");
	// jsonObject.addProperty("longt", "");
	// ClientResponse response = webResource.type("application/json")
	// .post(ClientResponse.class, jsonObject.toString());
	// return response;
	// }
}
