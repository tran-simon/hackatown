package com.example.hackatown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);

        Request rr = new Request(Request.EventType.FeuxCiruculation,"das", new LatLng(4, 5), new Date(), 3);
		System.out.println("YEEEEEEE");
        System.out.println(rr.xWwwFormUrlencoded());

        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                startActivity(intent);


            }
        });
    }
}
