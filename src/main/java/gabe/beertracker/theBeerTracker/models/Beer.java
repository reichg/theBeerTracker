package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
}
