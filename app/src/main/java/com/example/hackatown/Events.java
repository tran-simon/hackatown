package com.example.hackatown;

import java.util.Date;

public class Events {

    //Paramètre de l'évènement
    public enum EventType {FeuxCiruculation,PanneauxSiganlisation,PanneauxRue,Deneigement,NidDePoule,PoubelleRecup,Stationnement,AbrisBus,Lampadaire,InfSport,Autre}
    private EventType type;
    private String description;
    private double latitude;
    private double longitude;
    private int eventId;
    private Date date;
    //private Image image; TODO

    //Constructor
    public Events(EventType type, String description, double latitude, double longitude, Date date /*Image image TODO*/){
        this.type = type;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        /*this.image = image; TODO*/
    }

    //Getter and Setter
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
