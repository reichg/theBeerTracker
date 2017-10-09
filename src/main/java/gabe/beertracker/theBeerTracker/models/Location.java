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

    //@NotNull
    private double latitude;

    //@NotNull
    private double longitude;

    private String streetNumber;

    private String route;

    private String locality;

    private String administrativeAreaLevel2;

    private String administrativeAreaLevel1;

    private String country;

    private String postalCode;

    private String description;

    private String phone;

    private String webSite;

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

    public Location(String name, double latitude, double longitude, List<Beer> beers) {
        this();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.beers = beers;
    }

    public Location(String name) {
        this();
        this.name = name;
    }

    public Location(String name, double latitude, double longitude) {
        this();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLongitude() {
        return longitude;
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdministrativeAreaLevel2() {
        return administrativeAreaLevel2;
    }

    public void setAdministrativeAreaLevel2(String administrativeAreaLevel2) {
        this.administrativeAreaLevel2 = administrativeAreaLevel2;
    }

    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}