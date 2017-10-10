package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("userName")
public class LoginController {

    @Autowired
    LoginService service;

    @Autowired
    UserDao userDao;


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin() {

        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String userName, @RequestParam String password) {

        if (!service.validateUser(userName, password)) {

            model.addAttribute("errorMessage", "Invalid Credentials");
            return "login";
        }

        model.addAttribute("userName", userName);
        return "redirect:/userhome";


    }

    @RequestMapping(value = "logout")
    public String logout() {

        return "redirect:/login";
    }

}
