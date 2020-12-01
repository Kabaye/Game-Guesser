package edu.bsu.games.guesser.config;

import edu.bsu.games.guesser.data.storage.DataStorage;
import edu.bsu.games.guesser.data.storage.Game;
import edu.bsu.games.guesser.data.storage.Genre;
import edu.bsu.games.guesser.utils.ArrayUtils;
import edu.bsu.games.guesser.utils.StringUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 01.12.2020
 */
@Component
@Slf4j
public class DataStorageBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Object> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(DataStorage.class)) {
            map.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (map.containsKey(beanName)) {
            Object originalBean = map.get(beanName);
            return fulfillDataStorage((DataStorage) originalBean);
        }
        return bean;
    }

    private DataStorage fulfillDataStorage(DataStorage dataStorage) {
        dataStorage.getGenres().sort(Comparator.comparing(Genre::getGenre));
        for (Genre genre : dataStorage.getGenres()) {
            for (Game game : genre.getExamples()) {
                game.getFeatures().sort(Comparator.naturalOrder());
            }
            genre.getExamples().sort(Comparator.comparing(Game::getName));
        }

        dataStorage.getAllFeatures().sort(Comparator.naturalOrder());

        String generalBitMap = dataStorage.getAllFeatures().stream().map(s -> "0").collect(Collectors.joining());
        for (Genre genre : dataStorage.getGenres()) {
            for (Game game : genre.getExamples()) {
                StringBuilder bitMap = new StringBuilder(generalBitMap);
                for (String s : game.getFeatures()) {
                    int i = dataStorage.getAllFeatures().indexOf(s);
                    bitMap.replace(i, i + 1, "1");
                }
                String bitMapOfFeatures = bitMap.toString();
                game.setFeaturesBitSet(bitMapOfFeatures);
                genre.getBitMapsOfExamples().add(bitMapOfFeatures);
            }
        }

        int featuresSize = dataStorage.getAllFeatures().size();
        int genresSize = dataStorage.getGenres().size();
        Double[] b = ArrayUtils.fulfillArray(featuresSize, Double.class, () -> 0.0);
        Double[][] a = ArrayUtils.fulfillArray(featuresSize, genresSize, Double.class, () -> 0.0);
        Double[] temporaryB = ArrayUtils.fulfillArray(genresSize, Double.class, () -> 0.0);

        for (int i = 0; i < featuresSize; i++) {
            for (int j = 0; j < genresSize; j++) {
                Genre genre = dataStorage.getGenres().get(j);
                int finalI = i;
                temporaryB[j] = genre.getBitMapsOfExamples().stream()
                        .reduce(0.0,
                                (aDouble, s) -> aDouble + Double.parseDouble(s.substring(finalI, finalI + 1)),
                                Double::sum) / genre.getExamples().size();
                b[i] += temporaryB[j];
            }
            b[i] /= genresSize;

            for (int j = 0; j < genresSize; j++) {
                a[i][j] = Math.abs(temporaryB[j] - b[i]);
            }
        }


        Double[] featuresValuesSum = ArrayUtils.fulfillArray(dataStorage.getGenres().size(), Double.class, () -> 0.0);
        for (int i = 0; i < dataStorage.getGenres().size(); i++) {
            for (int j = 0; j < dataStorage.getAllFeatures().size(); j++) {
                featuresValuesSum[i] += a[j][i];
            }
        }

        log.debug("Values in feature matrix: {}", StringUtils.toString(a));
        return dataStorage.setFeaturesValues(a)
                .setFeaturesValuesSum(featuresValuesSum);
    }
}
