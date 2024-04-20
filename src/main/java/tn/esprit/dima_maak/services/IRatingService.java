package tn.esprit.dima_maak.services;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import tn.esprit.dima_maak.entities.Rating;
import tn.esprit.dima_maak.entities.User;

import java.util.List;

public interface IRatingService {
    Rating addRating(Rating rating);
    Rating updateRating(Rating rating);
    Rating findRatingById(Long id);
    void deleteRating(Long id);
    List<Rating> getAll();
    public double calculateOverallSatisfaction();

}
