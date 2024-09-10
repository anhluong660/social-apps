package com.swordfish.social.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("[Social-Service]: Hello World !", HttpStatus.OK);
    }
}
