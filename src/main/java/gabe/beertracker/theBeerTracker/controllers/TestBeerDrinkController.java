package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "")
public class TestBeerDrinkController {

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
    private UserGameDao userGameDao;


    @RequestMapping(value = "test3")

    public String test
        //       ()
    (HttpServletRequest request, Model model)
    {




        User newUser1 = new User("hash3", "username3");
        User newUser2 = new User("hash4", "username4");
        userDao.save(newUser1);
        userDao.save(newUser2);
        model.addAttribute("tags", beerTagDao.findAll());
        add10BeersTo10Locations();
     //   Location aLocation =locationDao.findOne(9);

        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), beerDao.findOne(3), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink);
        BeerDrink newBeerDrink2 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(3), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink2);
        BeerDrink newBeerDrink3 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(4), userDao.findOne(1), locationDao.findOne(4));
        beerDrinkDao.save(newBeerDrink3);
        BeerDrink newBeerDrink4 = new BeerDrink(LocalDateTime.now(), beerDao.findOne(5), userDao.findOne(1), locationDao.findOne(5));
        beerDrinkDao.save(newBeerDrink4);
        BeerDrink newBeerDrink5= new BeerDrink(LocalDateTime.now(), beerDao.findOne(6), userDao.findOne(1), locationDao.findOne(3));
        beerDrinkDao.save(newBeerDrink5);

        UserGame userGame = new UserGame(beerDao.findOne(10), userDao.findOne(1));
        userGameDao.save(userGame);

        if (request != null){
            String param1 = (String) request.getAttribute("param1");
            if (param1 == "auto"){
                request.setAttribute("param1", "auto");
                return "forward:/test2";
            }

        }

        model.addAttribute("beers", beerDao.findAll());
  //      return "test/index3";
        return "login";

    }



    @RequestMapping(value = "test4")
    public String test2  (Model model) {
        int userId = 1; //You should have userID before. I use it only for test
        User currentUser = userDao.findOne(userId); //Get user object
        List<BeerDrink> listofDrinks = currentUser.getBeerDrinks(); //Get a list of drinks for given user
        ArrayList<Beer> drunkBeers = new ArrayList<>(); // Here we will have a list of drunk beers without duplicates
        for (BeerDrink drink : listofDrinks){
            if (!drunkBeers.contains(drink.getBeer())){
                drunkBeers.add(drink.getBeer());
            }

            model.addAttribute("drunkbeers", drunkBeers); // here we already should have a list of drunk beers
        }

        return "test/index3";

    }

    @RequestMapping(value = "test6")
    public String test6  (Model model) {
        int userId = 1; //You should have userID before. I use it only for test
        User currentUser = userDao.findOne(userId); //Get user object
        List<Beer>  notTriedBeers = beerDao.getBeersNotTriedByUserName(currentUser.getUserName());
        List<Beer>  notTriedBeers2 = beerDao.getBeersNotTriedByUserId(currentUser.getId());

            model.addAttribute("drunkbeers", notTriedBeers); // here we already should have a list of drunk beers
            model.addAttribute("drunkbeers2", notTriedBeers2); // here we already should have a list of drunk beers


        return "test/index3";
    }
    @RequestMapping(value = "load-test-data")
    public String allTestDataSet
    (HttpServletRequest request) {
        request.setAttribute("param1", "auto");
        return "forward:/loadwa";

    }

    private void add10BeersTo10Locations(){

        for (int i =7; i< 17; i++){
            Beer aBeer = beerDao.findOne(i);
            for (int j = 10; j<20; j++){
                if (!aBeer.getLocations().contains(locationDao.findOne(j))){
                    aBeer.getLocations().add(locationDao.findOne(j));
                }
            }
            beerDao.save(aBeer);
        }

    }


}

