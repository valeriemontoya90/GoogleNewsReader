package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.model.News;

public class NewsParser {
    public static News parse(News news) {

        news.setTitle(HTMLParser.parse(news.getTitle()));
        news.setContent(HTMLParser.parse(news.getContent()));

        return news;
    }
}
