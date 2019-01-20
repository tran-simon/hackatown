package com.example.hackatown;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventInfoActivity extends AppCompatActivity {
    private String info = "";
    private JSONObject objectInfo = new JSONObject();
    private TextView textView;


    private String date = "", description = "";
    private int id = 0, type = 0, user_id = 0;
    private LatLng position = new LatLng(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView imageView = findViewById(R.id.imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        textView = findViewById(R.id.txt_scrollable);
        textView.setText("ASDF");

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


        try
        {
            loadData();
        } catch (JSONException e)
        {
            e.printStackTrace();
            //todo
        }



    }


    public void loadData() throws JSONException {
        info = "[{\"date\":\"Sat, 19 Jan 2019 20:44:52 GMT\",\"description\":\"\\u00c9cole \\u00e0 r\\u00e9nover\",\"id\":1,\"position\":\"45.504384,-73.6150716\",\"type\":1,\"user_id\":1}]\n";//TODO

        objectInfo = new JSONArray(info).getJSONObject(0);

        date = objectInfo.getString("date");
        description = objectInfo.getString("description");
        type = objectInfo.getInt("type");
        user_id = objectInfo.getInt("user_id");

        String latlng = objectInfo.getString("position");

        String positionStrings[] = latlng.split(",");

        position = new LatLng(Double.parseDouble(positionStrings[0]), Double.parseDouble(positionStrings[1]));


        textView.setText("Date: " + date + "\nDescription: " + description + "\nPosition (lat, long): (" + position.latitude + ":" + position.longitude  + ")\nUser: " + user_id);

    }
}
