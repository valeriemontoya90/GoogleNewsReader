package com.gnr.esgi.googlenewsreader.parser;

import android.util.Log;
import com.gnr.esgi.googlenewsreader.utils.Constants;
import com.gnr.esgi.googlenewsreader.model.Picture;
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

                article.setTitle((String) map.get(Constants.ARTICLE_KEY_TITLE));
                article.setContent((String) map.get(Constants.ARTICLE_KEY_CONTENT));

                //Parse date from string to Date
                //article.setDate((String) map.get(ArticleHelper.ARTICLE_KEY_DATE));

                LinkedTreeMap<String, Object> pictureUrl = (LinkedTreeMap<String, Object>) map.get(Constants.ARTICLE_KEY_PICTURE);
                article.setPicture(pictureUrl != null
                                    ? new Picture((String) pictureUrl.get("url"))
                                    : new Picture());

                Source source = new Source();
                source.setName((String) map.get(Constants.ARTICLE_KEY_SOURCE));
                source.setUrl((String) map.get(Constants.ARTICLE_KEY_LINK));
                article.setSource(source);

                listNews.add(ArticleParser.parse(article));
            }
        } catch(Exception e) {
            Log.w("JsonParser", "Error while reading news");
        }

        return listNews;
    }
}
