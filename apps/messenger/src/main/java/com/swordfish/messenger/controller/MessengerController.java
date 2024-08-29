package com.swordfish.messenger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messenger")
public class MessengerController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("[Messenger]: Hello World !", HttpStatus.OK);
    }
}
