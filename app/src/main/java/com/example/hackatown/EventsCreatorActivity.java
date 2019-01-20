package com.example.hackatown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;





public class EventsCreatorActivity extends AppCompatActivity {

    public void sendButtonId(Button button) {
        String type = Integer.toString(button.getId());
        Intent intent = new Intent(EventsCreatorActivity.this, EventCreatorPart2.class);
        intent.putExtra("position", getIntent().getStringExtra("position"));
        intent.putExtra("type", type);

        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events_creator);


        final Button button1 = findViewById(R.id.button_feu_circulation);
        final Button button2 = findViewById(R.id.button_panneau);
        final Button button3 = findViewById(R.id.button_deneigement);
        final Button button4 = findViewById(R.id.button_nid_poule);
        final Button button5 = findViewById(R.id.button_poubelle);
        final Button button6 = findViewById(R.id.button_stationnement);
        final Button button7 = findViewById(R.id.button_lampadaire);
        final Button button8 = findViewById(R.id.button_infrastructure_sportive);
        final Button button9 = findViewById(R.id.button_autre);
        final Button button10 = findViewById(R.id.button_nom_rue);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button4);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button5);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button6);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button7);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button8);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button9);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonId(button10);
            }
        });
    }
}





