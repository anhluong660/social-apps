package com.swordfish.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerApplication.class, args);

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("[[ Start User Manager Application Success ]]");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
    }
}
