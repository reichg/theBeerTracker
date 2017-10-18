package gabe.beertracker.theBeerTracker.models.data;

import gabe.beertracker.theBeerTracker.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BeerDao extends CrudRepository<Beer, Integer> {
    @Query(value="SELECT * FROM beer WHERE id IN (SELECT  beers_id FROM beer_tags WHERE tags_id =(SELECT id FROM beer_tag WHERE name =:foobar LIMIT 1))", nativeQuery = true)
    public List<Beer> getBeersByTag(@Param(value="foobar") String someTag);

    @Query(value="SELECT DISTINCT * FROM beer WHERE id IN (SELECT  beer_id FROM beer_drink WHERE user_id =:foobar )", nativeQuery = true)
    public List<Beer> getBeersTriedByUserId(@Param(value="foobar") int someId);

    @Query(value="SELECT DISTINCT * FROM beer WHERE id IN (SELECT  beer_id FROM beer_drink WHERE user_id =(SELECT id FROM user WHERE user_name =:foobar LIMIT 1))", nativeQuery = true)
    public List<Beer> getBeersTriedByUserName(@Param(value="foobar") String someUserName);


//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` = 'username4' LIMIT 1))
//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = 1)
////SELECT * FROM `beer` WHERE `id` IN (SELECT `beers_id` FROM `beer_tags` WHERE `tags_id` = (SELECT `id` FROM `beer_tag` WHERE `name` = "North"))

}