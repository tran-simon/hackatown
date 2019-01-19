package com.example.hackatown;


import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class Marker {
    public enum Type {
        DEFAULT,
        POUBELLE,
        NID_POULE
    }

    private MarkerOptions markerOption = new MarkerOptions();

    public Marker() {
        this(Type.DEFAULT);
    }

    public Marker(Type type) {
        //TODO: Definir les images en fonction du type
        int iconID = 0;
        switch (type)
        {
            case POUBELLE:
                break;
            case NID_POULE:
                break;

            case DEFAULT: default:
            iconID = R.mipmap.ic_warning;
                    break;
        }

        setIcon(iconID);
    }


    public Marker(LatLng position) {
        this();
        setPosition(position);
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



}
