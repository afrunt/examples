package com.afrunt.examples.springboot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Andrii Frunt
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AdminClient {
    public static void main(String[] args) {
        SpringApplication.run(AdminClient.class, args);
    }
}
