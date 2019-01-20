package com.example.hackatown;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> markerList = new ArrayList<>();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.setTitle("Carte");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapsActivity.this, EventsCreatorActivity.class);
                        getDeviceLocation();
                        String locationString = mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude();
                        intent.putExtra("position", locationString);
                        startActivity(intent);

                    }
                }
        );


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    /**
     * Permet de loader les données (position des marqueurs...)
     */
    public void loadData() {

        //TODO: Change
        Marker montreal = new Marker(new LatLng(45.50884, -73.58781));
        Marker terrebonne = new Marker(new LatLng(45.70004, -73.64732));


        markerList.add(montreal);
        markerList.add(terrebonne);
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
                    else
                    {
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        // Permission is not granted
        // Should we show an explanation?
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else
            {
                // No explanation needed; request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            0);
                }
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            // Permission has already been granted
            mMap.setMyLocationEnabled(true);
        }


        loadData();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                for (Marker marker1 : markerList)
                {
                    if (marker1.getMarkerOption().getPosition().equals(marker.getPosition()))
                    {
                        //TODO: L'user a cliqué sur le marker, launch activity
                        GetData getData = new GetData();
                        getData.execute(1);//-1

//                        Intent intent = new Intent(MapsActivity.this, EventInfoActivity.class);
//                        int id = 0;
//                        intent.putExtra("id", id);
//                        startActivity(intent);
                        break;
                    }
                }

                return true;
            }
        });
        getDeviceLocation();



    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 1 &&
                permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            else
            {
                mMap.setMyLocationEnabled(true);

            }
        }
        else
        {
            // Permission was denied. Display an error message.
        }
    }

    @Override
    public void onCameraMove() {

    }




    public class GetData extends AsyncTask<Integer, String, String> {



        // https://stackoverflow.com/a/2938787
        // https://stackoverflow.com/a/16450705
        @Override
        protected String doInBackground(final Integer... params) {
            URL url;
            try
            {
                String s = "https://dev.concati.me/data";
                if (params[0] > -1)
                {
                    s += "?id=" + params[0];
                }
                url = new URL(s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String jsonString = br.readLine();

                if (params[0] > -1)
                {
                    jsonString = '[' + jsonString + ']';
                }
                br.close();
                System.out.println("JSON: " + jsonString);

                return jsonString;
            } catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO: Parse as list


            Intent intent = new Intent(MapsActivity.this, EventInfoActivity.class);
            intent.putExtra("info", result);
            startActivity(intent);


        }
    }
}
