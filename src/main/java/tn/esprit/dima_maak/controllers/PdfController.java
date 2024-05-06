package tn.esprit.dima_maak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.dima_maak.serviceimpl.PdfService;

import java.io.IOException;
import java.util.List;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> createPdf(@RequestParam("contents") List<String> contents) {
        try {
            byte[] pdfBytes = pdfService.createPdf( contents);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("example.pdf").build());
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/getpdf")
    public ResponseEntity<byte[]> getPdf(@RequestParam("contents") List<String> contents) throws IOException {
        byte[] pdfContent = pdfService.createPdf(contents);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("example.pdf").build());
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

}

