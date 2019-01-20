package com.example.hackatown;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class EventCreatorPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);

        //Parametre
        Button sendRequestBtn = findViewById(R.id.sendRequestBtn);

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                Bundle extras = getIntent().getExtras();
                String typeId = extras.getString("id");
                EditText text = findViewById(R.id.plain_text_input);
                Request request = new Request( Request.EventType.AbrisBus, text.getText().toString(), new LatLng(0,0), new Date(), 10);
                Log.d("POLY", "Creation dune request:");
                Log.d("POLY", Request.EventType.AbrisBus + text.getText().toString() + new LatLng(0,0) + new Date());
                 //fin
            }
        });
    }
}
