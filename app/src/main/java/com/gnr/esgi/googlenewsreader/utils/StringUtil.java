package com.gnr.esgi.googlenewsreader.utils;

public class StringUtil {

    public static String capitalize(String source) {
        return Character.toUpperCase(source.charAt(0))
                + source.substring(1);
    }
}
