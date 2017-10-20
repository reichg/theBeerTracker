package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {


    @Autowired
    private BeerDao beerDao;
    @Autowired
    private BeerDrinkDao beerDrinkDao;
    @Autowired
    private BeerFeedbackDao beerFeedbackDao;
    @Autowired
    private BeerTagDao beerTagDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private LocationFeedbackDao locationFeedbackDao;
    @Autowired
    private UserDao userDao;
  @Autowired
    private UserPreferredTagsDao userPreferredTagsDao;
    @Autowired
    private UserKnownLocationsDao userKnownLocationsDao;

    @RequestMapping(value = "test5", method = RequestMethod.GET)
    public String searchDisplay(Model model) {
        int userId = 1;
        float maxDistance = 500;
        Location currentLoc = locationDao.findOne(3); // a location of a user only for test
        ArrayList<BeerTag> prefTags = beerTagDao.getPreferredTagsById(userId); //get all preferred tag of current user
        ArrayList<BeerAndOneLocation> filteredBeers = getFilteredBeers(userId, currentLoc, maxDistance, prefTags);//A list of beers filtered by distance and pref tags
        Collections.sort(filteredBeers, BeerAndOneLocation.BeerDistanceComparator);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);
        final Gson gson = new Gson();
        model.addAttribute("locations", gson.toJson(myLocList.toArray()));
        model.addAttribute("userLocation",gson.toJson(currentLoc));
        model.addAttribute("searchResults", filteredBeers);
        model.addAttribute("title", "search results for " + userDao.findOne(userId).getUserName());
        model.addAttribute("allBeerTags", beerTagDao.findAll());
        model.addAttribute("prefTags", transformPrefTagsForModel(prefTags)); // Serialise tags for JS
        return "test/map3";
    }

    @RequestMapping(value = "test5", method = RequestMethod.POST)
    public String searchPost(@RequestParam String myPosition, @RequestParam String tagsForSearch
         ,@RequestParam String tried  ,@RequestParam String distance , Model model) {
        int userId = 1;
        float maxDistance = Float.parseFloat(distance);
        final Gson gson = new Gson();
        System.out.println("tried = " + tried);
        System.out.println("distance = " + distance);
        Location currentLoc = getCurrentLocationOfUser(myPosition);
        ArrayList<BeerTag> prefTags =  getCurrentPrefTagsAndUpdate(tagsForSearch, userId);
        ArrayList<List<Beer>> filteredBeersByOneTag = new ArrayList<>();
        for (BeerTag prefTag : prefTags){
            filteredBeersByOneTag.add(beerDao.getBeersByTag(prefTag.getName()));
        }
        ArrayList<BeerAndOneLocation> filteredBeers = getBeersByTagsAndDistance(filteredBeersByOneTag, currentLoc, maxDistance);
        Collections.sort(filteredBeers, BeerAndOneLocation.BeerDistanceComparator);
        ArrayList<Location> myLocList= BeerAndOneLocation.locationsExtract(filteredBeers);
        String json = gson.toJson(myLocList.toArray());
        model.addAttribute("locations", json);
        model.addAttribute("userLocation",gson.toJson(currentLoc));
        model.addAttribute("searchResults", filteredBeers);
        model.addAttribute("title", "search results for " + userDao.findOne(userId).getUserName());
        model.addAttribute("allBeerTags", beerTagDao.findAll());
        model.addAttribute("prefTags", transformPrefTagsForModel(prefTags));
        return "test/map3";
    }
//
    private  ArrayList<BeerAndOneLocation> getBeersByTagsAndDistance( ArrayList<List<Beer>> filteredBeersByOneTag, Location currentLoc, float maxDistance){
        ArrayList<BeerAndOneLocation> filteredBeers = new ArrayList<>();
        for (Beer beer : filteredBeersByOneTag.get(0)){
            boolean rez = true;
            for (int i = 1; i < filteredBeersByOneTag.size(); i++){
                if (!filteredBeersByOneTag.get(i).contains(beer)){
                    rez = false;
                    break;
                }
            }
            if (rez) {
                for (Location location : beer.getLocations()){

                    if (location.getDistanceMi(currentLoc) <= maxDistance){
                        BeerAndOneLocation aBeerAndOneLocation = new BeerAndOneLocation(beer, currentLoc, location);
                        filteredBeers.add(aBeerAndOneLocation);

                    }
                }

            }
        }
        return filteredBeers;
    }

    private ArrayList<BeerAndOneLocation> getFilteredBeers(int userId, Location currentLoc, float maxDistance , List<BeerTag> prefTags) {
        ArrayList<List<Beer>> filteredBeersByOneTag = new ArrayList<>();
        for (BeerTag prefTag : prefTags){
            filteredBeersByOneTag.add(beerDao.getBeersByTag(prefTag.getName()));
        }
        return getBeersByTagsAndDistance(filteredBeersByOneTag, currentLoc, maxDistance);
    }

    private Location getCurrentLocationOfUser(String myPosition){
        Location currentLoc = new Location();
        final Gson gson = new Gson();
        if (myPosition.length()>0){
            SimpleLocation userPosJS = new SimpleLocation();
            userPosJS = gson.fromJson(myPosition, SimpleLocation.class);
            currentLoc.setLatitude(userPosJS.getLat());
            currentLoc.setLongitude(userPosJS.getLng());
        }
        else {
            currentLoc = locationDao.findOne(3);
        }
        return currentLoc;
    }

    private ArrayList<BeerTag> getCurrentPrefTagsAndUpdate(String tagsForSearch, int userId){
        final Gson gson = new Gson();
        int[] currentTagsIds = gson.fromJson(tagsForSearch, int[].class);
        ArrayList<BeerTag> prefTags = new ArrayList<>();
        if (currentTagsIds.length >0){
            ArrayList<UserPreferredTags> arrayTags = userPreferredTagsDao.getUserPreferredTagsByUserId(userId);
            for (UserPreferredTags oneTag : arrayTags){
                userPreferredTagsDao.delete(oneTag);
                for (int j = 0; j < currentTagsIds.length; j++){
                    prefTags.add(beerTagDao.findOne(currentTagsIds[j]));
                }

            }

            UserPreferredTags userPreferredTags = new UserPreferredTags(prefTags, userDao.findOne(userId));
            userPreferredTagsDao.save(userPreferredTags);
        }
        else prefTags.add(beerTagDao.findOne(1));
        return prefTags;
    }
    private String transformPrefTagsForModel( ArrayList<BeerTag> prefTags){
        final Gson gson = new Gson();
        ArrayList<Integer> tagsIds = new ArrayList<Integer>();
        for (BeerTag name : prefTags)
            tagsIds.add(name.getId());
        return gson.toJson(tagsIds.toArray());
    }

}