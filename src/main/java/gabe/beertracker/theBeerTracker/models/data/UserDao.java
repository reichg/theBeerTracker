package gabe.beertracker.theBeerTracker.models.data;

import gabe.beertracker.theBeerTracker.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    @Query(value="SELECT * FROM user WHERE user_name=:foobar LIMIT 1", nativeQuery = true)
    public User getUserByUsername(@Param(value="foobar") String somePassedUsername);

}