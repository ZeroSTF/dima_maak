package tn.esprit.dima_maak.serviceimpl;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.Leasing;
import tn.esprit.dima_maak.repositories.ILeasingRepository;
import tn.esprit.dima_maak.services.ILeasingService;

import javax.print.attribute.standard.MediaSize;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LeasingServiceImpl implements ILeasingService {


    private final  ILeasingRepository leasingRepository;

   @Override
   public Leasing createLeasing(Leasing l) {
       return leasingRepository.save(l);
   }
   @Override
   public Optional<Leasing> getLeasingById(Long leaseid) {
       return leasingRepository.findById(leaseid);

    }
    @Override
    public Leasing updateLeasing(Leasing l) {
        return leasingRepository.save(l);}

@Override
    public void deleteLeasingById(Long leaseid) {
        leasingRepository.deleteById(leaseid);
    }



}



