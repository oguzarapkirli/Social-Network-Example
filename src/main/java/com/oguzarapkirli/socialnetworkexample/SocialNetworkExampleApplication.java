package com.oguzarapkirli.socialnetworkexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialNetworkExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkExampleApplication.class, args);
    }

}
