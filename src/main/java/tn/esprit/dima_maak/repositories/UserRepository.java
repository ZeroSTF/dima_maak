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
    User findUserByDemandeList(Demande demande);
    Optional<User> findUserByEmail(String email);

    List<User> findUsersByEmail(String email);
    @Query("select count(u) from User u ")
    int countUsers ();
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.id = 1")
    List<User> findAdminUsers();

    @Query("SELECT u FROM User u JOIN u.role r WHERE (r.id = 2) OR (r.id = 3)")
    List<User> findUserUsers();

    //salary statistics
    @Query("SELECT count(u) FROM User u WHERE u.salary < 1000")
    int countUsersWithSalaryLessThan1000();

    @Query("SELECT count(u) FROM User u WHERE u.salary >= 1000 AND u.salary < 3000")
    int countUsersWithSalaryBetween1000And3000();

    @Query("SELECT count(u) FROM User u WHERE u.salary >= 3000 AND u.salary < 6000")
    int countUsersWithSalaryBetween3000And6000();

    @Query("SELECT count(u) FROM User u WHERE u.salary >= 6000")
    int countUsersWithSalaryMoreThan6000();

    //age statistics
    @Query("SELECT count(u) FROM User u WHERE YEAR(CURRENT_DATE) - YEAR(u.birthDate) < 18")
    int countUsersBelow18();

    @Query("SELECT count(u) FROM User u WHERE YEAR(CURRENT_DATE) - YEAR(u.birthDate) >= 18 AND YEAR(CURRENT_DATE) - YEAR(u.birthDate) < 25")
    int countUsersBetween18And25();

    @Query("SELECT count(u) FROM User u WHERE YEAR(CURRENT_DATE) - YEAR(u.birthDate) >= 25 AND YEAR(CURRENT_DATE) - YEAR(u.birthDate) < 40")
    int countUsersBetween25And40();

    @Query("SELECT count(u) FROM User u WHERE YEAR(CURRENT_DATE) - YEAR(u.birthDate) >= 40")
    int countUsersAbove40();

    //location statistics
    @Query("SELECT u.address.latitude, u.address.longitude FROM User u")
    List<Object[]> findAllUserCoordinates();

    //count users whose job is the same as the given job
    @Query("SELECT count(u) FROM User u WHERE u.job = ?1")
    int countUsersByJob(String job);
}