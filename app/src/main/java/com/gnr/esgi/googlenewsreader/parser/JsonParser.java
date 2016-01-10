package com.gnr.esgi.googlenewsreader.parser;

import android.util.Log;

import com.gnr.esgi.googlenewsreader.helper.NewsHelper;
import com.gnr.esgi.googlenewsreader.model.Article;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<Article> parse(ArrayList<LinkedTreeMap<String, Object>> maps) {
        List<Article> listNews = new ArrayList<>();

        try {
            for(LinkedTreeMap<String, Object> map : maps) {
                Article article = new Article();

                article.setTitle((String) map.get(NewsHelper.KEY_TITLE));
                article.setContent((String) map.get(NewsHelper.KEY_CONTENT));

                //Parse date from string to Date
                //news.setDate((String) map.get(NewsHelper.KEY_DATE));

                LinkedTreeMap<String, Object> picture = (LinkedTreeMap<String, Object>) map.get(NewsHelper.KEY_PICTURE);
                article.setPicture(picture != null
                        ? (String) picture.get("url")
                        : null);

                article.setSourceName((String) map.get(NewsHelper.KEY_SOURCE));
                article.setSourceUrl((String) map.get(NewsHelper.KEY_LINK));

                listNews.add(article);
            }
        } catch(Exception e) {
            Log.w("JsonParser", "Error while reading news");
        }

        return listNews;
    }
}
