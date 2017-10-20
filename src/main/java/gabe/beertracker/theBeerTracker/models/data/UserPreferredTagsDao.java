package gabe.beertracker.theBeerTracker.models.data;
import gabe.beertracker.theBeerTracker.models.BeerTag;
import gabe.beertracker.theBeerTracker.models.UserPreferredTags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface UserPreferredTagsDao extends CrudRepository<UserPreferredTags, Integer> {
    @Query(value="SELECT * FROM `user_preferred_tags` WHERE `user_id` =:foobar", nativeQuery = true)
    public ArrayList <UserPreferredTags> getUserPreferredTagsByUserId(@Param(value="foobar") int somePassedUsername);

/*    @Query(value="SELECT COUNT * FROM `user_preferred_tags` WHERE `user_id` =:foobar", nativeQuery = true)
    public int getUserPreferredTagsQuantityByUserId(@Param(value="foobar") int somePassedUsername);*/
}


