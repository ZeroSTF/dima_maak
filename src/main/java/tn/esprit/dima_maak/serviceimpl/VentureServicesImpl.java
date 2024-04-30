package tn.esprit.dima_maak.serviceimpl;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.services.IVentureServices;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    public void processExcelFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is on the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row if present

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Venture venture = createVentureFromRow(row);
                ventureRepository.save(venture);
            }
        }
    }

    private Venture createVentureFromRow(Row row) {
        Venture venture = new Venture();
        if (row.getCell(0) != null) {
            venture.setCompanyName(row.getCell(0).getStringCellValue());
        }
        if (row.getCell(1) != null) {
            venture.setVentureName(row.getCell(1).getStringCellValue());
        }
        if (row.getCell(2) != null) {
            venture.setVentureType(VType.valueOf(row.getCell(2).getStringCellValue()));
        }
        if (row.getCell(3) != null) {
            venture.setDescription(row.getCell(3).getStringCellValue());
        }
        if (row.getCell(4) != null) {
            venture.setStage(Stage.valueOf(row.getCell(4).getStringCellValue()));
        }
        if (row.getCell(5) != null) {
            venture.setSector(Sector.valueOf(row.getCell(5).getStringCellValue()));
        }
        if (row.getCell(6) != null) {
            venture.setAvailableShares((long) row.getCell(6).getNumericCellValue());
        }
        if (row.getCell(7) != null) {
            venture.setSharesPrice((float) row.getCell(7).getNumericCellValue());
        }
        if (row.getCell(8) != null) {
            venture.setStatus(IStatus.valueOf(row.getCell(8).getStringCellValue()));
        }
        if (row.getCell(9) != null) {
            venture.setLoanAmount((float) row.getCell(9).getNumericCellValue());
        }
        if (row.getCell(10) != null) {
            venture.setInterest((float) row.getCell(10).getNumericCellValue());
        }
        if (row.getCell(11) != null) {
            venture.setLoanDuration((long) row.getCell(11).getNumericCellValue());
        }
        if (row.getCell(12) != null) {
            venture.setDividendPerShare((float) row.getCell(12).getNumericCellValue());
        }

        return venture;
    }

    public boolean updateAllVenture() {
        List<Venture> ventures = ventureRepository.findAll();
        List<Venture> venturesToUpdate = ventures.stream()
                .filter(venture -> venture.getDateExp() != null && LocalDate.now().isAfter(venture.getDateExp()))
                .collect(Collectors.toList());

        if (!venturesToUpdate.isEmpty()) {
            venturesToUpdate.forEach(venture -> venture.setStatus(IStatus.CLOSED));
            ventureRepository.saveAll(venturesToUpdate);
            return true; // La mise à jour a été effectuée avec succès
        } else {
            return false; // Aucune Venture n'a été trouvée avec une date d'expiration dépassée
        }
    }


    @Override
    public boolean deleteVenturesExpired() {
        List<Venture> ventures = ventureRepository.findAll();
        List<Venture> expiredVentures = ventures.stream()
                .filter(venture -> venture.getDateExp() != null && LocalDate.now().isAfter(venture.getDateExp()))
                .collect(Collectors.toList());

        if (!expiredVentures.isEmpty()) {
            ventureRepository.deleteAll(expiredVentures);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // Aucun Venture n'a été trouvé avec une date d'expiration dépassée
        }
    }


}