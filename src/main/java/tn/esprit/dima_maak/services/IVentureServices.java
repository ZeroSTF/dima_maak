package tn.esprit.dima_maak.services;
import tn.esprit.dima_maak.entities.Venture;

import java.util.List;

public interface IVentureServices {

    Venture addVenture(Venture venture);
    Venture updateVenture(Venture venture);
    boolean deleteVenture (Long idV);
    List<Venture> getAllVenture();
    Venture getVentureById(Long idV);
    void updateVentureStatus(Long idV);

    /*void addVentureFromExcel(String filepath);*/
    /*void addVenturesFromExcel(String filePath) throws IOException;*/


}
