package com.gnr.esgi.googlenewsreader.parser;

import android.util.Log;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.model.Source;
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

                article.setTitle((String) map.get(ArticleConstants.KEY_TITLE));
                article.setContent((String) map.get(ArticleConstants.KEY_CONTENT));
                
                //Parse date from string to Date
                //article.setDate((String) map.get(ArticleHelper.KEY_DATE));

                LinkedTreeMap<String, Object> picture = (LinkedTreeMap<String, Object>) map.get(ArticleConstants.KEY_PICTURE);
                article.setPicture(picture != null
                        ? (String) picture.get("url")
                        : null);

                Source source = new Source();
                source.setName((String) map.get(ArticleConstants.KEY_SOURCE));
                source.setUrl((String) map.get(ArticleConstants.KEY_LINK));
                article.setSource(source);

                listNews.add(article);
            }
        } catch(Exception e) {
            Log.w("JsonParser", "Error while reading news");
        }

        return listNews;
    }
}
