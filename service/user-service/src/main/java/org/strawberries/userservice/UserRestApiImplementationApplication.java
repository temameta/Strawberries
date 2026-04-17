package org.strawberries.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserRestApiImplementationApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRestApiImplementationApplication.class, args);
    }

}
