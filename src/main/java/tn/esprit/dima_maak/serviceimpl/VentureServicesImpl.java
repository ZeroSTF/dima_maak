package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.services.IVentureServices;
@Service
@RequestMapping("/venture")
@AllArgsConstructor
public class VentureServicesImpl implements IVentureServices {

    private IVentureRepository ventureRepository;

    @Override
    public Venture addVenture(Venture venture){ return ventureRepository.save(venture);}

    @Override
    public Venture updateVenture(Venture venture){
        return ventureRepository.save(venture);
    }
    @Override
    public Void deleteVenture(Long id) {
        ventureRepository.deleteById((id));
        return null;
    }

    @Override
    public Venture getVentureById(Long id) {return  ventureRepository.findById(id).orElse(null);}




}
