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

/*    @Query(value = "INSERT INTO beer_locations (beer_id, locations_id) VALUES (foobar, foobar2)", nativeQuery = true)
    public void setNewBeerForLocation(@Param(value = "foobar") int beerId, @Param(value = "foobar2") int locationId);*/

}

//INSERT INTO beer_locations (beer_id, locations_id) VALUES (9, 16)