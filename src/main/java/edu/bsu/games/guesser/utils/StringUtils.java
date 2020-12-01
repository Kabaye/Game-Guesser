package edu.bsu.games.guesser.utils;

import java.util.StringJoiner;

/**
 * created by @Kabaye
 * date 01.12.2020
 */
public class StringUtils {

    @SuppressWarnings("all")
    public static <T extends Number> String toString(T[][] array) {
        StringJoiner mStr = new StringJoiner("", "\n", "");
        for (int i = -1; i < array[0].length; i++) {
            mStr.add(String.format("%8d", i)).add(" ");
        }
        mStr.add("\n");
        for (int i = 0; i < array.length; i++) {
            mStr.add(String.format("%8d", i)).add(" ");
            for (T t : array[i]) {
                mStr.add(String.format("%4f", t)).add(" ");
            }
            mStr.add("\n");
        }
        return mStr.toString();
    }

}
