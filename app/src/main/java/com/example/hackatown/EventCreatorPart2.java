package com.example.hackatown;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class EventCreatorPart2 extends AppCompatActivity {


    private Boolean isOthersSelected = false;
    //Paramètre et méthodes pour la prise de photo
    private final int REQUEST_PICTURE = 324;
    //Truc pour la photo
    String mCurrentPhotoPath;

    private ImageView imageView2;

    private File createImageFile() {
        // Create an image file name
        String imageFileName = "JPEG_PICTURE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try
        {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_PICTURE);
                System.out.println("YESS");
            }
        }
        else
        {
            System.out.println("MABAD");
        }
    }
    //Fin paramètre et méthodes pour la prise de photo


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK)
        {
            File file = new File(mCurrentPhotoPath);
            imageView2.setImageURI(Uri.fromFile(file));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Constructeur
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator_part2);

        //Parametre
        Button sendRequestBtn = findViewById(R.id.btn_send);
        imageView2 = findViewById(R.id.imageView2);


//        ActionBar toolbar = getSupportActionBar();

        TextView editableType = findViewById(R.id.EventName);
        editableType.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        Request.EventType type = Request.EventType.values()[getIntent().getIntExtra("type", 0)];
        String locationStrings[] = getIntent().getStringExtra("position").split(",");
        final Request.EventType typeDeRequest;
        final LatLng location = new LatLng(Double.parseDouble(locationStrings[0]), Double.parseDouble(locationStrings[1]));

        //Définir le type de l'event
        switch (type)
        {
            case FeuxCiruculation:
                typeDeRequest = Request.EventType.FeuxCiruculation;
//                toolbar.setTitle(R.string.feu_circulation);
                break;
            case PanneauxSiganlisation:
                typeDeRequest = Request.EventType.PanneauxSiganlisation;
//                toolbar.setTitle(R.string.panneau_signalisation);
                break;
            case PanneauxRue:
                typeDeRequest = Request.EventType.PanneauxRue;
//                toolbar.setTitle(R.string.panneau_rue);
                break;
            case Deneigement:
                typeDeRequest = Request.EventType.Deneigement;
//                toolbar.setTitle(R.string.deneigement);
                break;
            case NidDePoule:
                typeDeRequest = Request.EventType.NidDePoule;
//                toolbar.setTitle(R.string.nid_poule);
                break;
            case PoubelleRecup:
                typeDeRequest = Request.EventType.PoubelleRecup;
//                toolbar.setTitle(R.string.poubelle);
                break;
            case Stationnement:
                typeDeRequest = Request.EventType.Stationnement;
//                toolbar.setTitle(R.string.stationnement);
                break;
            case Lampadaire:
                typeDeRequest = Request.EventType.Lampadaire;
//                toolbar.setTitle(R.string.lampadaire);
                break;
            case InfSport:
                typeDeRequest = Request.EventType.InfSport;
//                toolbar.setTitle(R.string.infrastructure_sportive);
                break;
            case AbrisBus:
                typeDeRequest = Request.EventType.AbrisBus;
//                toolbar.setTitle(R.string.abris_bus);
                break;
            case Autre:
                typeDeRequest = Request.EventType.Autre;
//                toolbar.setTitle(R.string.autre);
                isOthersSelected = true;
                editableType.setVisibility(View.VISIBLE);
                editableType.setEnabled(true);
                break;
            default:
                typeDeRequest = null;
                break;
        }


        //Écouteurs de bouton
        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                EditText editableDescription = findViewById(R.id.plain_text_input);
                String description = editableDescription.getText().toString();
                if (isOthersSelected)
                {
                    description = "Requête de type : " + editableType.getText().toString() + ". " + description;
                }
                Date todaysDate = new Date();
                int userId = 1;
                Request request = new Request(typeDeRequest, description, location, todaysDate, userId, mCurrentPhotoPath);
                new CallAPI(new OnDataReceivedListener() {
                    @Override
                    public void OnDataReceived(String data) {
                        Toast.makeText(EventCreatorPart2.this, getString(R.string.msg_sent), Toast.LENGTH_SHORT).show();

                    }
                }).execute(request);
                EventCreatorPart2.this.finish();
                //fin
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                try
                {
                    dispatchTakePictureIntent();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                //fin
            }
        });


    }

}
