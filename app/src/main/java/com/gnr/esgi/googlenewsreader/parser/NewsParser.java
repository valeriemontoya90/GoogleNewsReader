package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.model.News;

public class NewsParser {
    public static News parse(News news) {

        news.setTitle(HtmlParser.parse(news.getTitle()));
        news.setContent(HtmlParser.parse(news.getContent()));

        return news;
    }
}
