package gabe.beertracker.theBeerTracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class LocationFeedback {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 45)
    private String title;

    @NotNull
    @Size(min = 10, max = 1000)
    private String text;

    @NotNull
    private LocalDateTime dateOfCreation;

    @ManyToOne
    private User user;

    @ManyToOne
    private Location location;

    public LocationFeedback() {
        dateOfCreation = LocalDateTime.now();
    }

    public LocationFeedback(String title, String text, User user, Location location) {
        this();
        this.title = title;
        this.text = text;
        this.user = user;
        this.location = location;
    }

}