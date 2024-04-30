package tn.esprit.dima_maak.services;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.Venture;

import java.io.IOException;
import java.util.List;

public interface IVentureServices {

    Venture addVenture(Venture venture);
    /*Venture updateVenture(Venture venture);*/
    public Venture updateVenture(Long idV, Venture venture);
    boolean deleteVenture (Long idV);
    List<Venture> getAllVenture();
    Venture getVentureById(Long idV);
    void updateVentureStatus(Long idV);
    void processExcelFile(MultipartFile filepath) throws IOException;

     boolean updateAllVenture();
     boolean deleteVenturesExpired();
}
