package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
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

    @Autowired
    private UserGameDao userGameDao;

    @RequestMapping(value = "")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "about")
    public String displayAbout() {
        return "about";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String dsiplayRegister(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());

        //model.addAttribute("categories", categoryDao.findAll());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute @Valid User newUser, Errors errors, @RequestParam String userName, Model model, HttpServletRequest request) {


        if (errors.hasErrors()) {
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
        ArrayList<Beer> beerList = beerDao.getBeersTriedByUserIdLast10(storedData.getId());
        model.addAttribute("beer", beer);
        model.addAttribute("userName", storedData.getUserName());
        ArrayList<BeerAndOneLocation> filteredBeers = getBeersWithOneLocation(storedData.getId(), userPosition, beerList);
        model.addAttribute("searchResults", filteredBeers);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);
        final Gson gson = new Gson();
        model.addAttribute("userLocation",gson.toJson(userPosition));
        model.addAttribute("locations", gson.toJson(myLocList.toArray()));
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

        return "userhome";

    }
    @RequestMapping(value = "userhome", method = RequestMethod.POST)
    public String processHome(HttpServletRequest request, Model model, Beer beer, @RequestParam("beerButton") String beerButton){
        if (beerButton.equals("go")){

            return  "locations";
        }
        return "gameplay";
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


        UserGame lastUserGame = userGameDao.getLastUserGameByUserId(user.getId());
        Integer randomBeerId = 0;
        UserGame userGame = new UserGame();
        Beer randomBeer = new Beer();
        if (lastUserGame == null || lastUserGame.getDateOfDiscarding() != null || lastUserGame.getBeerDrink() != null){
            ArrayList<Integer> notTriedBeersIds = beerDao.getBeerIdsNotTriedByUserIdLimited(user.getId());  //COMMENT FOR PRODUCTION
            randomBeerId = notTriedBeersIds.get(new Random().nextInt(notTriedBeersIds.size()));
            randomBeer = beerDao.findOne(randomBeerId);
            userGame = new UserGame(randomBeer, storedData );
        }
        //  ArrayList<Integer> notTriedBeersIds = beerDao.getBeerIdsNotTriedByUserId(user.getId());  //UNCOMMENT FOR PRODUCTION
        else{
            userGame = lastUserGame;
        }

//___________________________________
/*        //create list of all beer Ids
         ArrayList<Integer> allBeerIds = new ArrayList();
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
        }*/
//______________________
        randomBeer = userGame.getBeer();
        model.addAttribute("randomBeer", randomBeer.getName());
        session.setAttribute("beerId",randomBeer.getId());
        userGameDao.save(userGame);
        session.setAttribute("userGame",userGame);

        return "gameplay";

    }

    @RequestMapping(value = "gameplay", method = RequestMethod.POST)
    public String gameplayProcess(HttpServletRequest request, Model model, Beer beer, @RequestParam("beerButton") String beerButton) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session?
        //User user = userDao.findOne(storedData.getId());
        Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
        if (userPosition == null) userPosition = locationDao.findOne(3);
        session.setAttribute("userPosition", userPosition);
        Integer randomBeerId      =  (Integer) session.getAttribute("beerId");
        Beer randomBeer = beerDao.findOne(randomBeerId);

        if (beerButton.equals("yes")){
            model.addAttribute("randomBeer", randomBeer.getName());
            session.setAttribute("beerId",randomBeer.getId());
            model.addAttribute("beer", randomBeer);
            model.addAttribute("userName", storedData.getUserName());
           return  "redirect:/locations";
        }

        if (beerButton.equals("no")){ //game discard
              UserGame userGame = (UserGame) session.getAttribute("userGame");
              userGame.setDateOfDiscarding(LocalDateTime.now());
              userGameDao.save(userGame);
            return  "redirect:/gameplay";
        }



        return "gameplay";

    }





    // 1
    @RequestMapping(value = "locations")
    public String locations(HttpServletRequest request, Model model, Beer beer){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
      //  User user = userDao.findOne(storedData.getId());

        List<Beer> userBeerList = beerDao.getBeersTriedByUserId(storedData.getId());


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
        UserGame userGame = (UserGame)session.getAttribute("userGame");
        BeerDrink beerDrink = new BeerDrink(LocalDateTime.now() , storedRandomBeer, user, location);
        beerDrink.setUserGame(userGame);
        beerDrinkDao.save(beerDrink);
        userGame.setBeerDrink(beerDrink);
        userGameDao.save(userGame);


        return "redirect:/userhome";

    }


        private ArrayList<BeerAndOneLocation> getBeersWithOneLocation(int userId, Location userLocation, ArrayList<Beer> beerList) {
        ArrayList<Location> listLoc = locationDao.getLocationsFromBeerDrinksByUserIdLast10(userId);
            ArrayList<BeerAndOneLocation> filteredBeers = new ArrayList<>();

            for (int i =0; i< beerList.size(); i++){
                BeerAndOneLocation beerAndOneLocation = new BeerAndOneLocation(beerList.get(i), userLocation, listLoc.get(i));
                filteredBeers.add(beerAndOneLocation);
            }



        return (filteredBeers);
    }
}



