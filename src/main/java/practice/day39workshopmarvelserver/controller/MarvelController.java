package practice.day39workshopmarvelserver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import practice.day39workshopmarvelserver.model.Comment;
import practice.day39workshopmarvelserver.model.MarvelChar;
import practice.day39workshopmarvelserver.service.APIservice;
import practice.day39workshopmarvelserver.service.MarvelService;

@RestController
@RequestMapping("/api")
public class MarvelController {

    @Autowired
    private APIservice svc;

    @Autowired
    private MarvelService marvelSvc;

    @GetMapping("/characters")
    public ResponseEntity<String> getCharacters(@RequestParam String nameStartsWith,
            @RequestParam Optional<Integer> limit, @RequestParam Optional<Integer> offset) {

        if (nameStartsWith.trim().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(Json.createObjectBuilder()
                            .add("message", "nameStartsWith cannot be blank")
                            .toString());
        }
        if (limit.isEmpty()) {
            limit = Optional.of(20);
        }
        if (offset.isEmpty()) {
            offset = Optional.of(0);
        }
        
        // call from Marvel API and save to MySQL
        String response = svc.getCharacters(nameStartsWith.trim(), limit.get(), offset.get()).getBody();
        marvelSvc.saveCharacter(response);

        return svc.getCharacters(nameStartsWith.trim(), limit.get(), offset.get());
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<MarvelChar> getCharacterById(@PathVariable Integer characterId){
        MarvelChar result = marvelSvc.getMarvelCharById(characterId);
        if(result == null){
            return ResponseEntity.notFound().build();
            // TODO: call from Marvel API if not found
        }
        return ResponseEntity.status(200).body(result);
    }
    
    @PostMapping(path = "/character/{characterId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postComment(@PathVariable Integer characterId, @RequestBody String payload){
        marvelSvc.saveComment(characterId, payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/character/{characterId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer characterId){
        List<Comment> list = marvelSvc.getComments(characterId);
        return ResponseEntity.status(200).body(list);
    }

}
