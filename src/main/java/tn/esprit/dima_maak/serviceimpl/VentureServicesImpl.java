package tn.esprit.dima_maak.serviceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.services.IVentureServices;
import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/venture")
@RequiredArgsConstructor
public class VentureServicesImpl implements IVentureServices {

    private final IVentureRepository ventureRepository;

    @Override
    public Venture addVenture(Venture venture){ return ventureRepository.save(venture);}

    @Override
    public Venture updateVenture(Venture venture){
        return ventureRepository.save(venture);
    }
    @Override
    public boolean deleteVenture(Long idV) {
        Optional<Venture> ventureOptional = ventureRepository.findById(idV);
            if (ventureOptional.isPresent()) {
                ventureRepository.deleteById(idV);
                return true; // La suppression a été effectuée avec succès
            } else {
                return false; // L'identifiant spécifié n'existe pas
            }
    }
    @Override
    public Venture getVentureById(Long idV) {return  ventureRepository.findById(idV).orElse(null);}

    @Override
    public List<Venture> getAllVenture() {return ventureRepository.findAll();
    }
   @Override
    public void updateVentureStatus(Long idV) {
        Venture venture = ventureRepository.findById(idV).orElse(null);
        if (venture != null) {
            Long totalPurchasedShares = ventureRepository.getTotalPurchasedSharesForVenture(idV);
            Long totalInvestmentAmount = ventureRepository.getTotalAmountForVenture(idV);

            if (totalPurchasedShares != null && totalInvestmentAmount != null) {
                // Vérifier si le total des actions achetées dépasse ou est égal au nombre d'actions disponibles
                if (totalPurchasedShares >= venture.getAvailableShares()) {
                    venture.setStatus(IStatus.CLOSED);
                }

                // Vérifier si le montant total des investissements dépasse ou est égal au montant du prêt
                if (totalInvestmentAmount >= venture.getLoanAmount()) {
                    venture.setStatus(IStatus.CLOSED);
                }

                ventureRepository.save(venture);
            }
        }
    }





}





