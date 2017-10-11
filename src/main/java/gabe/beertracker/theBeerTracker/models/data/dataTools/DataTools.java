package gabe.beertracker.theBeerTracker.models.data.dataTools;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import gabe.beertracker.theBeerTracker.models.BeerTag;
import gabe.beertracker.theBeerTracker.models.data.BeerTagDao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

public class DataTools {

    private static String DATA_FILE_BEER = "resources/beers2.csv";
    private static String DATA_FILE_BREWERIES = "resources/breweries2.csv";
    private static String DATA_FILE_BREWERIES_GEO = "resources/breweries_geocode2.csv";
    private static String DATA_FILE_CATEGORIES = "resources/categories2.csv";
    private static String DATA_FILE_STYLES = "resources/styles2.csv";
    private static  Boolean isDataLoaded = false;
    private static  Boolean isBreweriesLoaded = false;
    private static  Boolean isGeoLoaded = false;
    private static  Boolean isCatsLoaded = false;
    private static  Boolean isStulesLoaded = false;
    private  static ArrayList<HashMap<String, String>> allStrings;
    private  static ArrayList<HashMap<String, String>> allBreweries;
    private  static ArrayList<HashMap<String, String>> allGeo;
    private  static ArrayList<HashMap<String, String>> allCategories;
    private  static ArrayList<HashMap<String, String>> allStyles;

/*    private static void loadAllData(){
        loadData(DATA_FILE_BEER, allStrings, isDataLoaded);
        loadData(DATA_FILE_BREWERIES, allBreweries, isBreweriesLoaded);
        loadData(DATA_FILE_BREWERIES_GEO, allGeo, isGeoLoaded);
        loadData(DATA_FILE_CATEGORIES, allCategories, isCatsLoaded);
        loadData(DATA_FILE_STYLES, allStyles, isStulesLoaded);
    }*/


    private static ArrayList<HashMap<String, String>> loadData(String Datafile, ArrayList<HashMap<String, String>> rez, boolean flag) {
//   Only load data once
        /*if (flag) {
            return;
        }*/
        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(Datafile);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            //Integer numberOfColumns = maxColumn;
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            rez = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newString = new HashMap<>();

                for (String headerLabel : headers) {
                    newString.put(headerLabel, record.get(headerLabel));
                }

                rez.add(newString);
            }

            // flag the data as loaded, so we don't do it twice
            flag = true;
            return rez;

        } catch (IOException e) {
            System.out.println("Failed to load file " + Datafile);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *

     * @return List of all of the values of the given field
     * //Created by LaunchCode
     */
 /*   public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadAllData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allStrings) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }
        Collections.sort(values);
        return values;
    }*/

    public static ArrayList<HashMap<String, String>> findAll(String type) {

        // load data, if not already loaded
        ArrayList<HashMap<String, String>> strings = new ArrayList<>();
        switch (type) {
            case "beer":  {allStrings = loadData(DATA_FILE_BEER, allStrings, isDataLoaded);
                strings = allStrings;}
                break;
            case "breweries": {
                allBreweries = loadData(DATA_FILE_BREWERIES, allBreweries, isBreweriesLoaded);
                strings = allBreweries;}
                break;
            case "geo":  {allGeo = loadData(DATA_FILE_BREWERIES_GEO, allGeo, isGeoLoaded);
            strings = allGeo;}
                break;
            case "cats":  {allCategories = loadData(DATA_FILE_CATEGORIES, allCategories, isCatsLoaded);
             strings = allCategories;}
                break;
            case "styles":  {allStyles = loadData(DATA_FILE_STYLES, allStyles, isStulesLoaded);
                strings = allStyles;}
                break;
            default: {allStyles = loadData(DATA_FILE_STYLES, allStyles, isStulesLoaded);
                strings = allStyles;}
                break;
        }

        ArrayList<HashMap<String, String>> allStringsClone = new ArrayList<>();
        for (HashMap<String, String> row : strings)
        {
            allStringsClone.add(row);
        }
        return allStringsClone;
    }

    //Splits any text into words
    public static ArrayList<String> splitString(String text) {
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(text.split("[^a-zA-Z]+")));
        return arrayList;
    }
 //Converts Iterable to List
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
//Checks given string  in an arraylist
    public static boolean containsString (ArrayList<String> myArL, String value){
       for (String record : myArL){
            if (value.equalsIgnoreCase(record))
            {
                return true;
            }
        }
        return false;
    }
    public static String trimString(String mystring, int length){
        if ( mystring!= null){
            if (mystring.length()>length)
                return mystring.substring(0, Math.min(mystring.length(), length));
            else return mystring;
        }
            return null;

    }
    /**
     * A filter for CsvToDbController
     * wa -- only Washington
     * all -- several additional states
     */
    public static boolean isGoodState(String state, String choise ){
        if (choise == "wa"){
            if (state.equalsIgnoreCase("washington"))
            {return true;}
        }
        if (choise == "all"){
            if (state.equalsIgnoreCase("washington")||state.equalsIgnoreCase("oregon")||state.equalsIgnoreCase("idaho")||
        state.equalsIgnoreCase("california") || state.equalsIgnoreCase("montana") || state.equalsIgnoreCase("British Columbia")
                || state.equalsIgnoreCase("nevada")    )
            {return true;}
        }
           if (state.equalsIgnoreCase("washington"))
        {return true;}
        return false;
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
    public static String findByColumnAndValue(String column, String value, String lcolumn, ArrayList<HashMap<String, String>> data, int attempts) {

        String rez = "";
        int attempt =1;
        for (HashMap<String, String> row : data) {

            String aValue = row.get(column);

            if (searchStringIC(aValue,value))
            {
                if (attempt >= attempts)
                    return row.get(lcolumn);
                rez = row.get(lcolumn);
                attempt++;
            }
        }
        return rez;
    }
}
