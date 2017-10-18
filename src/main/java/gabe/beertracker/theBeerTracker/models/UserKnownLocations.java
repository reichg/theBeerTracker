package gabe.beertracker.theBeerTracker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
public class UserKnownLocations {

    @Id
    @GeneratedValue
    private int id;

    //private SimpleLocation simpleLocation;

    private LocalDateTime dateOfCreation;

     @ManyToOne
     private User user;

    public UserKnownLocations() {
    }

    public UserKnownLocations( String aa) {
      //  this.simpleLocation = simpleLocation;
        this.dateOfCreation = LocalDateTime.now();
       // this.user = user;
    }

/*    public SimpleLocation getSimpleLocation() {
        return simpleLocation;
    }

    public void setSimpleLocation(SimpleLocation simpleLocation) {
        this.simpleLocation = simpleLocation;
    }*/

    public int getId() {
        return id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

/*    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
