package gabe.beertracker.theBeerTracker.models;

import gabe.beertracker.theBeerTracker.models.Location;

import java.util.Comparator;

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



    /*Comparator for sorting the list by distance*/
    public static Comparator<LocationAndDistance> distanceComparator = new Comparator<LocationAndDistance>() {

        public int compare(LocationAndDistance s1, LocationAndDistance s2) {

            double dist1 = s1.getDistance();
            double dist2 = s2.getDistance();
            if ((dist1-dist2) > 0)
                return 1;
            else if ((dist1-dist2) < 0)
                return -1;
            else return 0;
        }};

}



