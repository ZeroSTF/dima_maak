package tn.esprit.dima_maak.serviceimpl;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ServiceBadworld implements IServicebad{

    @Override
    public ResponseEntity<String> filterBadWords1(String text) {
        try {
            // API link mte3ha
            String url = "https://api.apilayer.com/bad_words?censor_character=censor_character";

            // key
            String apiKey = "0zUXvbMB20iIrrbVYGxVYV0BFr8RW5QO";

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
