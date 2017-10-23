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

    @OneToOne
    @JoinColumn(name = "user_game_id")
    private UserGame userGame;

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

    public LocalDateTime getDateOfDrinking() {
        return dateOfDrinking;
    }

    public void setDateOfDrinking(LocalDateTime dateOfDrinking) {
        this.dateOfDrinking = dateOfDrinking;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UserGame getUserGame() {
        return userGame;
    }

    public void setUserGame(UserGame userGame) {
        this.userGame = userGame;
    }


}
