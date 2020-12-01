package edu.bsu.games.guesser.shutdown;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Component
@RequiredArgsConstructor
public class ShutdownManager {
    private final ApplicationContext applicationContext;

    public void initializeShutdown(int returnCode) {
        System.exit(SpringApplication.exit(applicationContext, () -> returnCode));
    }
}
