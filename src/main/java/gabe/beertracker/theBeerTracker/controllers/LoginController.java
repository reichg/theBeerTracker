package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@SessionAttributes("userName")
public class LoginController{

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin() {

        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String userName, @RequestParam String password) {
        User retrievedUser = userDao.getUserByUsername(userName);
        if (retrievedUser == null){
            model.addAttribute("unameError", "That username does not exist! Please try again!");
            return "login";
        }
        else if(!password.equals(retrievedUser.getHash())){
            model.addAttribute("pwdError", "Wrong password for that username! Try again.");
            return "login";
        }
        model.addAttribute("userName", userName);
        return "redirect:/userhome/" +retrievedUser.getId();
    }

    @RequestMapping(value = "logout")
    public String logout() {

        return "redirect:/login";
    }

}
