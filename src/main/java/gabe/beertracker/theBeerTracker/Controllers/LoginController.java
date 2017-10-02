package gabe.beertracker.theBeerTracker.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

    @RequestMapping(value = "register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "home")
    public String home() {
        return "home";
    }


}
