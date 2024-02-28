package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Return;
import tn.esprit.dima_maak.repositories.IReturnRepository;
import tn.esprit.dima_maak.services.IReturnServices;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/return")
@AllArgsConstructor
public class IReturnServicesImpl implements IReturnServices {

    private IReturnRepository returnRepository;


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
}
