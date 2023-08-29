package practice.day39workshopmarvelserver.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;

@Service
public class APIservice {

    private String marvelURL = "http://gateway.marvel.com/v1/public/characters";
    private String publicAPIKey = "3924f6390d0fdc30f3fc44f644d13034";
    private String privateAPIKey = "c6b21eebe218897107a4fa771befa7737da36d79";

    public ResponseEntity<String> getCharacters(String nameStartsWith, Integer limit, Integer offset) {
        RestTemplate template = new RestTemplate();
        try {
            //MD5 hashing
            MessageDigest md = MessageDigest.getInstance("MD5");
            String newDate = String.valueOf(new Date().getTime() / 1000);
            String stringToBeHashed = newDate + privateAPIKey + publicAPIKey;
            byte[] mdByteArray = md.digest(stringToBeHashed.getBytes());
            BigInteger bigInt = new BigInteger(1, mdByteArray);
            String hash = bigInt.toString(16);

            String uriString = UriComponentsBuilder.fromUriString(marvelURL)
                    .queryParam("ts", newDate)
                    .queryParam("apikey", publicAPIKey)
                    .queryParam("hash", hash)
                    .queryParam("nameStartsWith", nameStartsWith)
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .toUriString();
            ResponseEntity<String> response = template.getForEntity(uriString, String.class);
            return response;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Json.createObjectBuilder()
                            .add("error", e.getMessage())
                            .build()
                            .toString());
        }

    }
}
