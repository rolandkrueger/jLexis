package org.jlexis.ui;

import org.jlexis.ui.spring.security.UserDetailsServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JLexisWebappApplication {
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{
                JLexisWebappApplication.class,
                UserDetailsServiceConfiguration.class}, args);
    }
}
