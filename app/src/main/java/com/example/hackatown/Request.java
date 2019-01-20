package com.example.hackatown;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

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
    public Request(EventType type, String description, LatLng position, Date date , int user_id){
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

    public LatLng getPosition() {
    	return position;
    }

    public int getUserID() {
    	return user_id;
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
    	//https://stackoverflow.com/a/8129350
	    return String.format(Locale.US, "type=%d&date=%d&position=%f,%f&description=%s&user_id=%d",
			    type.ordinal(), date.getTime(), position.latitude, position.longitude, description, user_id);
    }
}
