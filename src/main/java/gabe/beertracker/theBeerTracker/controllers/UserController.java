package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String dsiplayRegister(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute(new User());
        //model.addAttribute("categories", categoryDao.findAll());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute @Valid User newUser,
                                  Errors errors, Model model) {
        userDao.save(newUser);
        return "redirect:";

    }



    @RequestMapping(value = "login")
    public String displayLogin() {

        return "login";
    }

    /*@RequestMapping(value = "login", method = RequestMethod.POST)
    public String Login() {

        return "index";
    }*/

    /*@RequestMapping(value = "home")
    public String home(Model model) {


        model.addAttribute("welcome", "Hello: " + userDao.findOne(userName));
        return "home";
    }
*/

}
