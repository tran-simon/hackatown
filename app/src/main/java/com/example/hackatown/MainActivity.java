package com.example.hackatown;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
	String mCurrentPhotoPath;
	private File createImageFile() {
		// Create an image file name
		String imageFileName = "JPEG_PICTURE";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = null;
		try {
			image = File.createTempFile(
					imageFileName,  /* prefix */
					".jpg",         /* suffix */
					storageDir      /* directory */
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			photoFile = createImageFile();
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this,
						"com.example.hackatown.fileprovider",
						photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, 1);
				System.out.println("YESS");
			}
		} else {
			System.out.println("MABAD");
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test
        super.onCreate(savedInstanceState);


	   // System.out.println(rr.xWwwFormUrlencoded());

        setContentView(R.layout.activity_main);

	    dispatchTakePictureIntent();

        //Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        //startActivity(intent);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                startActivity(intent);


            }
        });




/*
	    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

	    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
		    if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
			    Request rr = new Request(Request.EventType.FeuxCiruculation, "dés", new LatLng(4, 5), new Date(), 3);
			    System.out.println("YEEEEEEE");

			    //new CallAPI().execute(rr);
				//new GetData().execute(-1);
		    } else {
		    	System.out.println("NO INTERNET");
		    }
	    } else {
		    System.out.println("NO PERMISSION");
	    }*/
    }
}
