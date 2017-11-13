package gabe.beertracker.theBeerTracker.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BeerNameAndScore {
    private String name;
    private Integer score;
    @Id
    private Integer id;

    public BeerNameAndScore() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
