package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class UserKnownLocation {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(mappedBy = "userKnownLocation", cascade = CascadeType.ALL)
    private SimpleLocation simpleLocation;

    private LocalDateTime dateOfCreation;

     @ManyToOne
     private User user;

    public UserKnownLocation() {
    }

    public UserKnownLocation(String aa) {
      //  this.simpleLocation = simpleLocation;
        this.dateOfCreation = LocalDateTime.now();
       // this.user = user;
    }

    public SimpleLocation getSimpleLocation() {
        return simpleLocation;
    }

    public void setSimpleLocation(SimpleLocation simpleLocation) {
        this.simpleLocation = simpleLocation;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
