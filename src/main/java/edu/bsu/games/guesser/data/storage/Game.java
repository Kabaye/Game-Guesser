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
public class Game {
    private String name;
    private List<String> features;
    private String bitMapOfFeatures;
}
