package gabe.beertracker.theBeerTracker.models.data;

import gabe.beertracker.theBeerTracker.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface BeerDao extends CrudRepository<Beer, Integer> {
    @Query(value="SELECT * FROM beer WHERE id IN (SELECT  beers_id FROM beer_tags WHERE tags_id =(SELECT id FROM beer_tag WHERE name =:foobar))", nativeQuery = true)
    public List<Beer> getBeersByTag(@Param(value="foobar") String someTag);

////SELECT * FROM `beer` WHERE `id` IN (SELECT `beers_id` FROM `beer_tags` WHERE `tags_id` = (SELECT `id` FROM `beer_tag` WHERE `name` = "North"))



}