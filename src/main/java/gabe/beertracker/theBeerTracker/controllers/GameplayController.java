package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.data.BeerDao;
import gabe.beertracker.theBeerTracker.models.data.BeerDrinkDao;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GameplayController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BeerDao beerDao;

    @Autowired
    private BeerDrinkDao beerDrinkDao;


}
