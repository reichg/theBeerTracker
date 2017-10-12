package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    UserDao userDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin() {

        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String userName, @RequestParam String password) {

        Iterable<User> allUsers = userDao.findAll();
        boolean userNameSuccess = false;
        boolean passwordSuccess = false;
        for (User user : allUsers) {
            if (userName.equals(user.getUserName())) {
                if (password.equals(user.getHash())) {
                    userNameSuccess = true;
                    passwordSuccess = true;
                }
                else {
                    userNameSuccess = true;
                    passwordSuccess = false;
                }
            }
        }

        if (userNameSuccess) {
            if (!passwordSuccess) {
                model.addAttribute("pwdError", "Wrong password for that username! Try again.");
                return "login";
            }
            model.addAttribute("userName", userName);
            return "redirect:/userhome";
        }
        model.addAttribute("unameError", "That username does not exist! Please try again!");
        return "login";
    }




    @RequestMapping(value = "logout")
    public String logout() {

        return "redirect:/login";
    }

}
