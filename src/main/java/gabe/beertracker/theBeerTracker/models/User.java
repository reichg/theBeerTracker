package gabe.beertracker.theBeerTracker.models;

//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Cascade;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    //@NotNull
    //@Size(min=3, max=25)
    private String firstName;

    //@NotNull
    //@Size(min=3, max=25)
    private String lastName;

    @NotNull
    @Size(min=3, max=25, message = "Password must contain between 3 and 25 characters")
    private String hash; //we will implement hash later. Use like  a string password now.
    private String matchHash;

    @NotNull
    @Size(min=3, max=25, message = "Name must contain between 3 and 25 characters")
    private String userName;

    private LocalDateTime dateOfBirth;

 //   @NotNull
    private LocalDateTime dateOfCreation;

    @OneToMany()
    @JoinColumn(name ="user_id")
  //  @Cascade(value = { CascadeType., CascadeType.DELETE })
    private List<BeerFeedback> beerFeedbacks = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name ="user_id")
    private List<LocationFeedback> locationFeedbacks = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name ="user_id")
    private List<BeerDrink> beerDrinks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<UserKnownLocation> userKnownLocations = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name ="user_id")
    private List<UserPreferredTags> userPreferredTags = new ArrayList<>();

    @ManyToMany()
    private List<Beer> favoriteBeers;

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<UserGame> userGames = new ArrayList<>();

    //private List<BeerTag> preferencesForSearch;



    public User() {
        dateOfCreation = LocalDateTime.now();
        List<BeerFeedback> beerFeedbacks = new ArrayList<>();
        List<LocationFeedback> locationFeedbacks = new ArrayList<>();
        List<BeerDrink> beerDrinks = new ArrayList<>();
        List<Beer> beers = new ArrayList<>();
        this.beerFeedbacks = beerFeedbacks;
        this.locationFeedbacks = locationFeedbacks;
        this.beerDrinks = beerDrinks;
     //   this.favoriteBeers = beers;
       // this.dateOfBirth = LocalDateTime.now();
    }

    public User(String hash, String userName) {
        this();
        this.hash = hash;
        this.matchHash = null;
        this.userName = userName;

    }

    public User(String username) {

    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getHash() {
        return hash;
    }

    public String getMatchHash() { return matchHash; }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setMatchHash(String matchHash) { this.matchHash = matchHash; }

    public void setUserName(String userName) {
        this.userName = userName.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public List<BeerDrink> getBeerDrinks() {
        return beerDrinks;
    }

    public void setBeerDrinks(List<BeerDrink> beerDrinks) {
        this.beerDrinks = beerDrinks;
    }

    public List<Beer> getFavoriteBeers() {
        return favoriteBeers;
    }

    public void setFavoriteBeers(List<Beer> favoriteBeers) {
        this.favoriteBeers = favoriteBeers;
    }

    public List<UserKnownLocation> getUserKnownLocations() {
        return userKnownLocations;
    }

    public void setUserKnownLocations(List<UserKnownLocation> userKnownLocations) {
        this.userKnownLocations = userKnownLocations;
    }

    public List<UserPreferredTags> getUserPreferredTags() {
        return userPreferredTags;
    }

    public void setUserPreferredTags(List<UserPreferredTags> userPreferredTags) {
        this.userPreferredTags = userPreferredTags;
    }

    public List<UserGame> getUserGames() {
        return userGames;
    }

    public void setUserGames(List<UserGame> userGames) {
        this.userGames = userGames;
    }


}
