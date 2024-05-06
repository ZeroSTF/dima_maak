package tn.esprit.dima_maak.serviceimpl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    public byte[] createPdf( List<String> contents) throws IOException {
        // yesne3 pdf
        PDDocument document = new PDDocument();

        // yzid fih page
        PDPage page = new PDPage();
        document.addPage(page);

        // ye3abih
        PDType1Font font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 12;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(font, fontSize);
        int y = 700;
        for (String content : contents) {
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText(content);
            contentStream.endText();
            y -= 20;
        }
        contentStream.close();

        // save ka byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        return baos.toByteArray();
    }
}
