package edu.bsu.games.guesser.data.storage;

import java.util.HashSet;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

@Data
@Accessors(chain = true)
public class Genre {
    private String genre;
    private List<Game> examples;
    private HashSet<String> bitMapsOfExamples = new HashSet<>();
}
