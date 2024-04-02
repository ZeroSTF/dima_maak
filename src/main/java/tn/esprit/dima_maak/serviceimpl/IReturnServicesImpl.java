package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.Return;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IReturnRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IReturnServices;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/return")
@RequiredArgsConstructor
public class IReturnServicesImpl implements IReturnServices {

    private final IReturnRepository returnRepository;
    private final IInvestmentRepository investmentRepository;
    private final UserRepository userRepository;



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
    public Return assignReturnToInvestment(Long idR, Long id, long loanDuration, float loanAmount, float interest) {

        Investment investment = investmentRepository.findById(id).orElse(null);
        Return aReturn = returnRepository.findById(idR).orElse(null);

        if (investment != null && aReturn != null) {
            // Appeler la fonction f1 avec les paramètres x, y et z
            float monthlyInterest = calculateMonthlyReturns( loanDuration,  loanAmount,  interest);

            // Associer l'investissement au retour
            aReturn.setInvestment(investment);

            // Affecter le résultat de la fonction f1 à l'attribut returnInterest
            aReturn.setReturnInterest(monthlyInterest);

            // Sauvegarder le retour mis à jour dans la base de données
            return returnRepository.save(aReturn);
        } else {
            // Gérer le cas où l'investissement ou le retour n'existe pas
            return null;
        }

    }


    @Override
    public float calculateMonthlyReturns(long loanDuration, float loanAmount, float interest) {

        double totalInterest = loanAmount * interest * loanDuration / 12; // Calcul du total des intérêts
        float monthlyInterest = (float) (totalInterest / loanDuration); // Calcul de l'intérêt mensuel
        return monthlyInterest;

    }

    @Override
    public Return addReturnAndAssignToInvestment(Long id, Return aReturn, long loanDuration, float loanAmount, float interest) {
        Investment investment = investmentRepository.findById(id).orElse(null);
        if (investment != null) {
            // Appeler la fonction de calcul avec les paramètres requis
            float monthlyInterest = calculateMonthlyReturns(loanDuration, loanAmount, interest);

            // Associer l'investissement au retour
            aReturn.setInvestment(investment);

            // Affecter le résultat du calcul à l'attribut returnInterest
            aReturn.setReturnInterest(monthlyInterest);

            // Sauvegarder le retour dans la base de données
            return returnRepository.save(aReturn);
        } else {
            // Gérer le cas où l'investissement n'existe pas
            return null;
        }
    }

   // @Scheduled(cron = "0 */2 * * * *")
   /* public void scheduleAddReturnAndAssignToInvestment() {
        // Appeler la méthode sans argument pour effectuer la tâche planifiée
        addReturnAndAssignToInvestment(1232L, new Return(), 5, 10, 12);}*/
}

