package com.gibster.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan("com.gibster.repo.pm")
@EnableEurekaClient
@EnableFeignClients
public class PatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

}
