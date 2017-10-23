package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.BeerDrinkDao;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;


@Controller
//@SessionAttributes("userName")
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    private BeerDrinkDao beerDrinkDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin() {

        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String userName, @RequestParam String password
      , @RequestParam Optional<String> myPosition , HttpServletRequest request)
    {
        User retrievedUser = userDao.getUserByUsername(userName);
        HttpSession session = request.getSession(false);    //the boolean doesn't auto create, but I can check if null then create session below
        if(session == null) {
            session = request.getSession();
        }
        if (retrievedUser == null){
            model.addAttribute("unameError", "That username does not exist! Please try again!");
            return "login";
        }
        else if(!password.equals(retrievedUser.getHash())){
            model.addAttribute("pwdError", "Wrong password for that username! Try again.");
            return "login";
        }
        model.addAttribute("userName", userName);
        session.setAttribute("loggedInUser", retrievedUser);  //**should be adding retrievedUser in the session**
        if(myPosition.isPresent()){
            if(myPosition.get() != null){
                Location userPosition=getCurrentLocationOfUserNull(myPosition.get());
                if(userPosition!=null)
                session.setAttribute("userPosition",userPosition);
            }
        }

        return "redirect:/userhome";
    }

    @RequestMapping(value = "logout",  method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();  //**should get current session**
        session.invalidate();  //**should remove all attributes from session**


        return "redirect:/login";
    }

    public static Location getCurrentLocationOfUserNull(String myPosition){   //deserialiaze location
        Location currentLoc = new Location();
        final Gson gson = new Gson();
        if (myPosition.length()>0){
            SimpleLocation userPosJS = new SimpleLocation();
            userPosJS = gson.fromJson(myPosition, SimpleLocation.class);
            currentLoc.setLatitude(userPosJS.getLat());
            currentLoc.setLongitude(userPosJS.getLng());
        }
        else {
            currentLoc = null;
        }
        return currentLoc;
    }

}
