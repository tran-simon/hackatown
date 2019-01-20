package com.example.hackatown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EventsCreatorActivity extends AppCompatActivity {


    public void sendButtonId(Events.EventType event) {
        Intent intent = new Intent(EventsCreatorActivity.this, EventCreatorPart2.class);
        intent.putExtra("id", event);
        Log.d("Test", event.toString());
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events_creator);
        this.setTitle("Événement");


        final Button button1 = findViewById(R.id.button_feu_circulation);
        final Button button2 = findViewById(R.id.button_panneau);
        final Button button3 = findViewById(R.id.button_nom_rue);
        final Button button4 = findViewById(R.id.button_deneigement);
        final Button button5 = findViewById(R.id.button_nid_poule);
        final Button button6 = findViewById(R.id.button_poubelle);
        final Button button7 = findViewById(R.id.button_stationnement);
        final Button button8 = findViewById(R.id.button_lampadaire);
        final Button button9 = findViewById(R.id.button_infrastructure_sportive);
        final Button button10 = findViewById(R.id.button_abribus);
        final Button button11 = findViewById(R.id.button_autre);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.FeuxCiruculation;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.PanneauxSiganlisation;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.PanneauxRue;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.Deneigement;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.NidDePoule;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.PoubelleRecup;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.Stationnement;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.Lampadaire;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.InfSport;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.AbrisBus;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Events.EventType eventButton = Events.EventType.Autre;
                Log.d("TEST", eventButton.toString());
                sendButtonId(eventButton);
            }
        });
    }
}





