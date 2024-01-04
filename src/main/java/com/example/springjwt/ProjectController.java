package com.example.springjwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {
    private List<String> messages = new ArrayList<>();

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestParam String message) {
        messages.add(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get")
    public String getMessages() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : messages) {
            stringBuilder.append("Anonym: " + message + "\n");
            stringBuilder.append(" \n");
        }
        return stringBuilder.toString();
    }
}
