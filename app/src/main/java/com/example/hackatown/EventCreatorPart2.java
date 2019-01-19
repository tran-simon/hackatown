package com.example.hackatown;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class EventCreatorPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);

        //Parametre
        Button sendRequestBtn = findViewById(R.id.sendRequestBtn);
        Button insertImgBtn = findViewById(R.id.insertImgBtn);
        final TextInputEditText eventDescription = findViewById(R.id.eventDescription);

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
            Request request = new Request( Request.EventType.AbrisBus, eventDescription.getFontFeatureSettings(), new LatLng(0,0), new Date(), 10);
            Log.d("POLY", Request.EventType.AbrisBus + eventDescription.getFontFeatureSettings() + new LatLng(0,0) + new Date());
            //fin
            }
        });
    }
}
