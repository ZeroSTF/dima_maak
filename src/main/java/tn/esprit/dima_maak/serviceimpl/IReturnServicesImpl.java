package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.Return;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IReturnRepository;
import tn.esprit.dima_maak.services.IReturnServices;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/return")
@RequiredArgsConstructor
public class IReturnServicesImpl implements IReturnServices {

    private final IReturnRepository returnRepository;
    private final IInvestmentRepository investmentRepository;



    @Override
    public Return addReturn(Return treturn) {
        return returnRepository.save(treturn);
    }

    @Override
    public Return updateReturn(Return treturn) {
        return returnRepository.save(treturn);
    }

    @Override
    public boolean deleteReturn(Long idR) {
        Optional<Return> returnOptional = returnRepository.findById(idR);
        if (returnOptional.isPresent()) {
            returnRepository.deleteById(idR);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // L'identifiant spécifié n'existe pas
        }
    }

    @Override
    public List<Return> getAllReturn() {
        return (List<Return>) returnRepository.findAll();
    }

    @Override
    public Return getReturnById(Long idR) {
        return  returnRepository.findById(idR).orElse(null);
    }

    @Override
    public Return assignReturnToInvestment(Long idR, Long id) {

            Investment investment = investmentRepository.findById(id).orElse(null);
            Return aReturn = returnRepository.findById(idR).orElse(null);
            aReturn.setInvestment(investment);
            return returnRepository.save(aReturn);

        }

    @Override
    public float calculateMonthlyReturns(long loanDuration, float loanAmount, float interest) {

        double totalInterest = loanAmount * interest * loanDuration / 12; // Calcul du total des intérêts
        float monthlyInterest = (float) (totalInterest / loanDuration); // Calcul de l'intérêt mensuel
        return monthlyInterest;


    }


}

