package edu.bsu.games.guesser.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * created by @Kabaye
 * date 01.12.2020
 */

public class FunctionsMap<T, V> {
    private final Map<T, V> innerMap = new HashMap<>();

    public FunctionsMap<T, V> put(T key, V value) {
        innerMap.put(key, value);
        return this;
    }

    public V get(T key) {
        return innerMap.get(key);
    }
}
