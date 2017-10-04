package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@RequestMapping(value = "user-registration")
@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "index")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "display", method = RequestMethod.GET)
    public String displayRegistrationForm(Model model){
        model.addAttribute(new User());
        model.addAttribute("title", "Register");
        model.addAttribute("users", userDao.findAll());

        return "register/display";
    }

    @RequestMapping(value = "process", method = RequestMethod.POST)
    public String processRegistrationForm(@ModelAttribute @Valid User newUser, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        userDao.save(newUser);
        return "redirect:home/" + newUser.getId();
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model, User user) {
        model.addAttribute("title", "Login");
        //model.addAttribute("user", userDao.findOne());

        //if (user) {
            //return "home";
        //}
        return "login";
    }
    /*@RequestMapping(value = "login", method = RequestMethod.POST)
    public String Login() {

        return "index";
    }*/

    @RequestMapping(value = "home/{userId}", method = RequestMethod.GET)
    public String home(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", userDao.findOne(userId));
        model.addAttribute("title", "Hello Username");

        return "home";
    }


}
