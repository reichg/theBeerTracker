package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.APRIL;

@Controller
@RequestMapping(value = "")
public class TestController {

/*
    @Controller
    @ResponseBody
    public String index(){
        return "Test!";

    } */
    @Autowired
    private BeerDao beerDao;
    @Autowired
    private BeerDrinkDao beerDrinkDao;
    @Autowired
    private BeerFeedbackDao beerFeedbackDao;
    @Autowired
    private BeerTagDao beerTagDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationFeedbackDao locationFeedbackDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "test")
  //  @ResponseBody
    public String index
     //       ()
           (Model model)
    {

        BeerTag newBeerTag1 = new BeerTag("tag1 light");
        beerTagDao.save(newBeerTag1);
        BeerTag newBeerTag2 = new BeerTag("tag2 dark");
        beerTagDao.save(newBeerTag2);
        Location newLocation = new Location("location1", -25.363, 131.044);
        locationDao.save(newLocation);
        List<BeerTag> listBeerTags = new ArrayList<>();
        listBeerTags.add(newBeerTag1);
        Beer newBeer = new Beer("beer1",listBeerTags,newLocation);
        beerDao.save(newBeer);
        listBeerTags.add(newBeerTag2);
        Beer newBeer2 = new Beer("beer2",listBeerTags,newLocation);
        beerDao.save(newBeer2);
        User newUser1 = new User("hash1", "username1");
        User newUser2 = new User("hash2", "username2");
        userDao.save(newUser1);
        userDao.save(newUser2);
        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), newBeer, newUser2, newLocation);
        beerDrinkDao.save(newBeerDrink);
        model.addAttribute("beers", beerDao.findAll());

        return "test/index";
    }

    @RequestMapping(value = "test/map")
     public String map(Model model){
        Location loc = locationDao.findOne(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //LocalDateTime dateTime = LocalDateTime.of(1986, APRIL, 8, 12, 30);
        //String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
        System.out.println("current_time: " + loc.getDateOfCreation().format(formatter) );
        final Gson gson = new Gson();
        System.out.println("Original Java object : " + loc);
        String json = gson.toJson(loc);
        System.out.println("json : " + json);
        model.addAttribute("location", json);
        //Location loc1 = gson.fromJson(json, Location.class);
        //System.out.println("Reconverted Java object : " + loc);
        return  "test/map";
    }
}
