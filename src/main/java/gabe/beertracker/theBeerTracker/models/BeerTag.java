package gabe.beertracker.theBeerTracker.models;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class BeerTag {
    @Id
    @GeneratedValue
    private int id;


   // @Size(min = 3, max = 45)
    private String name;

   // @NotNull
    private LocalDateTime dateOfCreation;

    @ManyToMany (mappedBy = "tags")
    private List<Beer> beers;

  //  @ManyToOne
  //  private UserPreferredTags userPreferredTags;


    public BeerTag() {
        dateOfCreation = LocalDateTime.now();
    }

    public BeerTag(String name) {
        this();
        this.name = name;
    }

    public BeerTag(String name, List<Beer> beers) {
        this();
        this.name = name;
        this.beers = beers;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
