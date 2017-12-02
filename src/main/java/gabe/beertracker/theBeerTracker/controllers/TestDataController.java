package gabe.beertracker.theBeerTracker.Controllers;

import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "")
public class TestDataController {

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
    @Autowired
    private UserPreferredTagsDao userPreferredTagsDao;

    private ArrayList<HashMap<String, String>> beers;
    private ArrayList<HashMap<String, String>> breweries;
    private ArrayList<HashMap<String, String>> geo;
    private ArrayList<HashMap<String, String>> categories;
    private ArrayList<HashMap<String, String>> styles;

    @RequestMapping(value = "test")
    //  @ResponseBody
    public String index
        //       ()
    (Model model) {

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
        ArrayList<BeerTag> arrayOfTags= new ArrayList<BeerTag>();
        arrayOfTags.add(beerTagDao.findOne(1));
        arrayOfTags.add(beerTagDao.findOne(2));
        UserPreferredTags userPreferredTags = new UserPreferredTags(arrayOfTags, userDao.findOne(1));
        userPreferredTagsDao.save(userPreferredTags);
        model.addAttribute("beers", beerDao.findAll());

        model.addAttribute("beers", beerDao.findAll());

        return "test/index";
    }
}
