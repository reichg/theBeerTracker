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

/*    @Query(value="SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT b.id, b.abv, b.date_of_creation, b.description, b.name, bd.beer_id, bd.user_id , bd.id AS bd_id FROM beer AS b JOIN beer_drink AS bd on b.id = bd.beer_id WHERE user_id =:foobar GROUP BY b.id ORDER BY bd_id DESC) mtt LIMIT 10"
            , nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserIdLast10(@Param(value="foobar") int someId);*/

    @Query(value="SELECT b.id,\n" +
            "b.abv,\n" +
            "b.date_of_creation,\n" +
            "b.description,\n" +
            "b.name,\n" +
            "bd.beer_id,\n" +
            "bd.user_id,\n" +
            "bd.location_id\n" +
            "FROM beer AS b\n" +
            "JOIN beer_drink AS bd\n" +
            "on b.id = bd.beer_id\n" +
            "WHERE user_id =:foobar GROUP BY bd.beer_id\n" +
            "ORDER BY bd.id DESC LIMIT 10"
            , nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserIdLast10(@Param(value="foobar") int someId);

    @Query(value="SELECT id, name, IFNULL(drinked*3,0)-IFNULL(discarded*2,0)+IFNULL(users,0) as score FROM\n" +
            "(SELECT id, name FROM beer) bt\n" +
            "LEFT OUTER JOIN\n" +
            "(SELECT IFNULL(beer_id, beer_id_u) as beer_id, drinked, discarded, users FROM\n" +
            "(SELECT * FROM\n" +
            "(SELECT IFNULL(bd_beer_id, ug_beer_id) as beer_id, drinked, discarded FROM\n" +
            "(SELECT * FROM\n" +
            "(SELECT beer_id as bd_beer_id, COUNT(*) as drinked FROM beer_drink\n" +
            "GROUP BY beer_id) bd1 \n" +
            "LEFT OUTER JOIN\n" +
            "(SELECT beer_id as ug_beer_id, COUNT(*) as discarded FROM user_game WHERE date_of_discarding IS NOT NULL\n" +
            "GROUP BY beer_id) ug\n" +
            "ON bd1.bd_beer_id = ug.ug_beer_id\n" +
            "UNION\n" +
            "SELECT * FROM\n" +
            "(SELECT beer_id as bd_beer_id, COUNT(*) as drinked FROM beer_drink\n" +
            "GROUP BY beer_id) bd1 \n" +
            "RIGHT OUTER JOIN\n" +
            "(SELECT beer_id as ug_beer_id, COUNT(*) as discarded FROM user_game WHERE date_of_discarding IS NOT NULL\n" +
            "GROUP BY beer_id) ug\n" +
            "ON bd1.bd_beer_id = ug.ug_beer_id) rez1) rez2\n" +
            "LEFT OUTER JOIN\n" +
            "(SELECT beer_id as beer_id_u, COUNT(DISTINCT user_id) as users\n" +
            "FROM beer_drink\n" +
            "GROUP BY beer_id) bd2\n" +
            "ON rez2.beer_id = bd2.beer_id_u\n" +
            "UNION\n" +
            "SELECT * FROM\n" +
            "(SELECT IFNULL(bd_beer_id, ug_beer_id) as beer_id, drinked, discarded FROM\n" +
            "(SELECT * FROM\n" +
            "(SELECT beer_id as bd_beer_id, COUNT(*) as drinked FROM beer_drink\n" +
            "GROUP BY beer_id) bd1 \n" +
            "LEFT OUTER JOIN\n" +
            "(SELECT beer_id AS ug_beer_id, COUNT(*) as discarded FROM user_game WHERE date_of_discarding IS NOT NULL\n" +
            "GROUP BY beer_id) ug\n" +
            "ON bd1.bd_beer_id = ug.ug_beer_id\n" +
            "UNION\n" +
            "SELECT * FROM\n" +
            "(SELECT beer_id AS bd_beer_id, COUNT(*) as drinked FROM beer_drink\n" +
            "GROUP BY beer_id) bd1 \n" +
            "RIGHT OUTER JOIN\n" +
            "(SELECT beer_id AS ug_beer_id, COUNT(*) as discarded FROM user_game WHERE date_of_discarding IS NOT NULL\n" +
            "GROUP BY beer_id) ug\n" +
            "ON bd1.bd_beer_id = ug.ug_beer_id) rez1) rez2\n" +
            "RIGHT OUTER JOIN\n" +
            "(SELECT beer_id as beer_id_u, COUNT(DISTINCT user_id) as users\n" +
            "FROM beer_drink \n" +
            "GROUP BY beer_id) bd2\n" +
            "ON rez2.beer_id = bd2.beer_id_u) rez3) tab\n" +
            "ON bt.id = tab.beer_id\n" +
            "ORDER BY score DESC"
            , nativeQuery = true)
    public ArrayList<BeerNameAndScoreDao> getBeerNamesAndScore();



/*@Query(value="SELECT * FROM beer WHERE  id <:foobar LIMIT 1"
        , nativeQuery = true)
public ArrayList<Beer> getBeersTriedByUserIdLast10(@Param(value="foobar") int someId);*/


/*    @Query(value="SELECT b.id, b.abv, b.date_of_creation, b.description, b.name, bd.beer_id, bd.user_id , bd.id AS bd_id FROM beer AS b JOIN beer_drink AS bd on b.id = bd.beer_id WHERE user_id =:foobar GROUP BY b.id ORDER BY bd_id DESC LIMIT 10"
            , nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserIdLast10(@Param(value="foobar") int someId);*/

    @Query(value="SELECT DISTINCT * FROM beer WHERE id IN (SELECT  beer_id FROM beer_drink WHERE user_id =(SELECT id FROM user WHERE user_name =:foobar LIMIT 1))", nativeQuery = true)
    public ArrayList<Beer> getBeersTriedByUserName(@Param(value="foobar") String someUserName);

    @Query(value="SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` =:foobar LIMIT 1))) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable", nativeQuery = true)
    public ArrayList<Beer> getBeersNotTriedByUserName(@Param(value="foobar") String someUserName);

    @Query(value="SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` =:foobar)) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable", nativeQuery = true)
    public ArrayList<Beer> getBeersNotTriedByUserId(@Param(value="foobar") int someId);

    @Query(value="SELECT `id` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` =:foobar)) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable", nativeQuery = true)
    public ArrayList<Integer> getBeerIdsNotTriedByUserId(@Param(value="foobar") int someId);

    @Query(value="SELECT `id` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` =:foobar)) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable WHERE `id`<20", nativeQuery = true)
    public ArrayList<Integer> getBeerIdsNotTriedByUserIdLimited(@Param(value="foobar") int someId);

//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` = 'username4' LIMIT 1))
//SELECT DISTINCT * FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = 1)
////SELECT * FROM `beer` WHERE `id` IN (SELECT `beers_id` FROM `beer_tags` WHERE `tags_id` = (SELECT `id` FROM `beer_tag` WHERE `name` = "North"))
//SELECT `id`, `abv`, `date_of_creation`, `description`, `name` FROM (SELECT * from `beer` AS b LEFT OUTER JOIN (SELECT DISTINCT `id` AS b_id FROM `beer` WHERE `id` IN (SELECT `beer_id` FROM `beer_drink` WHERE `user_id` = (SELECT `id` FROM user WHERE `user_name` = 'username3' LIMIT 1))) AS bt ON b.`id` = bt.`b_id` WHERE `b_id` IS NULL) AS mytable

}