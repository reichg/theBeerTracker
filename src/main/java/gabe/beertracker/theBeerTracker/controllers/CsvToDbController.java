package gabe.beertracker.theBeerTracker.Controllers;

import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import gabe.beertracker.theBeerTracker.models.data.dataTools.DataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static gabe.beertracker.theBeerTracker.models.data.dataTools.DataTools.*;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;




@Controller
@RequestMapping(value = "")
public class CsvToDbController {

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

    private  ArrayList<HashMap<String, String>> beers;
    private  ArrayList<HashMap<String, String>> breweries;
    private  ArrayList<HashMap<String, String>> geo;
    private  ArrayList<HashMap<String, String>> categories;
    private  ArrayList<HashMap<String, String>> styles;

    /**
     * Load data only for WA state
     */
    @RequestMapping(value = "loadwa")
  //  @ResponseBody
    public String loadwa

           (HttpServletRequest request, Model model)
    {
        loadAllData();
        dataToDB("wa"); //only Washington
        if (request != null){
            String param1 = (String) request.getAttribute("param1");
            if (param1 == "auto"){
                request.setAttribute("param1", "auto");
                return "forward:/test3";
            }

        }

        model.addAttribute("beers", beerDao.findAll());

        return "test/index";
    }
    /**
     * Load data for several additional states
     */
    @RequestMapping(value = "loadall")
    //  @ResponseBody
    public String loadall
        //       ()
    (Model model)
    {
        loadAllData();
        dataToDB("all"); //only Washington
        model.addAttribute("beers", beerDao.findAll());

        return "test/index";
    }
    /**
     * Read in data from a CSV files and store it in  lists
     */
    private void loadAllData(){
        beers = DataTools.findAll("beer");
        breweries = DataTools.findAll("breweries");
        geo = DataTools.findAll("geo");
        categories = DataTools.findAll("cats");
        styles = DataTools.findAll("styles");
    }
    /**
     * Save data in DB
     */
    private void dataToDB(String choise) {
       for (HashMap<String, String> row : beers) {
            String breweryId = row.get("brewery_id");
            String beerName = trimString(row.get("name"),45);
            String beerCat = row.get("cat_id");
            String styleId = row.get("style_id");
            String abv = row.get("abv");
            String beerDesc = trimString(row.get("descript"),255);
            if (isBeerNameUniq(beerName)) {
                if (isGoodState(findByColumnAndValue("id", breweryId, "state", breweries, 1),choise)) { //only our state
                    Location newBrewery = addNewBrewery(breweryId);
                    Iterable<BeerTag> newTags = addNewCatAndStyle(beerCat, styleId);
                    Beer newBeer = new Beer(beerName, toList(newTags), newBrewery);
                    newBeer.setAbv(abv);
                    newBeer.setDescription(beerDesc);
                    beerDao.save(newBeer);

                }
            }
        }
    }
    /**
     * It checks the location  for uniqueness and creates a new one, if necessary
     */

    private Location addNewBrewery(String breweryId){

        String breweryName = trimString(findByColumnAndValue("id", breweryId, "name", breweries, 1),45);
        double latitude = 0;
        double longitude =0;
        if (findByColumnAndValue("brewery_id", breweryId, "latitude", geo, 2)!="") {latitude = parseDouble(findByColumnAndValue("brewery_id", breweryId, "latitude", geo, 2));}
        if (findByColumnAndValue("brewery_id", breweryId, "longitude", geo, 2)!="") longitude = parseDouble(findByColumnAndValue("brewery_id", breweryId, "longitude", geo, 2));
        Iterable<Location> currentLocs = locationDao.findAll();
        for (Location currentLoc: currentLocs)
        {
            if (breweryName.equalsIgnoreCase(currentLoc.getName()))   {
                return currentLoc;
            }
        }

        Location aLocn = new Location(breweryName, latitude, longitude);
        aLocn.setAdministrativeAreaLevel1(findByColumnAndValue("id", breweryId, "state", breweries, 1));
        aLocn.setAdministrativeAreaLevel2(trimString(findByColumnAndValue("id", breweryId, "city", breweries, 1), 45));
        aLocn.setStreetNumber(findByColumnAndValue("id", breweryId, "address1", breweries, 1));
        aLocn.setRoute(findByColumnAndValue("id", breweryId, "address2", breweries, 1));
        aLocn.setPostalCode(findByColumnAndValue("id", breweryId, "code", breweries, 1));
        aLocn.setCountry(findByColumnAndValue("id", breweryId, "country", breweries, 1));
        aLocn.setPhone(findByColumnAndValue("id", breweryId, "phone", breweries, 1));
        aLocn.setWebSite(findByColumnAndValue("id", breweryId, "website", breweries, 1));
        aLocn.setDescription(trimString(findByColumnAndValue("id", breweryId, "descript", breweries, 1), 255));
        locationDao.save(aLocn);
        return aLocn;
    }

    /**
     * It checks the beer name  for uniqueness
     */
    private boolean isBeerNameUniq(String name){
        Iterable<Beer> currentbeers = beerDao.findAll();
        for (Beer beer:  currentbeers){
            if (trimString(beer.getName(), 45).equalsIgnoreCase(name))
                return false;
        return true;
        }
        return true;
    }
    /**
     * It creates the tag list from given IDs (from csv)
     */
    private ArrayList<BeerTag> addNewCatAndStyle(String catId, String styleId){
        ArrayList <BeerTag> tags = new ArrayList<>();
        ArrayList<String> exeptions = new ArrayList<String>();
        exeptions.add("and");
        ArrayList<String> category = splitString(findByColumnAndValue("id", catId, "cat_name", categories, 1));
        ArrayList<String> style = splitString(findByColumnAndValue("id", styleId, "style_name", styles, 1));
        ArrayList<String> tagWords = new ArrayList<>();
        for (String categoryWord: category){
            if ((!containsString(exeptions, categoryWord)&& (categoryWord.length() >= 3) && (categoryWord.length() <= 45)))
                tagWords.add(categoryWord);
        }
        for (String styleWord: style){
            if ((!containsString(exeptions, styleWord))&& (!containsString(tagWords, styleWord))&& (styleWord.length() >= 3) && (styleWord.length() <= 45)   )
                tagWords.add(styleWord);
        }

        for(String tagWord : tagWords){
            tags.add(addNewTag(tagWord));
        }

        return tags;
    }

    /**
     * It checks the tag  for uniqueness and creates a new one, if necessary
     */

    private BeerTag addNewTag (String name) {
        Iterable<BeerTag> tags = beerTagDao.findAll();

        for (BeerTag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name))
                return tag;
        }
        BeerTag newTag = new BeerTag(name);
        return newTag;
    }






}
