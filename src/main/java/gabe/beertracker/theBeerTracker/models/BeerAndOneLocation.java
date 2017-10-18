package gabe.beertracker.theBeerTracker.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.toIntExact;

public class BeerAndOneLocation extends Beer {
    private Location comparatorPoint; //a point for comparing distance
    private Location location; //One location // for more easy sorting in any list

    public BeerAndOneLocation() {
    }

    public BeerAndOneLocation(Beer beer, Location compPoint) {
        this.comparatorPoint = compPoint;
        this.setAbv(beer.getAbv());
        this.setDescription(beer.getDescription());
        this.setLocations(beer.getLocations());
        this.setBeerDrinks(beer.getBeerDrinks());
        this.setBeerFeedbacks(beer.getBeerFeedbacks());
        this.setDateOfCreation(beer.getDateOfCreation());
        this.setName(beer.getName());
        this.setTags(beer.getTags());
        this.setUsers(beer.getUsers());
      }

    public BeerAndOneLocation(Beer beer, Location compPoint, Location oneLocation ){
        this(beer, compPoint);
        this.location = oneLocation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getComparatorPoint() {
        return comparatorPoint;
    }

    public void setComparatorPoint(Location comparatorPoint) {
        this.comparatorPoint = comparatorPoint;
    }

    public Double getDistanceMi(){
        return this.location.getDistanceMi(this.getComparatorPoint());
    }

    public double getDistance(){  //rounded
        return Math.round(this.getDistanceMi()*1000)/1000;
    }

    public static ArrayList<BeerAndOneLocation> convertBeerToBeerAndOneLocation (Beer aBeer, Location compPt){
        ArrayList<BeerAndOneLocation> arList = new ArrayList<>();
        if (aBeer.getLocations() != null){
            for(Location aLoc : aBeer.getLocations()){
                BeerAndOneLocation newLoc = new BeerAndOneLocation(aBeer, compPt, aLoc);
            }

        }
        return arList;
    }

    public static ArrayList<Location> locationsExtract (ArrayList<BeerAndOneLocation> beers){
        ArrayList<Location> myloc = new ArrayList<Location>();
        for (BeerAndOneLocation beer: beers){
            myloc.add(beer.getLocation());
        }
        return myloc;
    }


    public static Comparator<BeerAndOneLocation> BeerDistanceComparator = new Comparator<BeerAndOneLocation>() {

        public int compare(BeerAndOneLocation b1, BeerAndOneLocation b2) {

            return  toIntExact(Math.round((b1.getLocation().getDistanceMi(b1.getComparatorPoint()) - b2.getLocation().getDistanceMi(b2.getComparatorPoint()))*100));


            //ascending order

        }};
}
