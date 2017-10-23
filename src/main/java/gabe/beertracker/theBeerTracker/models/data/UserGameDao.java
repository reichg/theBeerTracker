package gabe.beertracker.theBeerTracker.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import gabe.beertracker.theBeerTracker.models.*;


@Repository
@Transactional
public interface UserGameDao extends CrudRepository<UserGame, Integer> {
}

