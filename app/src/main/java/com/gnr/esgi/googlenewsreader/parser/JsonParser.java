package com.gnr.esgi.googlenewsreader.parser;

import android.util.Log;

import com.gnr.esgi.googlenewsreader.helper.NewsHelper;
import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Source;
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<News> parse(ArrayList<LinkedTreeMap<String, Object>> maps) {
        List<News> listNews = new ArrayList<>();

        try {
            for(LinkedTreeMap<String, Object> map : maps) {
                News news = new News();

                news.setTitle((String) map.get(NewsHelper.KEY_TITLE));
                news.setContent((String) map.get(NewsHelper.KEY_CONTENT));

                //Parse date from string to Date
                //news.setDate((String) map.get(NewsHelper.KEY_DATE));

                LinkedTreeMap<String, Object> picture = (LinkedTreeMap<String, Object>) map.get(NewsHelper.KEY_PICTURE);
                news.setPicture(picture != null
                        ? (String) picture.get("url")
                        : "");

                Source source = new Source();
                source.setName((String) map.get(NewsHelper.KEY_SOURCE));
                source.setUrl((String) map.get(NewsHelper.KEY_LINK));
                news.setSource(source);

                listNews.add(news);
            }
        } catch(Exception e) {
            Log.w("JsonParser", "Error while reading news");
        }

        return listNews;
    }
}
