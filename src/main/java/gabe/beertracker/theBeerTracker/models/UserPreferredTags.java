package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserPreferredTags {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name ="user_preferred_tags_id")
    private List<BeerTag> recordedPreferredTags;


    @ManyToOne
    private User user;

    private LocalDateTime dateOfCreation;

    public UserPreferredTags() {
    }

    public UserPreferredTags(ArrayList<BeerTag> recordedPreferredTags, User aUser) {
        this.recordedPreferredTags = recordedPreferredTags;
        this.dateOfCreation = LocalDateTime.now();
        this.user = aUser;
    }

    public List<BeerTag> getRecordedPreferredTags() {
        return recordedPreferredTags;
    }

    public void setRecordedPreferredTags(List<BeerTag> recordedPreferredTags) {
        this.recordedPreferredTags = recordedPreferredTags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }


}



