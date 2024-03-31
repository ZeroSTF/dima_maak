package tn.esprit.dima_maak.serviceimpl;

import jakarta.transaction.Transactional;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.services.*;
import tn.esprit.dima_maak.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements IUserService {
    UserRepository userRepository;
    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User addUser(User c) {
        return userRepository.save(c);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User modifyUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            return userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
    }

   /* @Transactional
    public void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            float newBalance =user.getBalance()+ returnAmount + returnInterest + sharesGain - totalInvestment;
            user.setBalance(newBalance);
            userRepository.save(user);
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Vous pouvez lancer une exception ou gérer d'une autre manière selon vos besoins
        }
    }*/


    @Transactional
    public void updateBalance(Long id, float returnAmount, float returnInterest, long sharesGain, float totalInvestment) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            // Vérifier si l'utilisateur a des investissements associés
            if (!user.getInvestments().isEmpty()) {
                float newBalance = user.getBalance() + returnAmount + returnInterest + sharesGain - totalInvestment;
                user.setBalance(newBalance);
                userRepository.save(user);
            }
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Vous pouvez lancer une exception ou gérer d'une autre manière selon vos besoins
        }
    }

    public boolean hasInvestments(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null && !user.getInvestments().isEmpty();
    }

}