package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Claim;
import tn.esprit.dima_maak.repositories.ClaimRepository;
import tn.esprit.dima_maak.services.IClaimService;

import java.util.List;

@Service
@AllArgsConstructor

public class ClaimServiceImpl implements IClaimService {

    private ClaimRepository claimrep;
    @Override
    public Claim addClaim(Claim c) {
        return claimrep.save(c);
    }

    @Override
    public Claim getClaimbyid(Long id) {
        return claimrep.findById(id).orElse(null);
    }

    @Override
    public void deleteClaim(Long id) {
        claimrep.deleteById(id);

    }

    @Override
    public List<Claim> getALL() {
        return (List<Claim>) claimrep.findAll();
    }

    @Override
    public Claim updateclaim(Claim c) {
        return claimrep.save(c);
    }
}
