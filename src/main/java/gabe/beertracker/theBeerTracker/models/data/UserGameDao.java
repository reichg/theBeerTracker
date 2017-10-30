package gabe.beertracker.theBeerTracker.models.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import gabe.beertracker.theBeerTracker.models.*;


@Repository
@Transactional
public interface UserGameDao extends CrudRepository<UserGame, Integer> {
    @Query(value="SELECT * FROM user_game WHERE user_id=:foobar ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public UserGame getLastUserGameByUserId(@Param(value="foobar") int somePassedUserId);
}

