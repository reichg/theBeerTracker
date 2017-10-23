package gabe.beertracker.theBeerTracker.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import static java.lang.Math.*;

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

    public Double getDistanceKm(Location point){
        double lon2 = toRadians(this.getLongitude());
        double lon1 = toRadians(point.getLongitude());
        double lat1 = toRadians(this.getLatitude());
        double lat2 = toRadians(point.getLatitude());
        double dlon= lon2 - lon1;
        double dlat= lat2 - lat1;
        double a = pow(sin(dlat/2),2) + cos(lat1) * cos(lat2) * pow(sin(dlon/2),2);
        double c = 2 * atan2(sqrt(a),sqrt(1-a));
        double minLatpoint = min(abs(this.getLatitude()), abs(point.getLatitude()));
        double maxLatpoint = max(abs(this.getLatitude()), abs(point.getLatitude()));
        double midlepoint = (minLatpoint + maxLatpoint)/2;
        //double r = 6371 ;//  6,353 km to 6,384
        double r =  (90 - midlepoint) * 31/90 + 6353;
        return r*c;
    }

    public Double getDistanceMi(Location point){
        return this.getDistanceKm( point)/1.60934;
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

    public void setName(String name) {
        this.name = name;
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

    public void setBeerDrinks(List<BeerDrink> beerDrinks) {
        this.beerDrinks = beerDrinks;
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public  void addBeer(Beer aBeer){
        List<Beer> listBeers = this.getBeers();
        listBeers.add(aBeer);
    }
}