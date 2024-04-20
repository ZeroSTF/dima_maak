package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Rating;

@Repository
public interface IRatingRepository extends CrudRepository<Rating,Long> {
@Query("select count(r) from Rating r")
    Long countRatings();
}
