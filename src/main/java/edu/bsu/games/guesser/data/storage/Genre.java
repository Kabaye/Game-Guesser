package edu.bsu.games.guesser.data.storage;

import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
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

    @Override
    public String toString() {
        StringJoiner genreJoiner = new StringJoiner("", "<html><font color=red>" + genre + "</font>", "");
        genreJoiner.add(": Игры: ");
        for (Game game : examples) {
            genreJoiner.add(game.toString());
        }
        return genreJoiner.toString();
    }
}
