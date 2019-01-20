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
    private Request.EventType type = Request.EventType.Autre;
    private int id = 0, user_id = 0;
    private LatLng position = new LatLng(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageView imageView = findViewById(R.id.imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        textView = findViewById(R.id.txt_scrollable);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


        try
        {
            loadData();

        } catch (JSONException e)
        {
            e.printStackTrace();
            Log.d("POLY", "onCreate: adsfad");
            //todo
        }

        switch (type)
        {
            case FeuxCiruculation:
                getSupportActionBar().setTitle(R.string.feu_circulation);
                break;
            case PanneauxSiganlisation:
                getSupportActionBar().setTitle(R.string.panneau_signalisation);
                break;
            case PanneauxRue:
                getSupportActionBar().setTitle(R.string.panneau_rue);
                break;
            case Deneigement:
                getSupportActionBar().setTitle(R.string.deneigement);
                break;
            case NidDePoule:
                getSupportActionBar().setTitle(R.string.nid_poule);
                break;
            case PoubelleRecup:
                getSupportActionBar().setTitle(R.string.poubelle);
                break;
            case Stationnement:
                getSupportActionBar().setTitle(R.string.stationnement);
                break;
            case AbrisBus:
                getSupportActionBar().setTitle(R.string.abris_bus);
                break;
            case Lampadaire:
                getSupportActionBar().setTitle(R.string.lampadaire);
                break;
            case InfSport:
                getSupportActionBar().setTitle(R.string.infrastructure_sportive);
                break;
            case Autre:
            default:
                getSupportActionBar().setTitle(R.string.autre);
                break;
        }

    }


    public void loadData() throws JSONException {
        info = "[{\"date\":\"Sat, 19 Jan 2019 20:44:52 GMT\",\"description\":\"\\u00c9cole \\u00e0 r\\u00e9nover\",\"id\":1,\"position\":\"45.504384,-73.6150716\",\"type\":1,\"user_id\":1}]\n";//TODO

        objectInfo = new JSONArray(info).getJSONObject(0);

        date = objectInfo.getString("date");
        description = objectInfo.getString("description");
        type = Request.EventType.values()[objectInfo.getInt("type")];
        user_id = objectInfo.getInt("user_id");

        String latlng = objectInfo.getString("position");

        String positionStrings[] = latlng.split(",");

        position = new LatLng(Double.parseDouble(positionStrings[0]), Double.parseDouble(positionStrings[1]));


        textView.setText("Date: " + date + "\nDescription: " + description + "\nPosition (lat, long): (" + position.latitude + ":" + position.longitude + ")\nUser: " + user_id);


    }
}
