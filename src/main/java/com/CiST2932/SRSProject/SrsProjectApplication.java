package com.CiST2932.SRSProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.CiST2932.SRSProject", "com.CiST2932"})
public class SrsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrsProjectApplication.class, args);
    }

}
