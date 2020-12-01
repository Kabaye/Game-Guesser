package edu.bsu.games.guesser.data.storage;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

@Data
@Accessors(chain = true)
public class DataStorage {
    private final List<String> allFeatures;
    private final List<Genre> genres;
    private Double[][] featuresValues;
    private Double[] featuresValuesSum;
}
