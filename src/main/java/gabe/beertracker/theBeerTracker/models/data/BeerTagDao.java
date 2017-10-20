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
public interface BeerTagDao extends CrudRepository<BeerTag, Integer> {
    @Query(value="SELECT * FROM beer_tag WHERE name=:foobar LIMIT 1", nativeQuery = true)
    public BeerTag getTagByTagname(@Param(value="foobar") String somePassedUsername);
    @Query(value="SELECT * FROM `beer_tag` WHERE `user_preferred_tags_id` IN (SELECT `id` FROM `user_preferred_tags` WHERE `user_id` =:foobar)", nativeQuery = true)
    public ArrayList<BeerTag> getPreferredTagsById(@Param(value="foobar") int someId);

}

//SELECT * FROM `beer_tag` WHERE `user_preferred_tags_id` IN (SELECT `id` FROM `user_preferred_tags` WHERE `user_id` = 1)