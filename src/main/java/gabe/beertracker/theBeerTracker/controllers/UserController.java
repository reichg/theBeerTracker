package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.BeerDao;
import gabe.beertracker.theBeerTracker.models.data.BeerDrinkDao;
import gabe.beertracker.theBeerTracker.models.data.LocationDao;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
//@SessionAttributes("userName")
public class UserController {

    @Autowired
    private BeerDrinkDao beerDrinkDao;

    @Autowired
    private BeerDao beerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    @RequestMapping(value = "")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String dsiplayRegister(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());

        //model.addAttribute("categories", categoryDao.findAll());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute @Valid User newUser,Errors errors, @RequestParam String userName
                                  , Model model, HttpServletRequest request) {
        if (errors.hasErrors() ) {
            return "register";
        }

              Iterable<User> allUsers = userDao.findAll();
        boolean userNameExists = false;
        boolean passwordExists = false;
        for (User user : allUsers) {
            if (newUser.getUserName().equals(user.getUserName())) {
                if (newUser.getHash().equals(user.getHash())) {
                    userNameExists = true;
                    passwordExists = true;
                }

                else {
                    userNameExists = true;
                    passwordExists = false;
                }
            }
        }

            if (!userNameExists) {
                if (!passwordExists) {
                    model.addAttribute("userName", userName);
                    HttpSession session = request.getSession(true);   // the boolean makes it create a new one if it's missing
                    session.setAttribute("loggedInUser", newUser); //should put newUser attributes into session
                    //model.addAttribute("registerSuccess", "You have successfully registered, please login");
                    userDao.save(newUser);
                    return "redirect:/userhome";
                }
                model.addAttribute("userName", userName);
                HttpSession session = request.getSession();   // the boolean makes it create a new one if it's missing
                session.setAttribute("loggedInUser", newUser); //should add the newUser to the session
                //model.addAttribute("registerSuccess", "You have successfully registered, please login");
                userDao.save(newUser);
                return "redirect:/userhome";
            }

            model.addAttribute("existingUsername", "Great minds think alike, username already exists");
            return "register";

    }


    @RequestMapping(value = "userhome")
    public String displayHome(HttpServletRequest request, Model model, Beer beer){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }

        Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
        if(userPosition == null) userPosition = locationDao.findOne(3);
        session.setAttribute("userPosition",userPosition);
      //  Location userPosition = locationDao.findOne(3);
      //  System.out.println("userPosition.getLatitude()=" + userPosition.getLatitude());
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());

        Iterable<Beer> beerList = beerDao.getBeersTriedByUserId(storedData.getId());

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", beerList);
        model.addAttribute("userName", storedData.getUserName());
        ArrayList<BeerAndOneLocation> filteredBeers = getBeersWithOneLocation(storedData.getId(), userPosition);
        model.addAttribute("searchResults", filteredBeers);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);
        final Gson gson = new Gson();
        model.addAttribute("userLocation",gson.toJson(userPosition));
        model.addAttribute("locations", gson.toJson(myLocList.toArray()));
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

        return "userhome";

    }

    @RequestMapping(value = "gameplay")
    public String gameplay(HttpServletRequest request, Model model, Beer beer) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());
        Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
        if (userPosition == null) userPosition = locationDao.findOne(3);
        session.setAttribute("userPosition", userPosition);
        Iterable<Beer> userBeerList = beerDao.getBeersTriedByUserId(storedData.getId()); //found beers list

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", userBeerList);
        model.addAttribute("userName", storedData.getUserName());
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());
        //Iterable<Beer> userBeerList = beerDao.getBeersTriedByUserId(storedData.getId());
        Iterable<Beer> allBeers = beerDao.findAll();


        ArrayList<Integer> allBeerIds = new ArrayList();
        ArrayList<Integer> notTriedBeersIds = new ArrayList();
        Integer randomBeerId = 0;

        //create list of all beer Ids
        for (Beer beers : allBeers) {

            //_________________!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (beers.getId() <= 20)     /// Comment this string for production
                // _________________!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                allBeerIds.add(beers.getId());

        }

        //create a list of all beers' id the user has found.
        for (Beer foundBeer : userBeerList) {
            //getting beer IDs that the user has not tried yet.
            if (allBeerIds.contains(foundBeer.getId())) {
                if (allBeerIds.contains(foundBeer.getId())) {
                    allBeerIds.remove(foundBeer.getId());
                }
            }
        }
        for (Integer beerId : allBeerIds) {

            notTriedBeersIds.add(beerId);
        }

        randomBeerId = notTriedBeersIds.get(new Random().nextInt(notTriedBeersIds.size()));
        Beer randomBeer = beerDao.findOne(randomBeerId);
        model.addAttribute("randomBeer", randomBeer.getName());
        session.setAttribute("beerId",randomBeer.getId());

        return "gameplay";

    }

    // 1
    @RequestMapping(value = "locations", method = RequestMethod.GET)
    public String locations(HttpServletRequest request, Model model, Beer beer, @RequestParam String beerName){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());

        Iterable<Beer> userBeerList = beerDao.getBeersTriedByUserId(storedData.getId());

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", userBeerList);
        model.addAttribute("userName", storedData.getUserName());
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

        Beer storedRandomBeer = beerDao.findOne((Integer)session.getAttribute("beerId"));
        model.addAttribute("beerName", storedRandomBeer.getName());
        List<Location> myLocList= storedRandomBeer.getLocations();
        Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
        if(userPosition == null) userPosition = locationDao.findOne(3);
        session.setAttribute("userPosition",userPosition);
        final Gson gson = new Gson();
        model.addAttribute("userLocation",gson.toJson(userPosition));
        model.addAttribute("locations", gson.toJson(myLocList.toArray()));
        return "locations";

    }

    @RequestMapping(value = "locations" , method = RequestMethod.POST)
    public String locationsProcess(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());
        Beer storedRandomBeer = beerDao.findOne((Integer)session.getAttribute("beerId"));
        List<Location> locations = storedRandomBeer.getLocations();
        Location location = locations.get(0);
        BeerDrink beerDrink = new BeerDrink(LocalDateTime.now() , storedRandomBeer, user, location);
        beerDrinkDao.save(beerDrink);
        return "redirect:/userhome";

    }







        private ArrayList<BeerAndOneLocation> getBeersWithOneLocation(int userId, Location userLocation){
        ArrayList<BeerAndOneLocation> filteredBeers = new ArrayList<>();
        ArrayList<BeerDrink> beerDrinks = beerDrinkDao.getUniqueBeerDrinksByUserId(userId);
        for (BeerDrink beerDrink : beerDrinks){
            BeerAndOneLocation beerAndOneLocation = new BeerAndOneLocation(beerDrink.getBeer(), userLocation, beerDrink.getLocation() );
            filteredBeers.add(beerAndOneLocation);
        }
        return (filteredBeers);
    }
}



