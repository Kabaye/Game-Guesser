package edu.bsu.games.guesser;

import java.awt.EventQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class GamesGuesserApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GamesGuesserApplication.class)
                .headless(false).run(args);
    }

}
