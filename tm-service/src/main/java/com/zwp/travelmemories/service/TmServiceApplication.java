package com.zwp.travelmemories.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zwp.travelmemories"})
public class TmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmServiceApplication.class, args);
    }

}
