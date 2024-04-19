package tn.esprit.dima_maak.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.dima_maak.entities.Rating;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.services.IRatingService;
import tn.esprit.dima_maak.services.IUserService;

import java.util.List;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
public class RatingRestController {
    private IRatingService ratingService;

    @PostMapping("/save")
    public Rating addRating(@RequestBody Rating rating){
        return ratingService.addRating(rating);
    }
    @PutMapping("/update")
    public Rating updateRating(@RequestBody Rating rating){
        return ratingService.updateRating(rating);
    }

    @GetMapping("/get/{idRating}")
    public Rating getRating(@PathVariable("idRating") long idRating){
        return ratingService.findRatingById(idRating);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRating( Long id){
        ratingService.deleteRating(id);
        return "Rating deleted !";
    }
    @GetMapping("/all")
    public List<Rating> getAllRating(){
        return ratingService.getAll();
    }

    @GetMapping("/overall")
    public double calculateOverallSatisfaction() {
        return ratingService.calculateOverallSatisfaction();
    }


}






