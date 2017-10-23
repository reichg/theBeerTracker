package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.Beer;
import gabe.beertracker.theBeerTracker.models.BeerDrink;
import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.BeerDao;
import gabe.beertracker.theBeerTracker.models.data.BeerDrinkDao;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameplayController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BeerDao beerDao;

    @Autowired
    private BeerDrinkDao beerDrinkDao;

    @RequestMapping(value = "mygameplay")
    public String displayRandomBeer(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session

        Iterable<Beer> userBeerList = beerDao.getBeersTriedByUserId(storedData.getId());
        Iterable<Beer> allBeers = beerDao.findAll();


        ArrayList<Integer> allBeerIds = new ArrayList();
        ArrayList<Integer> notTriedBeersIds = new ArrayList();
        Integer randomBeerId = 0;

        //create list of all beer Ids
        for (Beer beer : allBeers) {
            allBeerIds.add(beer.getId());

        }

        //create a list of all beers' id the user has found.
        for (Beer foundBeer : userBeerList) {
            //getting beer IDs that the user has not tried yet.
            if (allBeerIds.contains(foundBeer.getId())) {
                allBeerIds.remove(foundBeer.getId());
            }
        }

        for (Integer beerId : allBeerIds) {

            notTriedBeersIds.add(beerId);
        }

        randomBeerId = notTriedBeersIds.get(new Random().nextInt(notTriedBeersIds.size()));
        Beer randomBeer = beerDao.findOne(randomBeerId);
        model.addAttribute("randomBeer", randomBeer.getName());

        return "gameplay";

    }



}
