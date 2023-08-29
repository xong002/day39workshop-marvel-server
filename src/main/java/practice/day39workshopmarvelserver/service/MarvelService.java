package practice.day39workshopmarvelserver.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import practice.day39workshopmarvelserver.model.Comment;
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
                // System.out.println("CHARACTER NAME>>>>>>>>>>>>>>" + mChar.getThumbnail());
                repo.saveCharacter(mChar);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public MarvelChar getMarvelCharById(Integer id){
        return repo.getMarvelCharById(id);
    }

    public void saveComment(Integer charId, String jsonString){
        JsonReader r = Json.createReader(new StringReader(jsonString));
        JsonObject o = r.readObject();
        Comment newComment = new Comment();
        newComment.setComments(o.getString("comments"));
        newComment.setCharId(charId);
        repo.saveComment(newComment);
    }

    public List<Comment> getComments(Integer charId){
        return repo.getComments(charId);
    }
}
