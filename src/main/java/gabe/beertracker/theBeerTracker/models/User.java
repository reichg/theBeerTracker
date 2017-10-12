package gabe.beertracker.theBeerTracker.models;

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

   // @NotNull
    @Size(min=3, max=25)
    private String firstName;

  //  @NotNull
    @Size(min=3, max=25)
    private String lastName;

    @NotNull
    @Size(min=3, max=25)
    private String hash; //we will implement hash later. Use like  a string password now.

    @NotNull
    @Size(min=3, max=25)
    private String userName;

    private LocalDateTime dateOfBirth;

    @NotNull
    private LocalDateTime dateOfCreation;

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<BeerFeedback> beerFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<LocationFeedback> locationFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="user_id")
    private List<BeerDrink> beerDrinks = new ArrayList<>();

    @ManyToMany
    private List<Beer> favoriteBeers;

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
        this.userName = userName;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<BeerDrink> getBeerDrinks() {
        return beerDrinks;
    }
}
