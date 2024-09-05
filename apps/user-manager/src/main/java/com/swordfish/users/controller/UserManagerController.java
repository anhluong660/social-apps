package com.swordfish.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagerController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("[User-Manager]: Hello World !", HttpStatus.OK);
    }
}
