package gabe.beertracker.theBeerTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AboutController {
    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String aboutDisplay(Model model) {
        model.addAttribute("version", "Version: 0.0.1-a (11/13/17)");

        return "about";
    }
}
