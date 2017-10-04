package gabe.beertracker.theBeerTracker.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 45)
    private String name;

    @NotNull
    private double latitude;

    @NotNull
    private double longtitude;

 /*   private String streetNumber;

    private String route;

    private String locality;

    private String administrativeAreaLevel2;

    private String administrativeAreaLevel1;

    private String country;

    private String postalCode;*/

    @NotNull
    private transient LocalDateTime dateOfCreation;

    @OneToMany
    @JoinColumn(name ="location_id")
    private transient List<LocationFeedback> locationFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="location_id")
    private transient List<BeerDrink> beerDrinks = new ArrayList<>();

    @ManyToMany(mappedBy = "locations")
    private transient List<Beer> beers;

    public Location() {
        dateOfCreation = LocalDateTime.now();
    }

    public Location(String name, double latitude, double longtitude, List<Beer> beers) {
        this();
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.beers = beers;
    }

    public Location(String name) {
        this();
        this.name = name;
    }

    public Location(String name, double latitude, double longtitude) {
        this();
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public List<LocationFeedback> getLocationFeedbacks() {
        return locationFeedbacks;
    }

    public List<BeerDrink> getBeerDrinks() {
        return beerDrinks;
    }

    public List<Beer> getBeers() {
        return beers;
    }
}