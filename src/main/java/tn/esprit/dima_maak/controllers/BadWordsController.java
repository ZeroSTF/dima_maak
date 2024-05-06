package tn.esprit.dima_maak.controllers;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RestController
public class BadWordsController {


    @PostMapping("/text")
    public ResponseEntity<String> filterBadWords1(@RequestBody String text) {
        try {
            // API link mte3ha
            String url = "https://api.apilayer.com/bad_words?censor_character=censor_character";

            // key
            String apiKey = "vFmYBcb4CMB66bhFzl2Xk70K0W2l8WaE";

            // connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // t7ot  method
            con.setRequestMethod("POST");

            // w dzideha apikey f headers bch yevalidlik request
            con.setRequestProperty("apikey", apiKey);
            con.setRequestProperty("Content-Type", "text/plain");

            // te7oteha true bch tnajem tekteb f body
            con.setDoOutput(true);

            // 3abi  request body mte3na
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(text);
            wr.flush();
            wr.close();

            // Get response code eli jetna
            int responseCode = con.getResponseCode();

            // read body eli jena
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();

            // Return response
            return ResponseEntity.status(responseCode).body(responseBody.toString());
        } catch (Exception e) {
            // Return error response if any exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
