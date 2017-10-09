package gabe.beertracker.theBeerTracker.controllers;

import com.google.gson.Gson;
import gabe.beertracker.theBeerTracker.models.*;
import gabe.beertracker.theBeerTracker.models.data.*;
import gabe.beertracker.theBeerTracker.models.data.dataTools.CsvDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.time.Month.APRIL;



@Controller
@RequestMapping(value = "")
public class TestController {

/*
    @Controller
    @ResponseBody
    public String index(){
        return "Test!";

    } */
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


    @RequestMapping(value = "test")
  //  @ResponseBody
    public String index
     //       ()
           (Model model)
    {

        BeerTag newBeerTag1 = new BeerTag("tag1 light");
        beerTagDao.save(newBeerTag1);
        BeerTag newBeerTag2 = new BeerTag("tag2 dark");
        beerTagDao.save(newBeerTag2);
        Location newLocation = new Location("location1", -25.363, 131.044);
        locationDao.save(newLocation);
        List<BeerTag> listBeerTags = new ArrayList<>();
        listBeerTags.add(newBeerTag1);
        Beer newBeer = new Beer("beer1",listBeerTags,newLocation);
        beerDao.save(newBeer);
        listBeerTags.add(newBeerTag2);
        Beer newBeer2 = new Beer("beer2",listBeerTags,newLocation);
        beerDao.save(newBeer2);
        User newUser1 = new User("hash1", "username1");
        User newUser2 = new User("hash2", "username2");
        userDao.save(newUser1);
        userDao.save(newUser2);
        BeerDrink newBeerDrink = new BeerDrink(LocalDateTime.now(), newBeer, newUser2, newLocation);
        beerDrinkDao.save(newBeerDrink);
        model.addAttribute("beers", beerDao.findAll());
        model.addAttribute("tags", beerTagDao.findAll());

        loadAllData();
        dataToDB();
       //loadTags();

        return "test/index";
    }


/*    private boolean isTagUniq (BeerTag tag)   {
        Iterable<BeerTag> currentTags = beerTagDao.findAll();
        boolean rez = true;
        for (BeerTag currentTag: currentTags)
        {
            if (tag.getName().equalsIgnoreCase(currentTag.getName()))   {
                rez = false;
                break;
            }
        }
        return rez;
    }*/

    private boolean isBeerNameUniq(String name){
        Iterable<Beer> currentbeers = beerDao.findAll();
        for (Beer beer:  currentbeers){
            if (beer.getName().equalsIgnoreCase(name))
                return false;
        return true;
        }



        return true;
    }

    private Location addNewBrewery(String breweryId){
        String breweryName = findByColumnAndValue("id", breweryId, "name", breweries);
        double latitude = parseDouble(findByColumnAndValue("brewery_id", breweryId, "latitude", geo));
        double longitude = parseDouble(findByColumnAndValue("brewery_id", breweryId, "longitude", geo));
        Iterable<Location> currentLocs = locationDao.findAll();
        for (Location currentLoc: currentLocs)
        {
            if (breweryName.equalsIgnoreCase(currentLoc.getName()))   {
                return currentLoc;
            }
        }
        Location aLocn = new Location(breweryName, latitude, longitude);
        aLocn.setAdministrativeAreaLevel1(findByColumnAndValue("id", breweryId, "state", breweries));
        aLocn.setAdministrativeAreaLevel2(findByColumnAndValue("id", breweryId, "name", breweries));
        aLocn.setStreetNumber(findByColumnAndValue("id", breweryId, "address1", breweries));
        aLocn.setRoute(findByColumnAndValue("id", breweryId, "address2", breweries));
        aLocn.setPostalCode(findByColumnAndValue("id", breweryId, "code", breweries));
        aLocn.setCountry(findByColumnAndValue("id", breweryId, "country", breweries));
        aLocn.setPhone(findByColumnAndValue("id", breweryId, "phone", breweries));
        aLocn.setWebSite(findByColumnAndValue("id", breweryId, "website", breweries));
        aLocn.setDescription(findByColumnAndValue("id", breweryId, "descript", breweries));
        locationDao.save(aLocn);
        return aLocn;
    }

    private ArrayList<BeerTag> addNewCatAndStyle(String catId, String styleId){
            ArrayList <BeerTag> tags = new ArrayList<>();
            CsvDataLoader loader = new CsvDataLoader();
            ArrayList<String> exeptions = new ArrayList<String>();
            exeptions.add("and");
            ArrayList<String> category = loader.splitString(findByColumnAndValue("id", catId, "cat_name", categories));
            ArrayList<String> style = loader.splitString(findByColumnAndValue("id", styleId, "style_name", styles));
            ArrayList<String> tagWords = new ArrayList<>();
            for (String categoryWord: category){
                   if ((!loader.containsString(exeptions, categoryWord)&& (categoryWord.length() >= 3) && (categoryWord.length() <= 45)))
                        tagWords.add(categoryWord);
                    }
            for (String styleWord: style){
                if ((!loader.containsString(exeptions, styleWord))&& (!loader.containsString(tagWords, styleWord))&& (styleWord.length() >= 3) && (styleWord.length() <= 45)   )
                    tagWords.add(styleWord);
                }

            Iterable<BeerTag> currentTags = beerTagDao.findAll();
            //Change to Array
            for(String tagWord : tagWords){
                boolean myflag = true;
                for(BeerTag currentTag: currentTags){
                    if (currentTag.getName().equalsIgnoreCase(tagWord)){
                        tags.add(currentTag);
                        myflag = false;

                    }
                    if (myflag){
                        BeerTag newTag = new BeerTag(tagWord);
                        tags.add(newTag);
                        beerTagDao.save(newTag);

                    }
                }
            }
        return tags;
    }

    private void loadAllData(){
        CsvDataLoader loader = new CsvDataLoader();
        beers = CsvDataLoader.findAll("beer");
        breweries = CsvDataLoader.findAll("breweries");
        geo = CsvDataLoader.findAll("geo");
        categories = CsvDataLoader.findAll("cats");
        styles = CsvDataLoader.findAll("styles");
    }

    public static boolean searchStringIC(String myString,String value)
    {
        int max_iter = myString.length()-value.length();
        if (max_iter>=0)
        {
            for (int i = 0;i <= max_iter;i++)
            {
                if (value.equalsIgnoreCase(myString.substring(i,i+value.length())))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //It searches a value in a given column and returns a value from lcolumn in the same row
    private static String findByColumnAndValue(String column, String value, String lcolumn, ArrayList<HashMap<String, String>> data) {

        //ArrayList<HashMap<String, String>> jobs = new ArrayList<>();


        for (HashMap<String, String> row : data) {

            String aValue = row.get(column);

            if (searchStringIC(aValue,value))
            {
                return row.get(lcolumn);
            }
        }

        return "";
    }




    private void dataToDB() {
        CsvDataLoader loader = new CsvDataLoader();
        for (HashMap<String, String> row : beers) {
            String breweryId = row.get("brewery_id");
            String beerName = row.get("name");
            String beerCat = row.get("cat_id");
            String styleId = row.get("style_id");
            String abv = row.get("abv");
            String beerDesc = row.get("descript");
            if (isBeerNameUniq(beerName)) {
                if (findByColumnAndValue("id", breweryId, "state", breweries).equalsIgnoreCase("washington")) { //only our state
                    Location newBrewery = addNewBrewery(breweryId);
                    Iterable<BeerTag> newTags = addNewCatAndStyle(beerCat, styleId);
                    Beer newBeer = new Beer(beerName, loader.toList(newTags), newBrewery);
                    beerDao.save(newBeer);

                }
            }
        }
    }


  /*  private void loadTags(){
        CsvDataLoader loader = new CsvDataLoader();
        for( String sentence : loader.findAll("name")){
            ArrayList<String> words = loader.splitString(sentence);
            for( String word : words){
                if (word.length() >=3 ) {
                    BeerTag tag = new BeerTag(word.toLowerCase());
                    if (isTagUniq(tag)) {

                        beerTagDao.save(tag);
                    }
                }
            }
        }

    }*/

/*    private void loadAll(){
        CsvDataLoader loader = new CsvDataLoader();
        ArrayList<HashMap<String, String>> allData = loader.findAll();

        for( HashMap<String, String> row : loader.findAll()){
            ArrayList<String> words = loader.splitString(sentence);
            for( String word : words){
                if (word.length() >=3 ) {
                    BeerTag tag = new BeerTag(word.toLowerCase());
                    if (isTagUniq(tag)) {

                        beerTagDao.save(tag);
                    }
                }
            }
        }

    }*/


    @RequestMapping(value = "test/map")
     public String map(Model model){
        Location loc = locationDao.findOne(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //LocalDateTime dateTime = LocalDateTime.of(1986, APRIL, 8, 12, 30);
        //String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
        System.out.println("current_time: " + loc.getDateOfCreation().format(formatter) );
        final Gson gson = new Gson();
        System.out.println("Original Java object : " + loc);
        String json = gson.toJson(loc);
        System.out.println("json : " + json);
        model.addAttribute("location", json);
        //Location loc1 = gson.fromJson(json, Location.class);
        //System.out.println("Reconverted Java object : " + loc);
        return  "test/map";
    }
}
