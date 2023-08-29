package practice.day39workshopmarvelserver.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import practice.day39workshopmarvelserver.model.MarvelChar;
import practice.day39workshopmarvelserver.repository.MarvelCharRepository;

@Service
public class MarvelService {

    @Autowired
    private MarvelCharRepository repo;

    public void saveCharacter(String jsonString) {

        try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonArray jsonArray = o.getJsonObject("data").getJsonArray("results");
            for (JsonValue jsonValue : jsonArray) {
                MarvelChar mChar = new MarvelChar();
                JsonObject jsonObj = jsonValue.asJsonObject();
                mChar.toMarvelChar(jsonObj);
                System.out.println("CHARACTER NAME>>>>>>>>>>>>>>" + mChar.getName());
                repo.saveCharacter(mChar);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public MarvelChar getMarvelCharById(Integer id){
        return repo.getMarvelCharById(id);
    }
}
