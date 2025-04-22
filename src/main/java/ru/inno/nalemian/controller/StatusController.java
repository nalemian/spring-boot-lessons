package ru.inno.nalemian.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/test/{status}")
    public ResponseEntity<String> getWithNeededStatus(@PathVariable int status) {
        return ResponseEntity.status(status).body("test");
    }
}
