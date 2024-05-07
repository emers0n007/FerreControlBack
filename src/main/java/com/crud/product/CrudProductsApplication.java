package com.crud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CrudProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudProductsApplication.class, args);
    }

}
