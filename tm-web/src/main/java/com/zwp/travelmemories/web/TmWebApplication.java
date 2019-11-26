package com.zwp.travelmemories.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.zwp.travelmemories"},
                            exclude = {DataSourceAutoConfiguration.class})
public class TmWebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TmWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
