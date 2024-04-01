package tn.esprit.dima_maak.serviceimpl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.entities.Investment;
import tn.esprit.dima_maak.entities.User;
import tn.esprit.dima_maak.entities.Venture;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IInvestmentServices;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentServicesImpl implements IInvestmentServices {


    private final IInvestmentRepository investmentRepository;
    private final IVentureRepository iVentureRepository;
    private final UserRepository userRepository;


    @Override
    public Investment addInvestment(Investment investment) {
        return investmentRepository.save(investment);

    }

    @Override
    public Investment updateInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    @Override
    public boolean deleteInvestment(Long id) {
        Optional<Investment> investmentOptional = investmentRepository.findById(id);
        if (investmentOptional.isPresent()) {
            investmentRepository.deleteById(id);
            return true; // La suppression a été effectuée avec succès
        } else {
            return false; // L'identifiant spécifié n'existe pas
        }
    }

    @Override
    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id).orElse(null);
    }



    @Override
    public List<Investment> getAllInvestment() {
        return (List<Investment>) investmentRepository.findAll();
    }

   public Investment assignInvestmentToVenture(Long id, Long idV){
        Investment investment = investmentRepository.findById(id).orElse(null);
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        investment.setVenture(venture);
        return investmentRepository.save(investment);
     }

   /* @Override
    public Investment addInvestmentAndAssignToVenture(Investment investment, Long idV) {
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        if (venture != null) {
            investment.setVenture(venture);
            return investmentRepository.save(investment);
        } else {
            // Gérer le cas où la Venture n'existe pas
            return null;
        }
    }*/
    @Override
    public Float calculateTotalInvestment(Long purchasedShares, Float sharesPrice, Float amount) {
        return (sharesPrice * purchasedShares) + amount;
    }

    @Override
    public List<Investment> getUserInvestments(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getInvestments(); // Supposant que vous avez une méthode getInvestments() dans la classe User
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            return null; // ou vous pouvez lancer une exception selon vos besoins
        }
    }


    @Override
    public byte[] generateInvestmentPDF(Investment investment) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("__________Below, you'll find the details of your investment__________"));
        document.add(new Paragraph("ID: " + investment.getId()));
        document.add(new Paragraph("Date: " + (investment.getDate() != null ? investment.getDate().toString() : "N/A")));
        document.add(new Paragraph("Purchased Shares: " + investment.getPurchasedShares()));
        document.add(new Paragraph("Amount: " + investment.getAmount()));
        document.add(new Paragraph("Total Investment: " + investment.getTotalInvestment()));

        // Vérifiez si le statut n'est pas null avant d'appeler toString()
        if (investment.getStatus() != null) {
            document.add(new Paragraph("Status: " + investment.getStatus().toString()));
        } else {
            document.add(new Paragraph("Status: N/A"));
        }

        document.close();

        return baos.toByteArray();
    }

    /*@Override
    public byte[] addInvestmentAndAssignToVenture(Investment investment, Long idV) throws DocumentException {
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        if (venture != null) {
            investment.setVenture(venture);
            Investment savedInvestment = investmentRepository.save(investment);
            // Generate PDF for the saved investment
            return generateInvestmentPDF(savedInvestment);
        } else {
            // Gérer le cas où la Venture n'existe pas
            return null;
        }
    }*/

    @Override
    public byte[] addInvestmentAndAssignToVenture(Investment investment, Long idV) throws DocumentException {
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        if (venture != null) {
            // Calcul du montant total de l'investissement
            Float totalInvestment = calculateTotalInvestment(investment.getPurchasedShares(), venture.getSharesPrice(), investment.getAmount());

            // Attribution de la valeur calculée à l'attribut totalInvestment de l'objet investment
            investment.setTotalInvestment(totalInvestment);

            // Attribution de la venture à l'investissement
            investment.setVenture(venture);

            // Enregistrement de l'investissement
            Investment savedInvestment = investmentRepository.save(investment);

            // Génération du PDF pour l'investissement enregistré
            return generateInvestmentPDF(savedInvestment);
        } else {
            // Gérer le cas où la Venture n'existe pas
            return null;
        }
    }






}





