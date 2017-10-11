package gabe.beertracker.theBeerTracker.models;

import gabe.beertracker.theBeerTracker.models.Location;

public class LocationAndDistance extends Location {
    private double distance;


    public LocationAndDistance() {
    }

    public LocationAndDistance(Location point, double distance) {
        this.distance = distance;
        setAdministrativeAreaLevel1(point.getAdministrativeAreaLevel1());
        setAdministrativeAreaLevel2(point.getAdministrativeAreaLevel2());
        setBeerDrinks(point.getBeerDrinks());
        setBeers(point.getBeers());
        setCountry(point.getCountry());
        setDescription(point.getDescription());
        setLocality(point.getLocality());
        setPhone(point.getPhone());
        setPostalCode(point.getPostalCode());
        setRoute(point.getRoute());
        setStreetNumber(point.getStreetNumber());
        setWebSite(point.getWebSite());
        setName(point.getName());

    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


}
