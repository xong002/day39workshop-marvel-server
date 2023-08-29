package practice.day39workshopmarvelserver.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import practice.day39workshopmarvelserver.service.APIservice;

@RestController
@RequestMapping("/api")
public class MarvelController {

    @Autowired
    private APIservice svc;

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

        return svc.getCharacters(nameStartsWith.trim(), limit.get(), offset.get());
    }

}
