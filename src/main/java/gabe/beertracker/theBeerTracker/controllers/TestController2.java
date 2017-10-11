package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import gabe.beertracker.theBeerTracker.models.data.dataTools.DataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static gabe.beertracker.theBeerTracker.models.data.dataTools.DataTools.filterLocationsByDistanceWithDistance;


@Controller
@RequestMapping(value = "")
public class TestController2 {

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

    private  ArrayList<HashMap<String, String>> beers;
    private  ArrayList<HashMap<String, String>> breweries;
    private  ArrayList<HashMap<String, String>> geo;
    private  ArrayList<HashMap<String, String>> categories;
    private  ArrayList<HashMap<String, String>> styles;
    //private DataTools loader = new DataTools();

    @RequestMapping(value = "test2")

    public String index
        //       ()
    (Model model)
    {

/*        BeerTag newBeerTag1 = new BeerTag("tag3 light2");
        beerTagDao.save(newBeerTag1);
        BeerTag newBeerTag2 = new BeerTag("tag4 dark2");
        beerTagDao.save(newBeerTag2);
        Location newLocation = new Location("location2", -25.363, 131.044);
        locationDao.save(newLocation);
        List<BeerTag> listBeerTags = new ArrayList<>();
        listBeerTags.add(newBeerTag1);
        Beer newBeer = new Beer("beer3",listBeerTags,newLocation);
        beerDao.save(newBeer);
        listBeerTags.add(newBeerTag2);
        Beer newBeer2 = new Beer("beer4",listBeerTags,newLocation);
        beerDao.save(newBeer2);
        User newUser1 = new User("hash3", "username3");
        User newUser2 = new User("hash4", "username4");
        userDao.save(newUser1);
        userDao.save(newUser2);
        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), newBeer, newUser2, newLocation);
        beerDrinkDao.save(newBeerDrink);*/
       // model.addAttribute("beers", beerDao.findAll());
        model.addAttribute("beers", beerDao.findAll());
       // model.addAttribute("divider", " tags: ");
        model.addAttribute("tags", beerTagDao.findAll());


        return "test/index2";
    }
    @RequestMapping(value = "test/map2")
    public String distance(Model model){
        int radius = 50; //radius of search
        Location currentLoc = new Location("Space Needle", 47.6205, -122.3493); //Space Needle
        Iterable <Location> dbLocations = locationDao.findAll();
        model.addAttribute("locations", filterLocationsByDistanceWithDistance(dbLocations, currentLoc, 50 ));
       // model.addAttribute("distance", loc1.getDistanceMi(loc2));
        return  "test/map2";
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
