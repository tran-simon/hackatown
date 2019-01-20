package com.example.hackatown;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventInfoActivity extends AppCompatActivity implements OnDataReceivedListener {
    private String info = "";
    private JSONObject objectInfo = new JSONObject();
    private TextView textView;
    private ImageView imageView;


    private String date = "", description = "";
    private Request.EventType type = Request.EventType.Autre;
    private int id = 0, user_id = 0;
    private LatLng position = new LatLng(0, 0);

    private boolean imageIsFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imageView = findViewById(R.id.imageView);


        textView = findViewById(R.id.txt_scrollable);





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


        GetData getData = new GetData(new OnDataReceivedListener() {
            @Override
            public void OnDataReceived(String data) {
                info = data;
                try
                {
                    loadData();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
        getData.execute(getIntent().getIntExtra("id", 0));



    }


    @Override
    public void OnDataReceived(String data) {
        info = data;
        try
        {
            loadData();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public void loadData() throws JSONException {
        objectInfo = new JSONArray(info).getJSONObject(0);


        date = objectInfo.getString("date");
        description = objectInfo.getString("description");
        type = Request.EventType.values()[objectInfo.getInt("type")];
        user_id = objectInfo.getInt("user_id");

        String latlng = objectInfo.getString("position");

        String positionStrings[] = latlng.split(",");

        position = new LatLng(Double.parseDouble(positionStrings[0]), Double.parseDouble(positionStrings[1]));
        String typeString = null;
        switch (type)
        {
            case FeuxCiruculation:
                typeString = "Feux de signalisation";
                break;
            case PanneauxSiganlisation:
                typeString = "Panneau de signalisation";
                break;
            case PanneauxRue:
                typeString = "Panneau de nom de rue";
                break;
            case Deneigement:
                typeString = "Déneigement";
                break;
            case NidDePoule:
                typeString = "Nid de poule";
                break;
            case PoubelleRecup:
                typeString = "Poubelle/récupération remplie";
                break;
            case Stationnement:
                typeString = "Stationnement illégal";
                break;
            case Lampadaire:
                typeString = "Lampadaire";
                break;
            case InfSport:
                typeString = "Infrastructure sportive";
                break;
            case AbrisBus:
                typeString = "Abrisbus";
                break;
            case Autre:
                typeString = "Autre";
                break;
            default:
                typeString = null;
                break;
        }
        textView.setText(date + "\n\nType de requête: " + typeString + "\n\nDescription: " + description + "\n\nLatitude: " + position.latitude + "\n\nLongitude: " + position.longitude + "\n\nÉmis par " + user_id);
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(30);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        URL url2 = null;
        try
        {
            url2 = new URL("https://dev.concati.me/uploads/" + objectInfo.getInt("id") + ".jpg");
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        ImageDownload imageDownload = new ImageDownload(imageView, new OnDataReceivedListener() {
            @Override
            public void OnDataReceived(String data) {
                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            }
        });
        imageDownload.execute(url2);


    }


    private class ImageDownload extends AsyncTask<URL, Void, Bitmap> {
        private ImageView imageView;

        private OnDataReceivedListener listener;
        public ImageDownload(ImageView imageView, OnDataReceivedListener listener) {

            this.imageView = imageView;
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            URL url2 = urls[0];
            Bitmap bmp = null;
            try
            {
                bmp = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            listener.OnDataReceived(bitmap.toString());
        }
    }
}
