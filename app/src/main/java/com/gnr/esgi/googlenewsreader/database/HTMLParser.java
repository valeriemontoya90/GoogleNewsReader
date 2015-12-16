package com.gnr.esgi.googlenewsreader.database;

/**
 * Created by Ismail on 14-12-2015.
 */

public final class HTMLParser {
    public static final String ELLIPSIS = "&ellip;";
    public static final String TAG_PATTERN = "<[^>]+>";
    public static final String WHITESPACE_PATTERN = "\\s!+";

    public static String removeTags(String source) {
        return source
                .replaceAll(TAG_PATTERN, "")
                .replaceAll(WHITESPACE_PATTERN, " ")
                .trim();
    }

    public String parse(String source) {
        final int bodyIndex = source.indexOf("<body");

        if(bodyIndex > -1)
            source = source.substring(bodyIndex);

        return removeTags(source);
    }
}
