package com.example.hackatown;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        imageView = findViewById(R.id.imageView);



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


        Button delete = findViewById(R.id.btn_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				Log.d("DELETE IT", ""+id);
                new Delete().execute(id);
                finish();
            }
        });



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

        id = objectInfo.getInt("id");

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
                typeString = getString(R.string.feu_circulation);
                break;
            case PanneauxSignalisation:
                typeString = getString(R.string.panneau_signalisation);
                break;
            case PanneauxRue:
                typeString = getString(R.string.panneau_rue);
                break;
            case Deneigement:
                typeString = getString(R.string.deneigement);
                break;
            case NidDePoule:
                typeString = getString(R.string.nid_poule);
                break;
            case PoubelleRecup:
                typeString = getString(R.string.poubelle);
                break;
            case Stationnement:
                typeString = getString(R.string.stationnement);
                break;
            case Lampadaire:
                typeString = getString(R.string.lampadaire);
                break;
            case InfSport:
                typeString = getString(R.string.infrastructure_sportive);
                break;
            case AbrisBus:
                typeString = getString(R.string.abris_bus);
                break;
            case Autre:
                typeString = getString(R.string.autre);
                break;
            default:
                typeString = null;
                break;
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(typeString);

        ((TextView) findViewById(R.id.txt_type)).setText(typeString);
        ((TextView) findViewById(R.id.txt_description)).setText(description);
        ((TextView) findViewById(R.id.txt_position)).setText(position.latitude + ":\n" + position.longitude);
        textView = ((TextView) findViewById(R.id.txt_user));


        new User(new OnDataReceivedListener() {
	        @Override
	        public void OnDataReceived(String data) {
	        	textView.setText(textView.getText() + data);
	        }
        }).execute(user_id);

	    GlideApp.with(this).load("https://dev.concati.me/uploads/" + objectInfo.getInt("id") + ".jpg").into(imageView);




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
