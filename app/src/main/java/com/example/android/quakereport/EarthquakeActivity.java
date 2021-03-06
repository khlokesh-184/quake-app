/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Earthquke>>{
    private TextView mEmptyStateTextView;
    private  ListView earthquakeListView;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static String USGS_REQUEST_URL ="";
    private Custom_Adapter myAdapter;
    @Override
    public Loader<ArrayList<Earthquke>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby="+orderBy+"&minmag="+minMagnitude+"&limit=20";

        return new EarthquakeLoader(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquke>> loader, ArrayList<Earthquke> earthquakes) {
        // TODO: Update the UI with the result
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        try{myAdapter.clear();}
        catch(NullPointerException e){

        }
        if (earthquakes != null && !earthquakes.isEmpty()) {
            updateUI(earthquakes);
            return;
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);



    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquke>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        try {
            earthquakeListView.setEmptyView(mEmptyStateTextView);
        }catch(NullPointerException e){

        }


        // Get a reference to the LoaderManager, in order to interact with loaders.
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
     }



        // Create a fake list of earthquake locations.
      /*  ArrayList<Earthquke> earthquakes = new ArrayList<Earthquke>();
        earthquakes.add(new Earthquke(7.2f,"San Francisco","24 Feb, 2016"));
        earthquakes.add(new Earthquke(4.5f,"San Francisco","24 Feb, 2016"));
        earthquakes.add(new Earthquke(6.7f,"San Francisco","24 Feb, 2016"));
        earthquakes.add(new Earthquke(8.7f,"San Francisco","24 Feb, 2016"));
        earthquakes.add(new Earthquke(9.8f,"San Francisco","24 Feb, 2016"));
        earthquakes.add(new Earthquke(4.8f,"Tokyo","24 Feb, 2016"));
        earthquakes.add(new Earthquke(7.0f,"San Francisco","24 Feb, 2016"));
        */

    private void updateUI(ArrayList<Earthquke> listOfEarthquakes) {
        ArrayList<Earthquke> earthquakes = listOfEarthquakes;
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);

        myAdapter = new Custom_Adapter(this, earthquakes);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquke currentEarthquake = myAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

        earthquakeListView.setAdapter(myAdapter);
    }



}
