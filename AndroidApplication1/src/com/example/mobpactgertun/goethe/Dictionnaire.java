package com.example.mobpactgertun.goethe;

import java.util.ArrayList;
import java.util.List;

import model.Tuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.DictionnaireListAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import app.AppController;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobpactgertun.R;


public class Dictionnaire extends Activity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	String apiDic = "https://glosbe.com/gapi/translate?from=%1$s&dest=%2$s&format=json&phrase=%3$s&pretty=true",
			from, dest, phrase;
	private ProgressDialog pDialog;
	private Button buttonSearch;
	private ListView listView;
	private List<Tuc> list;
	private DictionnaireListAdapter adapter;
	EditText editTextSearch;
	List<String> liste;
	Tuc item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dictionnaire);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		listView = (ListView) findViewById(R.id.listViewDictionnaire);
		list = new ArrayList<Tuc>();
		liste = new ArrayList<String>();

		adapter = new DictionnaireListAdapter(Dictionnaire.this, list);
		listView.setAdapter(adapter);

		// button search
		buttonSearch = (Button) findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phrase = editTextSearch.getText().toString();
				liste.clear();

				list.clear();
				listView.setAdapter(null);
				makeGetObject(from, dest, phrase);

			}
		});

		// edit text
		editTextSearch = (EditText) findViewById(R.id.editTextSeaarch);

		// progresdialog

		pDialog = new ProgressDialog(Dictionnaire.this);
		pDialog.setMessage("Searching...");

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3),
								getString(R.string.title_section4),
								getString(R.string.title_section5) }), this);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dictionnaire, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		switch (position) {
		case 0:
			from = "deu";
			dest = "fra";

			break;
		case 1:
			from = "deu";
			dest = "eng";

			break;
		case 2:
			from = "deu";
			dest = "deu";

			break;
		case 3:
			from = "fra";
			dest = "deu";

			break;
		case 4:
			from = "eng";
			dest = "deu";

			break;

		default:
			break;
		}


		return true;
	}

	// get translation
	public void makeGetObject(String from, String dest, String phrase) {
		String uri = String.format(apiDic, from, dest, phrase);
		pDialog.show();
		// showpDialog();
		listView.setAdapter(adapter);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, uri,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {

							JSONArray array = response.getJSONArray("tuc");
							for (int i = 0; i < array.length(); i++) {

								JSONObject feedObjGlob = (JSONObject) array
										.get(i);
								JSONObject feedObj = feedObjGlob
										.getJSONObject("phrase");

								item = new Tuc();
								item.setPhrase(feedObj.getString("text"));
								item.setSearchword(response.getString("phrase"));
								//item.setMeanings(liste);
								list.add(item);

								//
								//

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						adapter.notifyDataSetChanged();

						pDialog.hide();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						

						// hide the progress dialog
						pDialog.hide();
						Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
		

	}
}
