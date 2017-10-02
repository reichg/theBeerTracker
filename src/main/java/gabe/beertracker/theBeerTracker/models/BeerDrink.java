package gabe.beertracker.theBeerTracker.models;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import javax.persistence.*;
@Entity
public class BeerDrink {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private LocalDateTime dateOfCreation;

    @NotNull
    private LocalDateTime dateOfDrinking;

    @ManyToOne
    private Beer beer;

    @ManyToOne
    private User user;

    @ManyToOne
    private Location location;

    public BeerDrink() {
        this.dateOfCreation = LocalDateTime.now();
    }

    public BeerDrink(LocalDateTime dateOfDrinking, Beer beer, User user, Location location) {
        this();
        this.dateOfDrinking = dateOfDrinking;
        this.beer = beer;
        this.user = user;
        this.location = location;
    }
}
