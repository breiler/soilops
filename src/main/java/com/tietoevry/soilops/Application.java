package com.tietoevry.soilops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.sqlite.date.FastDateFormat;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}