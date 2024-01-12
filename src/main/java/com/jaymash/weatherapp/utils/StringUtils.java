package com.jaymash.weatherapp.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        return Arrays.stream(text.split("\\s+"))
                .map((word) -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String fromUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
