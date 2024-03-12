package tn.esprit.dima_maak.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Venture;
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
    public List<Venture> getAllVenture() {return (List<Venture>) ventureRepository.findAll();
    }



}
