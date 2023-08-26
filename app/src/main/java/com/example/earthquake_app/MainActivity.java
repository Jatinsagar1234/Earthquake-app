package com.example.earthquake_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private EarthquakeAdapter mAdapter;
    private static final int ID=1;
    private TextView emptyView;
    private  static final String USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=19";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView earthquakeListView=findViewById(R.id.list);
        mAdapter =new EarthquakeAdapter(this,new ArrayList<Earthquake>());

        earthquakeListView.setAdapter(mAdapter);
        emptyView=findViewById(R.id.empty);
        earthquakeListView.setEmptyView(emptyView);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeuri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW ,earthquakeuri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);

            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnected()){
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(ID,null,this);
        }else {
            View view=findViewById(R.id.progress_bar);
            view.setVisibility(View.GONE);
            emptyView.setText("no internet connectivity ");
        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new Earthquake_Loader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakeList) {
        View view=findViewById(R.id.progress_bar);
        view.setVisibility(View.GONE);
        emptyView.setText("no data found");
     mAdapter.clear();
     if(earthquakeList!=null&&!earthquakeList.isEmpty()){
         mAdapter.addAll(earthquakeList);
     }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
  mAdapter.clear();
    }
}