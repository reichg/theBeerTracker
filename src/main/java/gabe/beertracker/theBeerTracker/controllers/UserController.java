package gabe.beertracker.theBeerTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class UserController {

    @RequestMapping(value = "register")
    public String register() {

        return "register";
    }

    @RequestMapping(value = "login")
    public String login() {

        return "login";
    }

    @RequestMapping(value = "home")
    public String home(Model model) {


        model.addAttribute("title", "Hello User" );
        return "home";
    }


}
