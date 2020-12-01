package edu.bsu.games.guesser.engine;

import edu.bsu.games.guesser.data.storage.DataStorage;
import edu.bsu.games.guesser.shutdown.ShutdownManager;
import edu.bsu.games.guesser.utils.FunctionsMap;
import edu.bsu.games.guesser.view.ViewController;
import java.awt.EventQueue;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

@Service
@RequiredArgsConstructor
public class GameEngine {
    private final ViewController viewController;
    private final DataStorage dataStorage;
    private final ShutdownManager shutdownManager;

    private final FunctionsMap<Integer, Runnable> mainMenuFunctions = new FunctionsMap<>();

    @PostConstruct
    public void postConstruct() {
        mainMenuFunctions
                .put(0, this::showFeatureValues)
                .put(1, this::showTrainingSet)
                .put(2, this::startGame)
                .put(3, this::shutdown)
                .put(-1, this::shutdown);
    }

    @EventListener
    public void onLoad(ContextRefreshedEvent contextRefreshedEvent) {
        EventQueue.invokeLater(this::initializeGame);
    }

    private void initializeGame() {
        loopGame();
    }

    @SuppressWarnings("all")
    private void loopGame() {
        while (true) {
            int i = viewController.showMenu();
            try {
                mainMenuFunctions.get(i).run();
            } catch (Exception e) {
                viewController.showErrorMessage(e.getMessage());
            }
        }
    }

    public void showFeatureValues() {
        viewController.showFeatureValues();
    }

    public void startGame() {

    }

    public void shutdown() {
        shutdownManager.initializeShutdown(0);
    }

    public void showTrainingSet() {

    }
}
