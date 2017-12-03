package gabe.beertracker.theBeerTracker.controllers;
//It's the main class in our app :-)
import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.BeerDao;
import gabe.beertracker.theBeerTracker.models.data.BeerTagDao;
import gabe.beertracker.theBeerTracker.models.data.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class BeerController {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private BeerDao beerDao;

    @Autowired
    private BeerTagDao beerTagDao;




    @RequestMapping(value = "add-beer", method = RequestMethod.GET)
    public String displayAddBeer(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            session.invalidate();
            return "redirect:/login";
        }
        User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session
        Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
        if (userPosition == null) userPosition = locationDao.findOne(3);
        session.setAttribute("userPosition", userPosition);
        final Gson gson = new Gson();
        model.addAttribute("title", "Add beer");
        model.addAttribute(new Beer());
        model.addAttribute("userName", storedData.getUserName());
        model.addAttribute("locations", locationDao.findAll());
        model.addAttribute("userLocation",gson.toJson(userPosition));
        model.addAttribute("allBeerTags", beerTagDao.findAll());
        return "add_beer";
    }

    @RequestMapping(value = "add-beer", method = RequestMethod.POST)
    public String processAddBeer(@ModelAttribute @Valid Beer newBeer, Errors errors, Model model, HttpServletRequest request
            , @RequestParam String locationsForSave, @RequestParam String tagsForSave) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        User storedData = (User) session.getAttribute("loggedInUser"); //should retrieve the stored session
        if (errors.hasErrors()) {
            final Gson gson = new Gson();
            model.addAttribute("title", "Add beer");
         //   model.addAttribute(new Beer());
            Location userPosition = (Location) session.getAttribute("userPosition"); //take userPosition
            model.addAttribute("userName", storedData.getUserName());
            model.addAttribute("locations", locationDao.findAll());
            model.addAttribute("userLocation",gson.toJson(userPosition));
            model.addAttribute("allBeerTags", beerTagDao.findAll());
             return "add_beer";
        }

        Iterable<Beer> beers = beerDao.findAll();
        Integer index = indexString(beers, newBeer.getName());

        if (index == null) {
        Iterable<BeerTag> beerTags =  getTags(tagsForSave);
        Iterable<Location> locations =  getLocations(locationsForSave);
        newBeer.setLocations(toList(locations));
        newBeer.setTags(toList(beerTags));
            beerDao.save(newBeer);
            return "redirect:/userhome";
        }

        model.addAttribute("existingName", "Great minds think alike, this beer already exists");
        return "redirect:/userhome";

    }



    private static Integer indexString (Iterable<Beer> beers, String name){
        int j =0;
        for (Beer beer : beers){
            if (name.equalsIgnoreCase(beer.getName()))
            {
                return j;
            }
            j++;
        }

        return null;
    }
    private Iterable<BeerTag> getTags(String tagsForSearch){
        final Gson gson = new Gson();
        Integer[] currentTagsIds = gson.fromJson(tagsForSearch, Integer[].class);
        Iterable<BeerTag> tags = beerTagDao.findAll(Arrays.asList(currentTagsIds));
        return tags;
    }
    private Iterable<Location> getLocations(String locationsIds){
        final Gson gson = new Gson();
        Integer[] currentTagsIds = gson.fromJson(locationsIds, Integer[].class);
        Iterable<Location> tags = locationDao.findAll(Arrays.asList(currentTagsIds));
        return tags;
    }
    public static <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }
}
