package tn.esprit.dima_maak.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.dima_maak.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select count(u) from User u ")
    int countUsers ();
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.id = 1")
    List<User> findAdminUsers();
}