package gabe.beertracker.theBeerTracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimpleLocation {

    @Id
    @GeneratedValue

    private int id;
    //@NotNull
    private double lat;

    //@NotNull
    private double lng;

    private String name;

    public double getLat() {
        return lat;
    }

    public void setLat(double latitude) {
        this.lat = latitude;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double longitude) {
        this.lng = longitude;
    }

    public SimpleLocation() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
