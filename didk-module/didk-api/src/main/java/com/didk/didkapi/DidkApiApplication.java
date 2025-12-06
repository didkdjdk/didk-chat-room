package com.didk.didkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DidkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidkApiApplication.class, args);
    }

}
