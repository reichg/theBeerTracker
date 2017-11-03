package gabe.beertracker.theBeerTracker.models;

public class BeerNameAndScore {
    private String beerName;
    private int score;



    private int id;



    public BeerNameAndScore() {
    }

    public BeerNameAndScore(String beerName, Integer score) {
        this.beerName = beerName;
        this.score = score;
    }

    public String getBeerName() {
        return beerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
