package com.example.hackatown;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class EventCreatorPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);
        String locationStrings[] = getIntent().getStringExtra("position").split(",");

        LatLng location = new LatLng(Double.parseDouble(locationStrings[0]), Double.parseDouble(locationStrings[1]));
    }
}
