package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Loyalty;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.LoyaltyRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.ILoyaltyService;
import tn.esprit.dima_maak.services.IUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements ILoyaltyService {
    private final LoyaltyRepository loyaltyRepository;
    private final IUserService userService;
    private final UserRepository userRepository;

    @Override
    public List<Loyalty> retrieveAllLoyalties() {
        return loyaltyRepository.findAll();
    }

    @Override
    public Loyalty retrieveLoyalty(Long id) {
        Optional<Loyalty> loyaltyOptional = loyaltyRepository.findById(id);
        return loyaltyOptional.orElse(null);
    }

    @Override
    public Loyalty addLoyalty(Loyalty loyalty) {
        User user=userRepository.findById(loyalty.getUser().getId()).get();
        loyalty.setUser(user);
        loyalty.setDate(LocalDateTime.now());
        loyalty.getUser().setLp(loyalty.getUser().getLp()+Math.toIntExact(loyalty.getValue()));
        userService.modifyUser(loyalty.getUser());
        return loyaltyRepository.save(loyalty);
    }

    @Override
    public void removeLoyalty(Long id) {
        Loyalty l=loyaltyRepository.findById(id).get();
        l.getUser().setLp(l.getUser().getLp()-Math.toIntExact(l.getValue()));
        userService.modifyUser(l.getUser());
        loyaltyRepository.deleteById(id);
    }

    @Override
    public Loyalty modifyLoyalty(Loyalty loyalty) {
        User user=loyalty.getUser();
        int difference=loyaltyRepository.findById(loyalty.getId()).get().getValue().intValue()-loyalty.getValue().intValue();
        user.setLp(user.getLp()-difference);
        userService.modifyUser(user);
        return loyaltyRepository.save(loyalty);
    }
}
