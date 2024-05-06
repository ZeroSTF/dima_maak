package tn.esprit.dima_maak.serviceimpl;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Demande;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.repositories.IDemandeRepository;
import tn.esprit.dima_maak.repositories.ILeasingRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.ILeasingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LeasingServiceImpl implements ILeasingService {


    private final ILeasingRepository leasingRepository;
    private final UserRepository userRepository;
    private final IDemandeRepository iDemandeRepository;
    @Override
    public Leasing createLeasing(Leasing l,Long iduser,Long iddemande) {
        Demande demande = iDemandeRepository.findById(iddemande).orElse(null);
        User user = userRepository.findById(iduser).orElse(null);

        l.setDemande(demande);
        float f =calculateMonthlyPayment(demande);
        l.setMonthlypayment(f);
        l.setUser(user);
        l.setInitialValue(demande.getAsset().getPrice());
        return leasingRepository.save(l);
    }
    public float calculateMonthlyPayment(Demande demande) {
        if (demande == null) {
            throw new IllegalArgumentException("La demande est nulle.");
        }



        float totalAmount = demande.getAsset().getPrice() ;
        float montantparmonth = totalAmount/12;
        float monthlyPaymentPercentage = 0.25f;

        float     monthlyPayment = montantparmonth * monthlyPaymentPercentage;



        return Math.round(monthlyPayment * 100) / 100.0f;
    }
    @Scheduled(cron = "0/35 * * * * *")
    public void checkRetard() {
        Iterable<Leasing> leasings = leasingRepository.findLeasingByEnddateAfter(LocalDate.now());
        for (Leasing leasing : leasings) {
            int monthsElapsed = (int) ChronoUnit.MONTHS.between(leasing.getStartdate(), LocalDate.now());
            float additionalAmount = 0.0f;

            if (monthsElapsed >= 1 && monthsElapsed < 2) {
                additionalAmount = leasing.getMonthlypayment() * 0.1f; // Add 10% after one month
            } else if (monthsElapsed >= 2 && monthsElapsed < 4) {
                additionalAmount = leasing.getMonthlypayment() * 0.2f; // Add 20% after two months
            } else if (monthsElapsed >= 4) {
                additionalAmount = leasing.getMonthlypayment() * 0.4f; // Add 20% after two months

            }
             float total = leasing.getMonthlypayment() + additionalAmount;
            leasing.setMonthlypayment(total);

        }
    }

    @Override
    public Optional<Leasing> getLeasingById(Long leaseid) {
        return leasingRepository.findById(leaseid);

    }

    @Override
    public Leasing updateLeasing(Leasing l,Long id) {
        Leasing leasing=leasingRepository.findById(id).orElse(null);
        leasing.setStartdate(l.getStartdate());
        leasing.setEnddate(l.getEnddate());
        leasing.setMonthlypayment(l.getMonthlypayment());
        leasing.setStatus(l.getStatus());
        leasing.setPenaltyfee(l.getPenaltyfee());
        leasing.setRenwealoption(l.getRenwealoption());
        leasing.setPaymentstatus(l.getPaymentstatus());
        leasing.setInitialValue(l.getInitialValue());



        return leasingRepository.save(leasing);
    }

    @Override
    public void deleteLeasingById(Long leaseid) {
         leasingRepository.deleteById(leaseid);

    }

    public String assignUserToLeasing(Long userId, Long leasingId) {
        User user = userRepository.findById(userId).orElse(null);
        Leasing leasing = leasingRepository.findById(leasingId).orElse(null);

        if (user != null && leasing != null) {
            leasing.setUser(user);
            leasingRepository.save(leasing);
            return "success";
        } else {
            return "Leasing or user null ";
        }
    }

    @Override
    public float calculateAnnualInterestRate(Leasing leasing) {

            User user = leasing.getDemande().getUser();


            Float creditScore = userRepository.findById(user.getId())
                    .map(User::getCreditScore)
                    .orElse(null);


            float interestRate;
            if (creditScore != null && creditScore >= 700) {
                interestRate = 5.0f; // pour les bons crédits
            } else {
                interestRate = 7.0f; // pour les mauvais crédits
            }
            return interestRate;
        }
    public float calculateLatePaymentPercentage(Leasing leasing) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = leasing.getEnddate(); // Date d'échéance du paiement

        long daysLate = ChronoUnit.DAYS.between(dueDate, currentDate);
        if (daysLate > 0) {
            if (daysLate <= 30) {
                return 3.0f;
            } else if (daysLate <= 60) {
                return 5.0f;
            } else {
                return 7.0f;
            }
        } else {
            return 0.0f;
        }
    }
    @Override
    public Iterable<Leasing> leasingList (){
        return leasingRepository.findAll();
    }


    @Override
    public Iterable<Leasing> leasingsbydemande (Long id){
        Iterable<Leasing> leasings= leasingRepository.findLeasingByDemande_Id(id);

        return leasings;
    }

}



















