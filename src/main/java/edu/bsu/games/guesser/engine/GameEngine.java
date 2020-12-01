package edu.bsu.games.guesser.engine;

import edu.bsu.games.guesser.data.storage.DataStorage;
import edu.bsu.games.guesser.data.storage.Game;
import edu.bsu.games.guesser.data.storage.Genre;
import edu.bsu.games.guesser.shutdown.ShutdownManager;
import edu.bsu.games.guesser.utils.ArrayUtils;
import edu.bsu.games.guesser.utils.FunctionsMap;
import edu.bsu.games.guesser.utils.Pair;
import edu.bsu.games.guesser.view.ViewController;
import java.awt.EventQueue;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        String unknownGenreFeaturesBitSet = viewController.showFeaturesMenu();

//        String[] split = unknownGenreFeaturesBitSet.split("");
//        List<String> featureNames = IntStream.range(0, dataStorage.getAllFeatures().size())
//                .mapToObj(value -> Pair.of(value, split[value]))
//                .filter(pair -> pair.getSecond().equals("1"))
//                .map(pair -> dataStorage.getAllFeatures().get(pair.getFirst()))
//                .collect(Collectors.toList());


        Double[] featuresCheckArr = ArrayUtils.fulfillArray(dataStorage.getGenres().size(), Double.class, () -> 0.0);
        double max = -1.0;
        Genre genre = null;
        int i = 0;
        for (Genre currentGenre : dataStorage.getGenres()) {
            double mu = calcMu(currentGenre, unknownGenreFeaturesBitSet, i);
            featuresCheckArr[i] = mu;
            if (mu > max) {
                max = mu;
                genre = currentGenre;
            }
            i++;
        }
        viewController.showResult(Objects.requireNonNull(genre).getGenre(), featuresCheckArr);
    }

    public void shutdown() {
        shutdownManager.initializeShutdown(0);
    }

    public void showTrainingSet() {
        viewController.showTrainingSet();
    }

    private Double calcMu(Genre currentGenre, String featureBitSet, int genreIndex) {
        double max = -1;
        for (Game game : currentGenre.getExamples()) {
            double c = 0;
            for (int i = 0; i < dataStorage.getAllFeatures().size(); i++) {
                c += (featureBitSet.substring(i, i + 1).equals(game.getFeaturesBitSet().substring(i, i + 1)) ? 1 : -1)
                        * dataStorage.getFeaturesValues()[i][genreIndex];
            }
            c = (dataStorage.getFeaturesValuesSum()[genreIndex] != 0) ? c / dataStorage.getFeaturesValuesSum()[genreIndex] : 0;
            max = Math.max(c, max);
        }
        return max;
    }
}
