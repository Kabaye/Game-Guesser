package edu.bsu.games.guesser.utils;

import java.lang.reflect.Array;
import java.util.function.Supplier;

/**
 * created by @Kabaye
 * date 01.12.2020
 */

public class ArrayUtils {
    @SuppressWarnings("unchecked")
    public static <T> T[] fulfillArray(int length, Class<T> clazz, Supplier<T> creator) {
        T[] array = (T[]) Array.newInstance(clazz, length);
        for (int i = 0; i < array.length; i++) {
            array[i] = creator.get();
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] fulfillArray(int rows, int columns, Class<T> clazz, Supplier<T> creator) {
        T[][] array = (T[][]) Array.newInstance(clazz, rows, columns);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = creator.get();
            }
        }
        return array;
    }
}
