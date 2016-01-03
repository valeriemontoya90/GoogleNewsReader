package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.parser.HTMLParser;

/**
 * Created by Ismail on 03-01-2016.
 */
public class NewsParser {
    public static News parse(News news) {

        news.setTitle(HTMLParser.parse(news.getTitle()));
        news.setContent(HTMLParser.parse(news.getContent()));

        return news;
    }
}
