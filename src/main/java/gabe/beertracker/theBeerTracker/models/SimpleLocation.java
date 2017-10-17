package gabe.beertracker.theBeerTracker.models;

public class SimpleLocation {
    //@NotNull
    private double lat;

    //@NotNull
    private double lng;

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
}
