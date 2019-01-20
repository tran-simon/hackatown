package com.example.hackatown;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);


	   // System.out.println(rr.xWwwFormUrlencoded());

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

	    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

	    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
		    if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
			    Request rr = new Request(Request.EventType.FeuxCiruculation, "dés", new LatLng(4, 5), new Date(), 3);
			    System.out.println("YEEEEEEE");

//			    new CallAPI().execute(rr);
			   // new GetData().execute(-1);
		    } else {
		    	System.out.println("NO INTERNET");
		    }
	    } else {
		    System.out.println("NO PERMISSION");
	    }
    }
}
