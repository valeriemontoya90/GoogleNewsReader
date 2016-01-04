package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.helper.NewsHelper;
import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Source;
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<News> parse(ArrayList<LinkedTreeMap<String, Object>> maps) {
        List<News> listNews = new ArrayList<>();
        for(LinkedTreeMap<String, Object> map : maps) {
            News news = new News();

            news.setTitle((String) map.get(NewsHelper.KEY_TITLE));
            news.setContent((String) map.get(NewsHelper.KEY_CONTENT));

            //Parse date from string to Date
            //news.setDate((String) map.get(NewsHelper.KEY_DATE));

            news.setPicture((String) ((LinkedTreeMap<String, Object>) map.get("image")).get("url"));

            Source source = new Source();
            source.setName((String) map.get(NewsHelper.KEY_SOURCE));
            source.setUrl((String) map.get(NewsHelper.KEY_LINK));
            news.setSource(source);

            listNews.add(news);
        }

        return listNews;
    }
}
