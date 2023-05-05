package com.gibster.nm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan("com.gibster.repo.nm")
@EnableEurekaClient
@EnableFeignClients
public class NurseApplication {

  public static void main(String[] args) {
    SpringApplication.run(NurseApplication.class, args);
  }

}
