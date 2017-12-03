package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
public class LocationController {

    @Autowired
    private LocationDao locationDao;

    @RequestMapping(value = "add-location", method = RequestMethod.GET)
    public String displayAddLocation(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            session.invalidate();
            return "redirect:/login";
        }
        User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session
        model.addAttribute("userName", storedData.getUserName());
        model.addAttribute("title", "Add location");
        model.addAttribute(new Location());
        return "location";
    }

    @RequestMapping(value = "add-location", method = RequestMethod.POST)
    public String processAddLocation(@ModelAttribute @Valid Location newLocation, Errors errors, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        if (errors.hasErrors()) {
            User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session
            model.addAttribute("userName", storedData.getUserName());
             return "location";
        }

//        Location existingLocation = locationDao.getLocationByName(newLocation.getName());
        Iterable<Location> locations = locationDao.findAll();
        Integer index = indexString(locations, newLocation.getName());

//        if (existingLocation == null) {
        if (index == null) {
            locationDao.save(newLocation);
            return "redirect:/userhome";
        }

        model.addAttribute("existingName", "Great minds think alike, this location already exists");
        return "redirect:/userhome";

    }

    private static Integer indexString (Iterable<Location> locations, String name){
        int j =0;
        for (Location location : locations){
            if (name.equalsIgnoreCase(location.getName()))
            {
                return j;
            }
            j++;
        }

        return null;
    }

}
