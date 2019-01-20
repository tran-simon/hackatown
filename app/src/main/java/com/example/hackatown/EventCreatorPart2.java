package com.example.hackatown;

import android.support.design.widget.TextInputEditText;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class EventCreatorPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);

        //Parametre
        Button sendRequestBtn = findViewById(R.id.sendRequestBtn);
        Bundle extras = getIntent().getExtras();
        TextView title = findViewById(R.id.EventName);
        String idType = extras.getString("id");
        final Request.EventType typeDeRequest;
        String locationStrings[] = getIntent().getStringExtra("position").split(",");
        LatLng presentPosition = new LatLng(Double.parseDouble(locationStrings[0]), Double.parseDouble(locationStrings[1]));

        //Définir le type de l'event
        switch (idType) {
            case "2131230766":  typeDeRequest = Request.EventType.FeuxCiruculation; title.setText("Feu de circulation");
            break;
            case "2131230771":  typeDeRequest = Request.EventType.PanneauxSiganlisation;  title.setText("Panneau de signalisation");
                break;
            case "2131230770":  typeDeRequest = Request.EventType.PanneauxRue;  title.setText("Panneau de nom de rue");
                break;
            case "2131230765":  typeDeRequest = Request.EventType.Deneigement;  title.setText("Déneigement");
                break;
            case "2131230769":  typeDeRequest = Request.EventType.NidDePoule;  title.setText("Nid de poule");
                break;
            case "2131230772":  typeDeRequest = Request.EventType.PoubelleRecup;  title.setText("Poubelle/Récupération remplie");
                break;
            case "2131230773":  typeDeRequest = Request.EventType.Stationnement;  title.setText("Stationnement illégal");
                break;
            case "2131230768":  typeDeRequest = Request.EventType.Lampadaire;  title.setText("Lampadaire");
                break;
            case "2131230767":  typeDeRequest = Request.EventType.InfSport;  title.setText("Infrastructure sportive");
                break;
            case "2131230763": typeDeRequest = Request.EventType.AbrisBus;  title.setText("Abribus");
                break;
            case "2131230764": typeDeRequest = Request.EventType.Autre;  title.setText("Autre");
                break;
            default: typeDeRequest = null;
                break;
        }

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                EditText editableDescription = findViewById(R.id.plain_text_input);
                String description = editableDescription.getText().toString();
                Date todaysDate = new Date();
                int userId = 1;
                Request request = new Request( typeDeRequest, description, presentPosition, todaysDate, userId);
                Log.d("POLY", "Creation dune request:");
                Log.d("POLY",  "Type : " +typeDeRequest + ", Description : " + description + ", Position : " + presentPosition + ", Date: " + todaysDate);
                 //fin
            }
        });

    }
}
