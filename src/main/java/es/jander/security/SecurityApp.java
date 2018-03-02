package es.jander.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApp
{
    public static void main (String [] args)
    {
        new SpringApplication(SecurityApp.class).run();
    }
}
