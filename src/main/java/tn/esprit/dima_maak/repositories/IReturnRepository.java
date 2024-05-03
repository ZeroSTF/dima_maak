package tn.esprit.dima_maak.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.Return;

@Repository
public interface IReturnRepository extends CrudRepository<Return ,Long> {
}