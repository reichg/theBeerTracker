package gabe.beertracker.theBeerTracker.models.data;
        import gabe.beertracker.theBeerTracker.models.UserKnownLocations;
                import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;
        import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserKnownLocationsDao extends CrudRepository<UserKnownLocations, Integer> {

}
