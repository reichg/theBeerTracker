package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.Beer;
import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.BeerDao;
import gabe.beertracker.theBeerTracker.models.data.BeerDrinkDao;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String processRegister(@ModelAttribute @Valid User newUser, @RequestParam String userName,
                                  Errors errors, Model model, HttpServletRequest request) {

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
        if (errors.hasErrors() ) {
            return "register";
        }

        else{
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
    }


    @RequestMapping(value = "userhome")
    public String displayHome(HttpServletRequest request, Model model, Beer beer){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());

        Iterable<Beer> beerList = beerDao.getBeersTriedByUserId(storedData.getId());

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", beerList);
        model.addAttribute("userName", storedData.getUserName());
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

        return "userhome";

    }

    @RequestMapping(value = "gameplay")
    public String gameplay(HttpServletRequest request, Model model, Beer beer){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User)session.getAttribute("loggedInUser"); //should retrieve the stored session?
        User user = userDao.findOne(storedData.getId());

        Iterable<Beer> beerList = beerDao.getBeersTriedByUserId(storedData.getId());

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", beerList);
        model.addAttribute("userName", storedData.getUserName());
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

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
        User user = userDao.findOne(storedData.getId());

        Iterable<Beer> beerList = beerDao.getBeersTriedByUserId(storedData.getId());

        model.addAttribute("beer", beer);
        model.addAttribute("beerList", beerList);
        model.addAttribute("userName", storedData.getUserName());
//        model.addAttribute("welcome", "Welcome, " + user.getUserName());

        return "locations";

    }

}



