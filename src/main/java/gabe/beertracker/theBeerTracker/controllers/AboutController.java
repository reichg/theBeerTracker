package gabe.beertracker.theBeerTracker.Controllers;

import gabe.beertracker.theBeerTracker.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import java.util.ArrayList;

@Controller
public class AboutController {

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String aboutDisplay(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "about";
        }
        User storedData = (User) session.getAttribute("loggedInUser");
        model.addAttribute("userName", storedData.getUserName());
        model.addAttribute("version", "Version: 0.0.1-a (11/13/17)");
        return "about";
    }
}
