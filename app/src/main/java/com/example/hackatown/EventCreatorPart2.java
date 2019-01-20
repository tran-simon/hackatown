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
        Request.EventType type = Request.EventType.values()[getIntent().getIntExtra("type", 0)] ;
        final Request.EventType typeDeRequest;
        String locationStrings[] = getIntent().getStringExtra("position").split(",");
        final LatLng location = new LatLng(Double.parseDouble(locationStrings[0]), Double.parseDouble(locationStrings[1]));


        //Définir le type de l'event
        switch (type) {
            case FeuxCiruculation:  typeDeRequest = Request.EventType.FeuxCiruculation;
                                    this.setTitle("Feu de circulation");
                                    title.setText("Feu de circulation");
                                    break;

            case PanneauxSiganlisation:  typeDeRequest = Request.EventType.PanneauxSiganlisation;
                                         this.setTitle("Panneau de signalisation");
                                         title.setText("Panneau de signalisation");
                                         break;

            case PanneauxRue:  typeDeRequest = Request.EventType.PanneauxRue;
                               this.setTitle("Panneau de nom de rue");
                               title.setText("Panneau de nom de rue");
                               break;

            case Deneigement:  typeDeRequest = Request.EventType.Deneigement;
                               this.setTitle("Déneigement");
                               title.setText("Déneigement");
                               break;

            case NidDePoule:  typeDeRequest = Request.EventType.NidDePoule;
                              this.setTitle("Nid de poule");
                              title.setText("Nid de poule");
                              break;

            case PoubelleRecup:  typeDeRequest = Request.EventType.PoubelleRecup;
                                 this.setTitle("Poubelle/Récupération remplie");
                                 title.setText("Poubelle/Récupération remplie");
                                 break;

            case Stationnement:  typeDeRequest = Request.EventType.Stationnement;
                                 this.setTitle("Stationnement illégal");
                                 title.setText("Stationnement illégal");
                                 break;

            case Lampadaire:  typeDeRequest = Request.EventType.Lampadaire;
                              this.setTitle("Lampadaire");
                              title.setText("Lampadaire");
                              break;

            case InfSport:  typeDeRequest = Request.EventType.InfSport;
                            this.setTitle("Infrastructure sportive");
                            title.setText("Infrastructure sportive");
                            break;

            case AbrisBus: typeDeRequest = Request.EventType.AbrisBus;
                           this.setTitle("Abribus");
                           title.setText("Abribus");
                           break;

            case Autre: typeDeRequest = Request.EventType.Autre;
                        this.setTitle("Autre");
                        title.setText("Autre");
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
                Request request = new Request( typeDeRequest, description, location, todaysDate, userId);
                Log.d("POLY", "Creation dune request:");
                Log.d("POLY",  "Type : " +typeDeRequest + ", Description : " + description + ", Position : (" + location.latitude + ":" + location.longitude + "), Date: " + todaysDate);
                 //fin
            }
        });
        }
}
