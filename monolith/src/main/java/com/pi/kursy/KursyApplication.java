package com.pi.kursy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KursyApplication {

    public static void main(String[] args) {
        SpringApplication.run(KursyApplication.class, args);
    }

}
