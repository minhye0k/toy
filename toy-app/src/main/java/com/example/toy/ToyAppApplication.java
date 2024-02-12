package com.example.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ToyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyAppApplication.class, args);
    }

}
