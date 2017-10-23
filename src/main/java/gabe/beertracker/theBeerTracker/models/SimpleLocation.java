package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;

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

    @OneToOne
    (mappedBy = "simpleLocation", cascade = CascadeType.ALL)
    private UserKnownLocation userKnownLocation;

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

    public UserKnownLocation getUserKnownLocation() {
        return userKnownLocation;
    }

    public void setUserKnownLocation(UserKnownLocation userKnownLocation) {
        this.userKnownLocation = userKnownLocation;
    }
}
