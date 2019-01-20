package com.example.hackatown;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import android.app.AlertDialog;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> markerList = new ArrayList<>();
    private String info = "";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    private boolean location;

    private void requestLocation() {
	    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    	location = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
		if (location) {
			getDeviceLocation();
			Toast.makeText(getApplicationContext(), "Have fun!", Toast.LENGTH_SHORT).show();
		}
		else
			new AlertDialog.Builder(this)
				.setTitle("Permission denied")
				.setMessage("You will not be able to access to your position and add an event.")
				.setCancelable(true)
				.setNegativeButton("OK", null)
				.create().show();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        requestLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	if (location) {
		                    Intent intent = new Intent(MapsActivity.this, EventsCreatorActivity.class);
		                    getDeviceLocation();
		                    String locationString = mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude();
		                    intent.putExtra("position", locationString);
		                    startActivity(intent);
	                    } else requestLocation();
                    }
                }
        );


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    public void refresh() {
        GetData getData = new GetData(new OnDataReceivedListener() {
            @Override
            public void OnDataReceived(String data) {
                info = data;
                loadData();
                findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
            }
        });

        getData.execute(-1);
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
    }

    /**
     * Permet de loader les données (position des marqueurs...)
     */
    public void loadData() {
        mMap.clear();
        try
        {
            JSONArray jsonArray = new JSONArray(info);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                Marker marker = new Marker(object);
                markerList.add(marker);


            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        for (Marker marker : markerList)
        {
            mMap.addMarker(marker.getMarkerOption());
        }

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try
        {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful())
                    {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                    }
                }
            });
        } catch (SecurityException e)
        {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0, getSupportActionBar().getHeight(),0, 0);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                for (Marker marker1 : markerList)
                {
                    if (marker1.getMarkerOption().getPosition().equals(marker.getPosition()))
                    {
                        //TODO: L'user a cliqué sur le marker, launch activity
                        //                        GetData getData = new GetData(MapsActivity.this, EventInfoActivity.class);
                        //                        getData.execute(marker1.getId());//-1
                        Intent intent = new Intent(MapsActivity.this, EventInfoActivity.class);
                        intent.putExtra("id", marker1.getId());
                        startActivity(intent);

                        break;
                    }
                }

                return true;
            }
        });
        getDeviceLocation();


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.50884, -73.58781), 10));
        refresh();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_activity_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.refresh:
                refresh();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
