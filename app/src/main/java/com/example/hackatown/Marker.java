package com.example.hackatown;


import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class Marker {

    private MarkerOptions markerOption = new MarkerOptions();

    public Marker() {
        this(Request.EventType.Autre);
    }

    public Marker(Request.EventType type) {
        //TODO: Definir les images en fonction du type
        int iconID = 0;
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
