package com.example.hackatown;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.location.Location;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class EventCreatorPart2 extends AppCompatActivity {

    //Truc pour la photo
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

    private void dispatchTakePictureIntent() throws IOException {
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
    //Fin truc photo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);

        //Parametre
        Button sendRequestBtn = findViewById(R.id.sendRequestBtn);
        ImageButton takePicture = findViewById(R.id.insertImgBtn);
        Bundle extras = getIntent().getExtras();
        TextView title = findViewById(R.id.EventName);
        Request.EventType type = Request.EventType.values()[getIntent().getIntExtra("type", 0)] ;
        final Request.EventType typeDeRequest;
        String locationStrings[] = getIntent().getStringExtra("position").split(",");
        final LatLng location = new LatLng(Double.parseDouble(locationStrings[0]), Double.parseDouble(locationStrings[1]));

        //Définir le type de l'event
        switch (type) {
            case FeuxCiruculation:  typeDeRequest = Request.EventType.FeuxCiruculation; title.setText("Feu de circulation");
            break;
            case PanneauxSiganlisation:  typeDeRequest = Request.EventType.PanneauxSiganlisation;  title.setText("Panneau de signalisation");
                break;
            case PanneauxRue:  typeDeRequest = Request.EventType.PanneauxRue;  title.setText("Panneau de nom de rue");
                break;
            case Deneigement:  typeDeRequest = Request.EventType.Deneigement;  title.setText("Déneigement");
                break;
            case NidDePoule:  typeDeRequest = Request.EventType.NidDePoule;  title.setText("Nid de poule");
                break;
            case PoubelleRecup:  typeDeRequest = Request.EventType.PoubelleRecup;  title.setText("Poubelle/Récupération remplie");
                break;
            case Stationnement:  typeDeRequest = Request.EventType.Stationnement;  title.setText("Stationnement illégal");
                break;
            case Lampadaire:  typeDeRequest = Request.EventType.Lampadaire;  title.setText("Lampadaire");
                break;
            case InfSport:  typeDeRequest = Request.EventType.InfSport;  title.setText("Infrastructure sportive");
                break;
            case AbrisBus: typeDeRequest = Request.EventType.AbrisBus;  title.setText("Abribus");
                break;
            case Autre: typeDeRequest = Request.EventType.Autre;  title.setText("Autre");
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

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                try {
                    dispatchTakePictureIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //fin
            }
        });


    }

}
