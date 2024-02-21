package com.bananaapps.bananamusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BananaMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BananaMusicApplication.class, args);
    }

}