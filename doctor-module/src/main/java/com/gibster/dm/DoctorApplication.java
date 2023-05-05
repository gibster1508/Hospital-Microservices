package com.gibster.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan("com.gibster.repo.dm")
@EnableEurekaClient
@EnableFeignClients
public class DoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorApplication.class, args);
    }

}
