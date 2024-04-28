package tn.esprit.dima_maak.serviceimpl;


import lombok.AllArgsConstructor;
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
        l.setUser(user);

        return leasingRepository.save(l);
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
        leasing.setDepositamount(l.getDepositamount());
        leasing.setPenaltyfee(l.getPenaltyfee());
        leasing.setAdditionalfee(l.getAdditionalfee());
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

            User user = leasing.getUser();


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

    }


















