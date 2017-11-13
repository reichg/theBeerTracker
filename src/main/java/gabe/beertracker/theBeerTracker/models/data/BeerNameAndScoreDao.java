package gabe.beertracker.theBeerTracker.models.data;


import gabe.beertracker.theBeerTracker.models.BeerNameAndScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface BeerNameAndScoreDao extends CrudRepository<BeerNameAndScore, Integer> {

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
            "ORDER BY score DESC LIMIT 20"
            , nativeQuery = true)
    public ArrayList<gabe.beertracker.theBeerTracker.models.BeerNameAndScore> getBeerNamesAndScore();

}