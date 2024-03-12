package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.services.IInvestmentServices;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentServicesImpl implements IInvestmentServices {


    private final IInvestmentRepository investmentRepository;
    private final IVentureRepository iVentureRepository;
    @Override
    public Investment addInvestment(Investment investment){
        return investmentRepository.save(investment);

    }

    @Override
    public Investment updateInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    @Override
    public boolean deleteInvestment(Long id) {
        Optional<Investment> investmentOptional = investmentRepository.findById(id);
        if (investmentOptional.isPresent()) {
            investmentRepository.deleteById(id);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // L'identifiant spécifié n'existe pas
        }
    }
    @Override
    public Investment getInvestmentById(Long id) {return  investmentRepository.findById(id).orElse(null);}

    @Override
    public List<Investment> getAllInvestment() {return (List<Investment>) investmentRepository.findAll();
    }

    public Investment assignInvestmentToVenture(Long id, Long idV){
        Investment investment = investmentRepository.findById(id).orElse(null);
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        investment.setVenture(venture);
        return investmentRepository.save(investment);

    }




}
