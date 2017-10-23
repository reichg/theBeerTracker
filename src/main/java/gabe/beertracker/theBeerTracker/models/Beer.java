package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Beer {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    //@Size(min=3, max=25)
    private String name;

    @NotNull
    private LocalDateTime dateOfCreation;

    @OneToMany
    @JoinColumn(name ="beer_id")
    private List<BeerFeedback> beerFeedbacks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name ="beer_id")
    private List<BeerDrink> beerDrinks = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    private List<BeerTag> tags;

    @ManyToMany(mappedBy = "favoriteBeers")
    private List<User> users;

    @ManyToMany
    private List<Location> locations;

    @OneToMany
    @JoinColumn(name ="beer_id")
    private List<UserGame> userGame;

    private String description;

    private String abv; //Alcohol By Volume

    public Beer() {
        this.dateOfCreation = LocalDateTime.now();
        List<Location> locations = new ArrayList<>();
        this.locations = locations;

    }

    public Beer(String name, List<BeerTag> tags, List<Location> locations) {
        this();
        this.name = name;
        this.tags = tags;
        this.locations = locations;
    }

    public int getId() {
        return id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Beer(String name, List<BeerTag> tags, Location location) {
        this();
       // List<Location> locations = new ArrayList<>();
        this.locations.add(location);
        this.name = name;
        this.tags = tags;
      //  this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public List<BeerTag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
       StringBuilder rez = new StringBuilder();
       rez.append(this.name);
       for(BeerTag tag : tags)
       {
           rez.append(" ");
           rez.append(tag.getName());
       }

        return rez.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setBeerFeedbacks(List<BeerFeedback> beerFeedbacks) {
        this.beerFeedbacks = beerFeedbacks;
    }

    public void setBeerDrinks(List<BeerDrink> beerDrinks) {
        this.beerDrinks = beerDrinks;
    }

    public void setTags(List<BeerTag> tags) {
        this.tags = tags;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public List<BeerFeedback> getBeerFeedbacks() {
        return beerFeedbacks;
    }

    public List<BeerDrink> getBeerDrinks() {
        return beerDrinks;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserGame> getUserGame() {
        return userGame;
    }

    public void setUserGame(List<UserGame> userGame) {
        this.userGame = userGame;
    }
}
