package com.gnr.esgi.googlenewsreader.utils;

public class Config {

    public static final String BASE_API_URL = "http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="; //"http://news.google.com/news?output=rss&q=";

    public static final String ARTICLE_KEY_ID = "id";
    public static final String ARTICLE_KEY_TITLE = "titleNoFormatting";
    public static final String ARTICLE_KEY_CONTENT = "content";
    public static final String ARTICLE_KEY_CREATED_AT = "publishedDate";
    public static final String ARTICLE_KEY_SOURCE_NAME = "publisher";
    public static final String ARTICLE_KEY_SOURCE_URL = "unescapedUrl";
    public static final String ARTICLE_KEY_PICTURE = "image";
    public static final String ARTICLE_KEY_PICTURE_URL = "url";

    public static final boolean DISPLAY_LOG = true;
    public static final String LOG_PREFIX = "refresh";
    public static final int GET_ARTICLES_FROM_API = 20;
}
