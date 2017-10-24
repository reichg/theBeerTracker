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
public interface BeerDrinkDao extends CrudRepository<BeerDrink, Integer> {
    @Query(value="SELECT * FROM beer_drink WHERE user_id =:foobar GROUP BY `beer_id`", nativeQuery = true)
    public ArrayList<BeerDrink> getUniqueBeerDrinksByUserId(@Param(value="foobar") int someId);
}