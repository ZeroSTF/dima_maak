package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Loyalty;
import tn.esprit.dima_maak.repositories.LoyaltyRepository;
import tn.esprit.dima_maak.services.ILoyaltyService;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements ILoyaltyService {
    private final LoyaltyRepository loyaltyRepository;

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
        return loyaltyRepository.save(loyalty);
    }

    @Override
    public void removeLoyalty(Long id) {
        loyaltyRepository.deleteById(id);
    }

    @Override
    public Loyalty modifyLoyalty(Loyalty loyalty) {
        return loyaltyRepository.save(loyalty);
    }
}
