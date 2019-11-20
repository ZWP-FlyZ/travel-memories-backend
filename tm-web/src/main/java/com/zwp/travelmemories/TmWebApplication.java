package com.zwp.travelmemories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TmWebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TmWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        TestWebApp.dosome();
    }
}
