package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Source;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ismail on 04-01-2016.
 */
public class JsonParser {
    public static List<News> parse(ArrayList<LinkedTreeMap<String, Object>> maps) {
        List<News> listNews = new ArrayList<>();
        for(LinkedTreeMap<String, Object> map : maps) {
            News news = new News();

            news.setTitle((String) map.get("titleNoFormatting"));
            news.setContent((String) map.get("content"));

            //Parse date from string to Date
            //news.setDate((String) map.get("publishedDate"));

            Source source = new Source();
            source.setName((String) map.get("publisher"));
            source.setUrl((String) map.get("unescapedUrl"));

            listNews.add(news);
        }

        return listNews;
    }
}
