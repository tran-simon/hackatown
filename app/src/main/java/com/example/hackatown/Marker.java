package com.example.hackatown;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class Marker {

    private MarkerOptions markerOption = new MarkerOptions();
    private JSONObject info = new JSONObject();
    private String date = "", description = "";
    private Request.EventType type = Request.EventType.Autre;
    private int user_id = 0, id = -1;

    private LatLng position;

    public Marker(JSONObject info) throws JSONException {

        date = info.getString("date");
        description = info.getString("description");
        int type1 = info.getInt("type");
        if (type1 < Request.EventType.values().length)
        {
            type = Request.EventType.values()[type1];
        }
        user_id = info.getInt("user_id");

        String latlng = info.getString("position");

        String positionStrings[] = latlng.split(",");

        position = new LatLng(Double.parseDouble(positionStrings[0]), Double.parseDouble(positionStrings[1]));

        markerOption.position(position);
        id = info.getInt("id");


    }




    public int getIconId(){        
        int iconID = R.drawable.ic_warning_black_24dp;

        switch (type)
        {
            case FeuxCiruculation:
                iconID = R.drawable.icon_feu_circulation;

                break;
            case PanneauxSignalisation:
                iconID = R.drawable.icon_signalisation;
                break;
            case PanneauxRue:
//
                break;
            case Deneigement:
                iconID = R.drawable.icon_deneigement;
                break;
            case NidDePoule:
//                iconID = R.drawable.icon_
                break;
            case PoubelleRecup:
                iconID = R.drawable.icon_poubelle;
                break;
            case Stationnement:
                iconID = R.drawable.icon_stationnement;
                break;
            case AbrisBus:
                iconID = R.drawable.icon_abribus;
                break;
            case Lampadaire:
                iconID = R.drawable.icon_lampadaire;
                break;
            case InfSport:
                iconID = R.drawable.icon_infrastructure_sportive;
                break;

            case Autre: default:
            iconID = R.mipmap.icon_autre;
            break;
        }
        return iconID;
    }


    /**
     * SOURCE: https://stackoverflow.com/questions/42365658/custom-marker-in-google-maps-in-android-with-vector-asset-icon/45564994#45564994
     * @param context
     * @param vectorResId
     * @return
     */
    public BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void setPosition(LatLng latLng) {
        getMarkerOption().position(latLng);
    }

    /**
     * Pour changer la position du marqueur
     *
     * @param lat Latitude
     * @param lon Longitude
     */
    public void setPosition(double lat, double lon) {
        setPosition(new LatLng(lat, lon));
    }


    public MarkerOptions getMarkerOption() {
        return markerOption;
    }


    public void setMarkerOption(MarkerOptions markerOption) {
        this.markerOption = markerOption;
    }

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Request.EventType getType() {
        return type;
    }

    public void setType(Request.EventType type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getPosition() {
        return position;
    }
}
