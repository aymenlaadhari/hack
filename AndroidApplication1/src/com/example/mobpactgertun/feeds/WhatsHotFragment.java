package com.example.mobpactgertun.feeds;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.ConnectionDetector;
import adapter.FeedListAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Scroller;
import android.widget.Toast;
import app.AppController;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobpactgertun.R;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.RequestParams;

public class WhatsHotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	private static final String TAG = WhatsHotFragment.class.getSimpleName();
	private ListView listView;
	private FeedListAdapter listAdapter;
	private List<FeedItem> feedItems;
	private ProgressDialog pDialog;
	private String URL_FEED1 = "http://192.168.1.100:8084/MobPact/ServiceWeb/Feeds/getfeeds";
	String sttatus, link, image,imgPath,fileName;
	private ConnectionDetector cd;
	Boolean isInternetPresent = false;
	FloatingActionButton mFab;
	private PopupWindow popWindow;
	EditText linkText, textPost;
	private static int RESULT_LOAD_IMG = 1;
	LayoutInflater layoutInflater;
	RequestParams params = new RequestParams();
	private SwipeRefreshLayout swipeRefreshLayout;
	private int offSet = 0;

	public WhatsHotFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);
		listView = (ListView) rootView.findViewById(R.id.list);
		mFab = (FloatingActionButton) rootView.findViewById(R.id.fabbutton);
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(this);
		mFab.listenTo(listView);
		
		 /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        swipeRefreshLayout.setRefreshing(true);
// 
//                                        getdata();
//                                    }
//                                }
//        );
		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onShowPopup(v);
				Intent intentNewpost = new Intent(getActivity(),NewPostActivity.class);
				startActivity(intentNewpost);

			}
		});
		feedItems = new ArrayList<FeedItem>();
		listAdapter = new FeedListAdapter(getActivity(), feedItems);
		listView.setAdapter(listAdapter);
		pDialog = new ProgressDialog(getActivity());
		pDialog.setCancelable(false);
		pDialog.setMessage("Loading data...");
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		getdata();

		return rootView;
	}
	
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getdata();
		
	}
	
	/**
	 * get data
	 */

	private void getdata()
	{
		// We first check for cached request
				Cache cache = AppController.getInstance().getRequestQueue().getCache();
				Entry entry = cache.get(URL_FEED1);
				if (entry != null) {
					// fetch the data from cache
					try {
						String data = new String(entry.data, "UTF-8");
						try {

							parseJsonFeed(new JSONObject(data));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

				} else if (isInternetPresent && entry == null) {
					//pDialog.show();
					// making fresh volley request and getting json
					JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
							URL_FEED1,new Response.Listener<JSONObject>() {

								@Override
								public void onResponse(JSONObject response) {
									VolleyLog.d(TAG, "Response: " + response.toString());
									if (response != null) {
										parseJsonFeed(response);
										swipeRefreshLayout.setRefreshing(false);
										//pDialog.hide();
									}
								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {
									VolleyLog.d(TAG, "Error: " + error.getMessage());
									//pDialog.hide();
									Toast.makeText(getActivity(), "Network Error",
											Toast.LENGTH_SHORT).show();
									 swipeRefreshLayout.setRefreshing(false);

								}
							});

					// Adding request to volley request queue
					AppController.getInstance().addToRequestQueue(jsonReq);
				}
	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		swipeRefreshLayout.setRefreshing(true);
		try {
			// JSONArray feedArray = response.getJSONArray("feed");
			JSONArray feedArray = response.getJSONArray("feeds");

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				FeedItem item = new FeedItem();
				item.setId(feedObj.getInt("id"));
				item.setName(feedObj.getString("name"));

				// Image might be null sometimes
				String image = feedObj.isNull("image") ? null : feedObj
						.getString("image");

				item.setImge(image);
				item.setStatus(feedObj.getString("status"));

				// item.setProfilePic(feedObj.getString("profilePic"));
				item.setProfilePic(feedObj.getString("profilepic"));
				// item.setTimeStamp(feedObj.getString("timeStamp"));
				item.setTimeStamp(feedObj.getString("timestamp"));

				// url might be null sometimes
				String feedUrl = feedObj.isNull("url") ? null : feedObj
						.getString("url");
				item.setUrl(feedUrl);

				feedItems.add(item);
			}

			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();
			swipeRefreshLayout.setRefreshing(true);
		} catch (JSONException e) {
			e.printStackTrace();
			swipeRefreshLayout.setRefreshing(true);
		}
	}

	public void hideFab(View view) {
		mFab.hide(true);
		// getActionBar().hide();
	}

	public void showFab(View view) {
		mFab.hide(false);
		// getActionBar().show();
	}

	public void fabClicked(View view) {
		Toast.makeText(getActivity(), "Hi", Toast.LENGTH_LONG).show();
	}

	public void onShowPopup(View v) {

		layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflate the custom popup layout
		final View inflatedView = layoutInflater.inflate(R.layout.popup_layout,
				null, false);

		// get device size
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);

		// set height depends on the device size
		popWindow = new PopupWindow(inflatedView, size.x - 10, size.y - 270,
				true);
		// set a background drawable with rounders corners
		popWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.popup_bg));
		// make it focusable to show the keyboard to enter in `EditText`
		popWindow.setFocusable(true);
		// make it outside touchable to dismiss the popup window
		popWindow.setOutsideTouchable(true);

		// show the popup at bottom of the screen and set some margin at bottom
		// ie,
		popWindow.showAtLocation(v, Gravity.BOTTOM, 0, 100);

		Button btnDismiss = (Button) inflatedView
				.findViewById(R.id.button_post);
		Button loadImage = (Button)inflatedView.findViewById(R.id.getImage);
		textPost = (EditText) inflatedView
				.findViewById(R.id.EditTextFeedbackBody);
		textPost.setScroller(new Scroller(getActivity().getApplicationContext()));
		textPost.setMaxLines(1);
		textPost.setVerticalScrollBarEnabled(true);
		textPost.setMovementMethod(new ScrollingMovementMethod());
		linkText = (EditText) inflatedView.findViewById(R.id.EditTextEmail);

		btnDismiss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sttatus = textPost.getText().toString();
				link = linkText.getText().toString();
				popWindow.dismiss();
			}
		});
	
		loadImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadImagefromGallery(v);
				
			}
		});
	}

	public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

	



}
