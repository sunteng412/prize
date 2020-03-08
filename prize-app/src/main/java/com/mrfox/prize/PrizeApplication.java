package com.mrfox.prize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PrizeApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrizeApplication.class, args);
    }

}
