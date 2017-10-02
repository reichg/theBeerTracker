package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "register")
    public String Register() {

        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String provessRegister(@ModelAttribute @Valid User newUser, Model model) {

        userDao.save(newUser);
        return "index";
    }

    @RequestMapping(value = "login")
    public String displayLogin() {

        return "login";
    }

    @RequestMapping(value = "login")
    public String processLogin() {

        return "index";
    }

    @RequestMapping(value = "home")
    public String home(Model model) {


        model.addAttribute("title", "Hello User" );
        return "home";
    }


}
