package com.gnr.esgi.googlenewsreader.helper;

public class NewsHelper {

    public static final String KEY_API_URL = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="; //"http://news.google.com/news?output=rss&q=";

    public static final String KEY_TITLE = "titleNoFormatting";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_LINK = "unescapedUrl";
    public static final String KEY_DATE = "publishedDate";
    public static final String KEY_SOURCE = "publisher";
    public static final String KEY_PICTURE = "image";

    public static String getUrl(String tagName) {
        return KEY_API_URL + tagName;
    }
}
