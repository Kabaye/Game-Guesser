package edu.bsu.games.guesser.utils;

import lombok.Data;

/**
 * created by @Kabaye
 * date 15.11.2020
 */

@Data(staticConstructor = "of")
public class Pair<F, S> {
    private final F first;
    private final S second;
}
