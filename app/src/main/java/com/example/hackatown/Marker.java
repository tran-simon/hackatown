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
        int iconID = R.mipmap.icon_deneigement_round;
        switch (type)
        {
            case FeuxCiruculation:
                iconID = R.mipmap.icon_traffic_light;
                break;
            case PanneauxSiganlisation:
                iconID = R.mipmap.icon_signalisation;
                break;
            case PanneauxRue:
                iconID = R.mipmap.icon_paneaux_rue;
                break;
            case Deneigement:
                iconID = R.mipmap.icon_deneigement_round;
                break;
            case NidDePoule:
                iconID = R.mipmap.icon_nid_poule;
                break;
            case PoubelleRecup:
                iconID = R.mipmap.icon_poubelle;
                break;
            case Stationnement:
                iconID = R.mipmap.icon_stationnement;
                break;
            case AbrisBus:
                iconID = R.mipmap.icon_abribus;
                break;
            case Lampadaire:
                iconID = R.mipmap.icon_lampadaire;
                break;
            case InfSport:
                iconID = R.mipmap.icon_inf_sport;
                break;

            case Autre: default:
            iconID = R.mipmap.icon_autre;
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
