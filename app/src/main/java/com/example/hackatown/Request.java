package com.example.hackatown;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.StringJoiner;

public class Request
{

    //Paramètre de l'évènement
    public enum EventType {FeuxCiruculation,PanneauxSiganlisation,PanneauxRue,Deneigement,NidDePoule,PoubelleRecup,Stationnement,AbrisBus,Lampadaire,InfSport,Autre}
    private EventType type;
    private String    description;
    private LatLng    position;
    private Date      date;
    private int       user_id;
    private int       eventId;
    //private Image image; TODO

    //Constructor
    public Request(EventType type, String description, LatLng position, Date date /*Image image TODO*/, int user_id){
        this.type = type;
        this.description = description;
        this.position = position;
        this.date = date;
        this.user_id = user_id;
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

    String xWwwFormUrlencoded() {
	    return String.format("type= %s  date=%s position=%s description=%s user_id = %d",
			    type, date, position, description, user_id);
    }
}
