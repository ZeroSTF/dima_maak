package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Rating;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IRatingRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IRatingService;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements IRatingService {
    private IRatingRepository ratingRepository;
    private UserRepository userRepository;
    @Override
    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating findRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRating(Long id) {

    }

    @Override
    public List<Rating> getAll() {
        return (List<Rating>) ratingRepository.findAll();
    }

    @Override
    public double calculateOverallSatisfaction() {
            List<User> users = userRepository.findAll();
            double totalRating = ratingRepository.countRatings();
            int totalUsers = userRepository.countUsers();
            return totalRating/totalUsers;
        }

    }












