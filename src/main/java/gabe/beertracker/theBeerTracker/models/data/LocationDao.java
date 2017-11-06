package gabe.beertracker.theBeerTracker.models.data;


import gabe.beertracker.theBeerTracker.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface LocationDao extends CrudRepository<Location, Integer> {


    @Query(value=
            "SELECT b.id, b.administrative_area_level1, b.country, b.description, b.latitude" +
                    ", b.longitude, b.locality, b.name, b.phone, b.postal_code, \n" +
                    "b.route, b.street_number, b.web_site, bd.beer_id as bd_id" +
                    ", bd.beer_id, bd.location_id, bd.user_game_id , b.administrative_area_level2 FROM \n" +
                    "(location AS b \n" +
                    "JOIN beer_drink AS bd \n" +
                    "on b.id = bd.location_id) WHERE user_id =:foobar\n" +
                    "GROUP BY bd.beer_id\n" +
                    "ORDER BY bd.id DESC\n" +
                    "LIMIT 10"
            , nativeQuery = true)
    public ArrayList<Location> getLocationsFromBeerDrinksByUserIdLast10(@Param(value="foobar") int someId);

    @Query(value="SELECT * FROM location WHERE name=:foobar LIMIT 1", nativeQuery = true)
    public Location getLocationByName(@Param(value="foobar") String somePassedName);

}

//INSERT INTO beer_locations (beer_id, locations_id) VALUES (9, 16)