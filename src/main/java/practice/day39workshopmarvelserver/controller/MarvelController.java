package practice.day39workshopmarvelserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import practice.day39workshopmarvelserver.service.APIservice;

@RestController
@RequestMapping("/api")
public class MarvelController {
    
    @Autowired
    private APIservice svc;

    @GetMapping("/characters")
    public ResponseEntity<String> getCharacters(){
        return svc.getCharacters();
    }

}
