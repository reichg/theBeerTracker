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
public interface BeerDao extends CrudRepository<Beer, Integer> {
    @Query(value="SELECT * FROM beer WHERE id IN (SELECT  beers_id FROM beer_tags WHERE tags_id =(SELECT id FROM beer_tag WHERE name =:foobar LIMIT 1))", nativeQuery = true)
    public ArrayList<Beer> getBeersByTag(@Param(value="foobar") String someTag);

    @Query(value="SELECT DISTINCT * FROM beer WHERE id IN (SELECT  beer_id FROM beer_drink WHERE user_id =:foobar )", nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserId(@Param(value="foobar") int someId);

    @Query(value="SELECT DISTINCT * FROM beer WHERE id IN (SELECT  beer_id FROM beer_drink WHERE user_id =(SELECT id FROM user WHERE user_name =:foobar LIMIT 1))", nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserName(@Param(value="foobar") String someUserName);

    @Query(value="SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` =:foobar LIMIT 1))) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable", nativeQuery = true)
    public ArrayList<Beer> getBeersNotTriedByUserName(@Param(value="foobar") String someUserName);

    @Query(value="SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` =:foobar)) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable", nativeQuery = true)
    public ArrayList<Beer> getBeersNotTriedByUserId(@Param(value="foobar") int someId);



//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` = 'username4' LIMIT 1))
//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = 1)
////SELECT * FROM `beer` WHERE `id` IN (SELECT `beers_id` FROM `beer_tags` WHERE `tags_id` = (SELECT `id` FROM `beer_tag` WHERE `name` = "North"))
//SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` = 'username3' LIMIT 1))) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable

}