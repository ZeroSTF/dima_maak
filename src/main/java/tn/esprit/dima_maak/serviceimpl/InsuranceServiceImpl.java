package tn.esprit.dima_maak.serviceimpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.*;
import tn.esprit.dima_maak.services.IInsuranceService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InsuranceServiceImpl implements IInsuranceService {
@Autowired
private InsuranceRepository inrep;
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
private UserRepository userrep;
    @Autowired
private InsurancePRepository inPrep;
    @Autowired
    PremiumRepository premiumRepository;
    @Override
    public Insurance addInsurance(Insurance i) {
        return inrep.save(i);
    }

    @Override
    public Insurance getInsuranceById(Long id) {
        return inrep.findById(id).orElse(null);
    }

    @Override
    public void deleteInsurance(Long id) {
        inrep.deleteById(id);

    }

    @Override
    public List<Insurance> getALL() {

            return(List<Insurance>) inrep.findAll();


    }


    @Override
    public Insurance updateInsurance(Insurance i) {
        Insurance insurance= inrep.findById(i.getId()).orElse(null);
        insurance.setState(i.getState());
        insurance.setStartDate(i.getStartDate());
        insurance.setEndDate(i.getEndDate());
        insurance.setClientcoverageamount(i.getClientcoverageamount());

        return inrep.save(insurance);
    }

    @Override
    public Insurance addinsuranceandassigntouser(Insurance insurance, Long iduser) {
        inrep.save(insurance);
        User user = userrep.findById(iduser).orElse(null);
        insurance.setUser(user);
        return  inrep.save(insurance);


    }

    @Override
    public Insurance assigninsurancetouser(Long idinsurance, Long iduser) {
        Insurance i=inrep.findById(idinsurance).orElse(null);
        User u=userrep.findById(iduser).orElse(null);
        i.setUser(u);
        return inrep.save(i);
    }

    @Override
    public Insurance assinsuranceandassigntoinsurancepack(Insurance insurance, Long idinsuranceP) {
        inrep.save(insurance);
        InsuranceP inp = inPrep.findById(idinsuranceP).orElse(null);
        insurance.setInsuranceP(inp);
        return inrep.save(insurance);
    }

    @Override
    public List<Insurance> retrieveinsurancebypacktype(IType insurancePType) {
        return inrep.retrieveinsurancebypacktype(insurancePType);
    }

    @Override
    public Long gettotalinsurance() {
        return inrep.count();
    }

   /* @Override
    public Map<String, Long> getInsuranceTypeDistribution() {
        return inrep.getInsuranceTypeDistribution();
    }*/

    @Override
    public Insurance assigninsurancetoinsurancepack(Long idinsurance, Long idinsurancepack) {
        InsuranceP inp = inPrep.findById(idinsurancepack).orElse(null);
        Insurance i = inrep.findById(idinsurance).orElse(null);
        i.setInsuranceP(inp);
        return inrep.save(i);
    }

    @Override
    public Long countInsurancesByPackType(IType packtype) {
        return inrep.countinsurancebypacktype(packtype);
    }

    @Override
    public String calculatePercentageByType(IType packtype) {
        Long totalinsurances = gettotalinsurance();
        if(totalinsurances == 0)
        {
            return "0.00%";
        }
        Long insurancesbypacktype = countInsurancesByPackType(packtype);
        double percentage = (double) insurancesbypacktype/totalinsurances *100.0;
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(percentage/100.0);
    }

    @Override
    public float findTotalCoverageAmountByPackType(IType packType) {
        return inrep.findTotalCoverageAmountByPackType(packType);
    }
    @Override
    public List<Insurance> getallbyuser(Long iduser) {
        User user = userrep.findById(iduser).orElse(null);
        return inrep.findByUser(user);
    }

    @Override
    public Insurance createInsurance(Long insurancePackId, Long iduser) {
        Insurance insurance = new Insurance();
        LocalDate currentDate = LocalDate.now();
        insurance.setStartDate(currentDate);
        insurance.setEndDate(null);
        insurance.setClientcoverageamount(0);
        insurance.setClientpremium(0);
        insurance.setState(InStatus.Pending);

        InsuranceP insuranceP= inPrep.findById(insurancePackId).get();
        User user = userrep.findById(iduser).orElse(null);
        insurance.setInsuranceP(insuranceP);
        insurance.setUser(user);
        return inrep.save(insurance);
    }
    @Override
    public Insurance updatedInsurance(Long insuranceId) {
        Insurance insurance= inrep.findById(insuranceId).get();
        insurance.setState(InStatus.Accepted);
        Iterable<Insurance> insurances = inrep.findAll();
        int calcul=0;
        float total=0;
        for (Insurance insurance1 : insurances
        ){
            if (insurance1.getInsuranceP().getId().equals(insurance.getInsuranceP().getId()) && insurance.getInsuranceP().getType().equals(insurance1.getInsuranceP().getType())){
                calcul++;
                total+=insurance1.getInsuranceP().getCoverageAmount();
            }
        }
        float pourcentageclaim=calculatePercentage(insurance.getUser(),insurance.getInsuranceP().getType(),insurance.getId())/100;
        //float moyenne = total/calcul;
        float averageclaimamount = total / calcul;

        float clientcovergeamount = (((pourcentageclaim * averageclaimamount)+insurance.getInsuranceP().getCoverageAmount())/2);

        insurance.setClientcoverageamount(clientcovergeamount);
        insurance.setClientpremium(clientcovergeamount/12);
        LocalDate currentDate = LocalDate.now();
        insurance.setStartDate(currentDate);
        LocalDate endDate = currentDate.plusYears(1);
        insurance.setEndDate(endDate);


        for (int i = 0; i <= 11; i++) {
            Premium premium = new Premium();
            premium.setAmount(clientcovergeamount/12);
            premium.setStatus(false);
            premium.setDate(currentDate.plusMonths(i));
            premium.setInsurance(insurance);
            premium.setAccumulatedInterest(20f);
            premiumRepository.save(premium);
        }
        System.out.println("clientcovergeamount = " + clientcovergeamount);
        System.out.println("totalcoverageamount = " + total);
        System.out.println("averageclaimamount = " + averageclaimamount);
        System.out.println("countInsurances = "  +calcul);
        System.out.println("pourcentageclaim = "  +pourcentageclaim);

        return inrep.save(insurance);
    }
    public Float calculatePercentage(User userInfo, IType type,Long id) {
        Float percentage = 0f;
        int nbr=0;
        Iterable<Claim> claims = claimRepository.findAll();
        for (Claim claim : claims
        ){
            if (claim.getInsurance().getId().equals(id)){
                nbr++;
            }
        }
        switch (nbr){
            case 1:
                percentage +=2f;
                break;
            case 2:
                percentage +=4f;
                break;
            case 3:
                percentage +=6f;
                break;
            default:
                percentage +=2f;
                break;
        }
        if (userInfo != null) {
            if ("Doctor".equalsIgnoreCase(userInfo.getJob())) {
                percentage += 10f;
            } else if ("Engineer".equalsIgnoreCase(userInfo.getJob())) {
                percentage += 5f;
            } else {
                percentage += 2.5f;
            }

            int age = calculateAge(userInfo.getBirthDate());
            if (age >= 18 && age <= 30) {
                percentage += 5f;
            } else if (age > 30 && age <= 50) {
                percentage += 8f;
            } else if (age > 50) {
                percentage += 10f;
            }

            if (type != null) {
                switch (type) {
                    case Property_Insurance:
                        percentage += 5f;
                        break;
                    case Health_Insurance:
                        percentage += 7.5f;
                        break;
                    case Auto_Insurance:
                        percentage += 3f;
                        break;
                    case Liability_Insurance:
                        percentage += 6f;
                        break;
                    case Business_Insurance:
                        percentage += 10f;
                        break;
                    default:
                        break;
                }
            }
        }
        return percentage;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - birthDate.getYear();
    }
   /* public Insurance createInsurance(Long insurancePackId, Long iduser) {
        InsuranceP insurancepack = inPrep.findById(insurancePackId).orElse(null);
        User user = userrep.findById(iduser).orElse(null);
        float totalcoverageamount = findTotalCoverageAmountByPackType(insurancepack.getType());
        double averageclaimamount = totalcoverageamount / countInsurancesByPackType(insurancepack.getType());
        float pourcentageclaim = 0.1F;
        float clientcovergeamount = (float) (((pourcentageclaim * averageclaimamount) + insurancepack.getCoverageAmount()) / 2);

        Insurance insurance = new Insurance();
        insurance.setUser(user);
        insurance.setStartDate(LocalDate.now()); // You might want to customize this based on your business logic
        insurance.setEndDate(insurance.getStartDate().plusYears(insurancepack.getDuration())); // Adjust duration logic as needed
        insurance.setClientcoverageamount(clientcovergeamount);
        insurance.setClientpremium(clientcovergeamount / 12);
        insurance.setState(InStatus.Accepted);
        return inrep.save(insurance);
    }*/

}
