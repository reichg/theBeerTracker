package gabe.beertracker.theBeerTracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class UserGame {

        @Id
        @GeneratedValue
        private int id;

        @NotNull
        private LocalDateTime dateOfCreation;

        @ManyToOne
        private Beer beer;

        @ManyToOne
        private User user;

        private LocalDateTime dateOfDiscarding;

        @OneToOne (mappedBy = "userGame", cascade = CascadeType.ALL)
        private BeerDrink beerDrink;

        public UserGame() {
                this.dateOfCreation = LocalDateTime.now();
        }
        public UserGame(Beer aBeer, User aUser) {
                this();
                this.beer = aBeer;
                this.user = aUser;
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

        public LocalDateTime getDateOfDiscarding() {
                return dateOfDiscarding;
        }

        public void setDateOfDiscarding(LocalDateTime dateOfDiscarding) {
                this.dateOfDiscarding = dateOfDiscarding;
        }

        public BeerDrink getBeerDrink() {
                return beerDrink;
        }

        public void setBeerDrink(BeerDrink beerDrink) {
                this.beerDrink = beerDrink;
        }

        public int getId() {
                return id;
        }

        public LocalDateTime getDateOfCreation() {
                return dateOfCreation;
        }
}
