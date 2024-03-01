package tn.esprit.dima_maak.repositories;


import org.springframework.data.repository.CrudRepository;
import tn.esprit.dima_maak.entities.Leasing;

import java.util.List;
import java.util.Optional;

public interface ILeasingRepository extends CrudRepository<Leasing,Long> {


    public Optional<Leasing> findById(Long  leaseid);


}