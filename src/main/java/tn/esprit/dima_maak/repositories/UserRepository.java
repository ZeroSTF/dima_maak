package tn.esprit.dima_maak.repositories;

import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByDemandeList(Demande demande);
    Optional<User> findUserByEmail(String email);

    List<User> findUsersByEmail(String email);
}