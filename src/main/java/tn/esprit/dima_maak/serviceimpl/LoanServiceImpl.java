package tn.esprit.dima_maak.serviceimpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.dima_maak.entities.*;
import tn.esprit.dima_maak.repositories.LoanRepository;
import tn.esprit.dima_maak.repositories.PortionRepository;
import tn.esprit.dima_maak.services.ILoanService;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    public LoanRepository LoanRepository;

    private PortionRepository portionRepository;
    @Override
    public List<Loan> retrieveLoanperUser(Long iduser) {
        return LoanRepository.findByUserId(iduser);
    }

    @Override
    public Loan retrieveLoan(Long idLoan) {
        return LoanRepository.findById(idLoan).orElse(null);
    }

    @Override
    public Loan addLoanRequest(Loan loan) {
        return LoanRepository.save(loan);
    }

    @Override
    public void removeLoanRequest(Long idloan) {
        LoanRepository.deleteById(idloan);

    }

    @Override
    public Loan modifyLoanRequest(Loan loan) {
        return LoanRepository.save(loan);
    }

    @Override
    public ByteArrayOutputStream simulateLoan(Loan loan) throws DocumentException {
        // Initialize Document and PdfWriter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        // Open the document
        document.open();
        PdfContentByte contentByte = pdfWriter.getDirectContent();

        // Create a rectangle
        Rectangle rectangle = new Rectangle(80, 800, 500, 770);
        rectangle.setBorder(Rectangle.BOX);
        rectangle.setBorderWidth(1);

        // Add the rectangle to the document
        contentByte.rectangle(rectangle);

        // Move to the center of the rectangle for adding text
        contentByte.moveTo((rectangle.getLeft() + rectangle.getRight()) / 2, (rectangle.getBottom() + rectangle.getTop()) / 2);

        // Add content to the document
        Font font = FontFactory.getFont(FontFactory.COURIER, 16,Font.BOLD, BaseColor.BLACK);

        // Create a Paragraph with centered text
        Paragraph paragraph = new Paragraph("Table de remboursement", font);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        // Add the Paragraph to the document
        document.add(paragraph);
        document.add(Chunk.NEWLINE);


        PdfPTable table = new PdfPTable(5); // 5 columns
        table.setWidthPercentage(100);

        PdfPCell numberLibelle = new PdfPCell(new Phrase("Num"));
        numberLibelle.setBorderWidth(1f);
        table.addCell(numberLibelle);

        PdfPCell montantLibelle = new PdfPCell(new Phrase("Montant restant dû au debut du mois"));
        montantLibelle.setBorderWidth(1f);
        table.addCell(montantLibelle);

        PdfPCell interetLibelle = new PdfPCell(new Phrase("Intéret"));
        interetLibelle.setBorderWidth(1f);
        table.addCell(interetLibelle);

        PdfPCell AmortLibelle = new PdfPCell(new Phrase("Amortissement"));
        AmortLibelle.setBorderWidth(1f);
        table.addCell(AmortLibelle);



        double md=0;
        double inte=0;
        double mens=0;
        double annu=0;
        double amorti=0;

        if (loan.getPaimentMethod()==LMethod.Monthly_Constant) {
            PdfPCell MensLibelle = new PdfPCell(new Phrase("Mensualité"));
            MensLibelle.setBorderWidth(1f);
            table.addCell(MensLibelle);
            md=loan.getAmount();
            mens   =(loan.getAmount()*loan.getInterest())/(1-Math.pow((1+loan.getInterest()),-1*loan.getTermInMonths()));
            for(int i=1;i<=loan.getTermInMonths();i++){

                md=md-amorti;
                inte=md*loan.getInterest();
                amorti=mens -inte;

            PdfPCell number = new PdfPCell(new Phrase(String.valueOf(i)));
            number.setBorderWidth(1f);
            table.addCell(number);

            PdfPCell montantrest = new PdfPCell(new Phrase(String.format("%.2f",md)));
            montantrest.setBorderWidth(1f);
            table.addCell(montantrest);

            PdfPCell interet = new PdfPCell(new Phrase(String.format("%.2f",inte)));
            interet.setBorderWidth(1f);
            table.addCell(interet);

            PdfPCell ammort = new PdfPCell(new Phrase(String.format("%.2f",amorti)));
            ammort.setBorderWidth(1f);
            table.addCell(ammort);

            PdfPCell mensualite1 = new PdfPCell(new Phrase(String.format("%.2f",mens)));
            mensualite1.setBorderWidth(1f);
            table.addCell(mensualite1);

        }

        }

        else if (loan.getPaimentMethod()==LMethod.Bloc_Annual){
            PdfPCell MensLibelle = new PdfPCell(new Phrase("Annuité"));
            MensLibelle.setBorderWidth(1f);
            table.addCell(MensLibelle);
            for(int i=1;i<=loan.getTermInMonths()/12;i++){




                md=loan.getAmount();
                inte=md*loan.getInterest();
                if(i==loan.getTermInMonths()/12){
                    amorti=md;
                }
                annu=inte+amorti;
                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(i)));
                number.setBorderWidth(1f);
                table.addCell(number);

                PdfPCell montantrest = new PdfPCell(new Phrase(String.format("%.2f",md)));
                montantrest.setBorderWidth(1f);
                table.addCell(montantrest);

                PdfPCell interet = new PdfPCell(new Phrase(String.format("%.2f",inte)));
                interet.setBorderWidth(1f);
                table.addCell(interet);

                PdfPCell ammort = new PdfPCell(new Phrase(String.format("%.2f",amorti)));
                ammort.setBorderWidth(1f);
                table.addCell(ammort);

                PdfPCell annuite = new PdfPCell(new Phrase(String.format("%.2f",annu)));
                annuite.setBorderWidth(1f);
                table.addCell(annuite);

            }
        }
        else if (loan.getPaimentMethod()==LMethod.Bloc_Monthly){
            PdfPCell MensLibelle = new PdfPCell(new Phrase("Mensualité"));
            MensLibelle.setBorderWidth(1f);
            table.addCell(MensLibelle);
            for(int i=1;i<=loan.getTermInMonths();i++){

                md=loan.getAmount();
                inte=md*loan.getInterest();
                if(i==loan.getTermInMonths()){
                    amorti=md;
                }
                mens=inte+amorti;
                PdfPCell number = new PdfPCell(new Phrase(i));
                number.setBorderWidth(1f);
                table.addCell(number);

                PdfPCell montantrest = new PdfPCell(new Phrase(String.format("%.2f",md)));
                montantrest.setBorderWidth(1f);
                table.addCell(montantrest);

                PdfPCell interet = new PdfPCell(new Phrase(String.format("%.2f",inte)));
                interet.setBorderWidth(1f);
                table.addCell(interet);

                PdfPCell ammort = new PdfPCell(new Phrase(String.format("%.2f",amorti)));
                ammort.setBorderWidth(1f);
                table.addCell(ammort);

                PdfPCell mensualite1 = new PdfPCell(new Phrase(String.format("%.2f",mens)));
                mensualite1.setBorderWidth(1f);
                table.addCell(mensualite1);

            }
        }
        else if (loan.getPaimentMethod()==LMethod.Annual_Constant){
            PdfPCell MensLibelle = new PdfPCell(new Phrase("Annuité"));
            MensLibelle.setBorderWidth(1f);
            table.addCell(MensLibelle);
            annu=((loan.getAmount()*loan.getInterest())/(1-Math.pow(1+loan.getInterest(),-1*(loan.getTermInMonths()/12))));
            md=loan.getAmount();
            for(int i=1;i<=loan.getTermInMonths()/12;i++){




                md=md-amorti;
                inte=md*loan.getInterest();
                amorti=annu-inte;

                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(i)));
                number.setBorderWidth(1f);
                table.addCell(number);

                PdfPCell montantrest = new PdfPCell(new Phrase(String.format("%.2f",md)));
                montantrest.setBorderWidth(1f);
                table.addCell(montantrest);

                PdfPCell interet = new PdfPCell(new Phrase(String.format("%.2f",inte)));
                interet.setBorderWidth(1f);
                table.addCell(interet);

                PdfPCell ammort = new PdfPCell(new Phrase(String.format("%.2f",amorti)));
                ammort.setBorderWidth(1f);
                table.addCell(ammort);

                PdfPCell mensualite1 = new PdfPCell(new Phrase(String.format("%.2f",annu)));
                mensualite1.setBorderWidth(1f);
                table.addCell(mensualite1);

            }
        }
        else if (loan.getPaimentMethod()==LMethod.Amortissment_Constant){
            PdfPCell MensLibelle = new PdfPCell(new Phrase("Annuité"));
            MensLibelle.setBorderWidth(1f);
            table.addCell(MensLibelle);

            md= loan.getAmount();
            amorti=md/(loan.getTermInMonths()/12);

            for(int i=1;i<=loan.getTermInMonths()/12;i++){
                md=md-amorti;
                if(i==1){
                    md= loan.getAmount();
                }
                inte=md*loan.getInterest();
                annu=inte+amorti;

                PdfPCell number = new PdfPCell(new Phrase(String.valueOf(i)));
                number.setBorderWidth(1f);
                table.addCell(number);

                PdfPCell montantrest = new PdfPCell(new Phrase(String.format("%.2f",md)));
                montantrest.setBorderWidth(1f);
                table.addCell(montantrest);

                PdfPCell interet = new PdfPCell(new Phrase(String.format("%.2f",inte)));
                interet.setBorderWidth(1f);
                table.addCell(interet);

                PdfPCell ammort = new PdfPCell(new Phrase(String.format("%.2f",amorti)));
                ammort.setBorderWidth(1f);
                table.addCell(ammort);

                PdfPCell mensualite1 = new PdfPCell(new Phrase(String.format("%.2f",annu)));
                mensualite1.setBorderWidth(1f);
                table.addCell(mensualite1);

            }
        }
        float[] columnWidthsD = {40f, 80f,80f,80f,80f}; // Change these values as needed
        table.setWidths(columnWidthsD);
        document.add(table);
document.close();

        return outputStream;
    }

    @Transactional
    @Override
    public void UpdateLoanStatus(Long id) throws ParseException {
        double amorti=0;
        double md=0;
        double inte=0;
        double mens=0;
        double annu=0;

        Loan loan=LoanRepository.findById(id).get();



        loan.setStatus(LStatus.Accepted);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(loan.getStartDate());
        // Extract year, month, and day from the Calendar instance
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate localDate = LocalDate.of(year, month, day+1);
        if(loan.getPaimentMethod()==LMethod.Monthly_Constant){

            md=loan.getAmount();
            mens=(loan.getAmount()*loan.getInterest())/(1-Math.pow((1+loan.getInterest()),-1*loan.getTermInMonths()));
            for(int i=0;i<=loan.getTermInMonths();i++){

                md = md - amorti;
                inte = md * loan.getInterest();
                amorti = mens - inte;


                LocalDate newLocalDate = localDate.plusMonths(i);
                Portion por=new Portion();
                por.setAmount(mens);
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setDate(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        else if(loan.getPaimentMethod()==LMethod.Monthly_Progressive){
            md= loan.getAmount();
            mens = loan.getAmount()*((loan.getInterest()*Math.pow((1+loan.getInterest()),loan.getTermInMonths())/(Math.pow((1+loan.getInterest()),loan.getTermInMonths())-1)));

            for(int i=0;i<=loan.getTermInMonths();i++){

                md=md-amorti;
                inte=md*loan.getInterest();
                amorti=mens -inte;


                LocalDate newLocalDate = localDate.plusMonths(i);
                Portion por=new Portion();
                por.setAmount(mens);
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setDate(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        else if(loan.getPaimentMethod()==LMethod.Bloc_Annual){
            for(int i=0;i<=loan.getTermInMonths()/12;i++){
                md=loan.getAmount();
                inte=md*loan.getInterest();

                if(i==loan.getTermInMonths()/12){
                    amorti=loan.getAmount();
                }
                annu=inte+amorti;
                Portion por=new Portion();
                LocalDate newLocalDate = localDate.plusYears(i);
                por.setDate(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setAmount(annu);
                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        else if(loan.getPaimentMethod()==LMethod.Bloc_Monthly){
            for(int i=0;i<=loan.getTermInMonths();i++){
                md=loan.getAmount();
                inte=md*loan.getInterest();
                if(i==loan.getTermInMonths()){
                    amorti=md;
                }
                mens=inte+amorti;
                Portion por=new Portion();
                LocalDate newLocalDate = localDate.plusYears(i);
                por.setDate(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                por.setAmount(mens);
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        else if(loan.getPaimentMethod()==LMethod.Annual_Constant){
            annu=((loan.getAmount()*loan.getInterest())/(1-Math.pow(1+loan.getInterest(),-1*(loan.getTermInMonths()/12))));
            md=loan.getAmount();
            for(int i=0;i<=loan.getTermInMonths()/12;i++){
                md=md-amorti;
                inte=md*loan.getInterest();
                amorti=annu-inte;

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                Portion por=new Portion();
                LocalDate newLocalDate = localDate.plusYears(i);
                por.setDate(dateFormat.parse(dateFormat.format(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))));
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setAmount(annu);                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        else if(loan.getPaimentMethod()==LMethod.Amortissment_Constant){

            md= loan.getAmount();
            amorti=md/(loan.getTermInMonths()/12);
            for(int i=0;i<=loan.getTermInMonths()/12;i++){

                md=md-amorti;
                if(i==1){
                    md= loan.getAmount();
                }
                inte=md*loan.getInterest();
                annu=inte+amorti;



                Portion por=new Portion();
                LocalDate newLocalDate = localDate.plusYears(i);
                por.setDate(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                por.setMd(md);
                por.setInte(inte);
                por.setAmortissement(amorti);
                por.setAmount(annu);
                por.setStatus(PStatus.Active);
                por.setLoan(loan);
                portionRepository.save(por);
                loan.getPortions().add(por);
            }

        }
        LoanRepository.save(loan);
    }
    @Override
    public void penalityCalculation() {
        List<Portion> portionList = (List<Portion>) portionRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Portion portion : portionList) {
            LocalDate portionDate = portion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (currentDate.isAfter(portionDate)) {
             Portion   portiona =new Portion();
                portiona.setStatus(PStatus.In_default);
                portiona.setAmount(portion.getInte());
                portiona.setLoan(portion.getLoan());
                portiona.setMd(portion.getMd());
                portiona.setInte(portion.getInte());
                portiona.setDate(portion.getDate());
                portiona.setAmortissement(portion.getAmortissement());
                portionRepository.save(portiona);




                }

            }

        }
    }







