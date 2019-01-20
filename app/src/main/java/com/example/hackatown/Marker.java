package com.example.hackatown;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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

        initIcon();

    }
    public void initIcon(){
        int iconID = R.mipmap.img_nid_poule;
        switch (type)
        {
            case FeuxCiruculation:
                break;
            case PanneauxSiganlisation:
                break;
            case PanneauxRue:
                break;
            case Deneigement:
                break;
            case NidDePoule:
                break;
            case PoubelleRecup:
                break;
            case Stationnement:
                break;
            case AbrisBus:
                break;
            case Lampadaire:
                break;
            case InfSport:
                break;

            case Autre: default:
//            iconID = R.mipmap.ic_warning;
            break;
        }

        setIcon(iconID);
    }





    /**
     * Permet de changer l'icone
     *
     * @param id id de l'image (R.drawable.ic_warning_black_24dp)
     */
    public void setIcon(int id) {
        getMarkerOption().icon(BitmapDescriptorFactory.fromResource(id));
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
