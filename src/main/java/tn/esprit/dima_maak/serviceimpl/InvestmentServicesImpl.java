package tn.esprit.dima_maak.serviceimpl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.dima_maak.Configuration.UserScore;

import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.IInvestmentRepository;
import tn.esprit.dima_maak.repositories.IVentureRepository;
import tn.esprit.dima_maak.repositories.UserRepository;
import tn.esprit.dima_maak.services.IInvestmentServices;

import java.util.*;
import java.util.stream.Collectors;

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


    @Override
    public String doInvestment(Long investmentId, Long ventureId) {
        Investment investment = investmentRepository.findById(investmentId).orElse(null);
        Venture venture = iVentureRepository.findById(ventureId).orElse(null);

        if (investment == null || venture == null) {
            return "The investment opportunity or venture is unavailable.";
        }

        if (venture.getStatus() == IStatus.CLOSED) {
            return "You cannot invest as the venture is closed";
        }

        long purchasedShares = investment.getPurchasedShares();
        long availableShares = venture.getAvailableShares() != null ? venture.getAvailableShares() - purchasedShares : 0;
        float investmentAmount = investment.getAmount();
        float loanAmount = venture.getLoanAmount() != null ? venture.getLoanAmount() - investmentAmount : 0;

        if (availableShares < 0 || loanAmount < 0) {
            return "High investment amount";
        }

        // Update availableShares and loanAmount
        venture.setAvailableShares(availableShares);
        venture.setLoanAmount(loanAmount);

        // Update status to CLOSED if both availableShares and loanAmount are 0
        if (venture.getAvailableShares() == 0 && venture.getLoanAmount() == 0) {
            venture.setStatus(IStatus.CLOSED);
        }

        investment.setVenture(venture);

        investment = investmentRepository.save(investment);
        venture = iVentureRepository.save(venture);

        return "The investment has been successfully allocated to the venture.";
    }

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

        try {
            Image image = Image.getInstance("C:\\Users\\ramil\\Downloads\\Logo Marketing Agency Digital..png");
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Vérifiez si le statut n'est pas null avant d'appeler toString()
        if (investment.getStatus() != null) {
            document.add(new Paragraph("Status: " + investment.getStatus().toString()));
        } else {
            document.add(new Paragraph("Status: N/A"));
        }

        document.close();

        return baos.toByteArray();
    }

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

    public List<UserScore> calculateUserScores() {
        List<UserScore> userScores = new ArrayList<>();

        // Récupérer tous les utilisateurs de la base de données
        List<User> users = userRepository.findAll();

        // Calculer le score pour chaque utilisateur
        for (User user : users) {
            List<Investment> investments = getUserInvestments(user.getId());
            int investmentCount = investments != null ? investments.size() : 0;
            userScores.add(new UserScore(user.getId(), investmentCount));
        }

        // Trier la liste des scores par ordre décroissant de score
        userScores.sort(Comparator.comparingInt(UserScore::getInvestmentCount).reversed());

        return userScores;
    }

    /* @Override
    public Investment AddAndDoInvestment(Investment investment, Long idV) {
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
    public String AddAndDoInvestment(Investment investment, Long idV) {
        Venture venture = iVentureRepository.findById(idV).orElse(null);
        if (venture != null) {
            if (venture.getStatus() == IStatus.CLOSED) {
                return "You cannot invest as the venture is closed";
            }

            long purchasedShares = investment.getPurchasedShares();
            long availableShares = venture.getAvailableShares() != null ? venture.getAvailableShares() - purchasedShares : 0;
            float investmentAmount = investment.getAmount();
            float loanAmount = venture.getLoanAmount() != null ? venture.getLoanAmount() - investmentAmount : 0;

            if (availableShares < 0 || loanAmount < 0) {
                return "High investment amount";
            }

            venture.setAvailableShares(availableShares);
            venture.setLoanAmount(loanAmount);

            // Mettre à jour le statut de la venture à CLOSED si les conditions sont remplies
            if (venture.getAvailableShares() == 0 && venture.getLoanAmount() == 0) {
                venture.setStatus(IStatus.CLOSED);
            }
            // Calcul du montant total de l'investissement
            Float totalInvestment = calculateTotalInvestment(investment.getPurchasedShares(), venture.getSharesPrice(), investment.getAmount());

            // Attribution de la valeur calculée à l'attribut totalInvestment de l'objet investment
            investment.setTotalInvestment(totalInvestment);


            investment.setVenture(venture);
            investmentRepository.save(investment);
            return "The investment has been successfully added and allocated to the venture.";
        } else {
            // Gérer le cas où la Venture n'existe pas
            return "Venture not exist";
        }
    }


    /////////////////////////////////////////////////////////////////////////////////






   /* public Map<User, Investment.ReturnStats> getReturnStatisticsByUserId() {
        List<Investment> investments = investmentRepository.getAllInvestmentsWithReturns();
        // Groupement des retours par utilisateur
        Map<User, List<Return>> returnsByUser = investments.stream()
                .flatMap(investment -> investment.getReturns().stream())
                .collect(Collectors.groupingBy(return -> retrieveUser(investment, return.getUserId())));
        // Calcul de la somme des returnInterest pour chaque utilisateur
        return returnsByUser.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Return> returns = entry.getValue();
                            int numberOfReturns = returns.size();
                            double totalReturnInterest = returns.stream().mapToDouble(Return::getReturnInterest).sum();
                            return new Investment.ReturnStats(numberOfReturns, totalReturnInterest);
                        }
                ));
    }*/
}