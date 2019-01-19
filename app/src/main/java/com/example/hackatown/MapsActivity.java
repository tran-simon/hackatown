package com.example.hackatown;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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


        loadData();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                for (Marker marker1 : markerList)
                {
                    Log.d("POLY", marker.getPosition() + " : " + marker1.getMarkerOption());
                    if (marker1.getMarkerOption().getPosition().equals(marker.getPosition()))
                    {
                        //TODO: L'user a cliqué sur le marker, launch activity
                        Intent intent = new Intent(MapsActivity.this, EventInfoActivity.class);
                        startActivity(intent);
                        break;
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void onCameraMove() {

    }
}
