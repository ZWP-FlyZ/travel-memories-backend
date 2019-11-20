package com.zwp.travelmemories.repo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TmRepoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TmRepoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
