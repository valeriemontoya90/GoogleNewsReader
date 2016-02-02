package com.gnr.esgi.googlenewsreader.webservices;

import com.gnr.esgi.googlenewsreader.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Article> parseResultPage(String response) {
        ArrayList<Article> parsedArticlesList = new ArrayList<Article>();
        try {
            JSONObject jObj = new JSONObject(response);

            JSONObject json_response_data = jObj.getJSONObject("responseData");
            JSONArray json_results = json_response_data.getJSONArray("results");

            for (int i=0; i < json_results.length(); i++) {
                JSONObject item = json_results.getJSONObject(i);
                parsedArticlesList.add(new Article(item));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

            /*for(LinkedTreeMap<String, Object> map : maps) {
                Article article = new Article();

                article.setTitle((String) map.get(Config.ARTICLE_KEY_TITLE));
                article.setContent((String) map.get(Config.ARTICLE_KEY_CONTENT));

                //Parse date from string to Date
                //article.setDate((String) map.get(ArticleHelper.ARTICLE_KEY_CREATED_AT));

                LinkedTreeMap<String, Object> pictureUrl = (LinkedTreeMap<String, Object>) map.get(Config.ARTICLE_KEY_PICTURE);
                //article.setPicture(pictureUrl != null ? new Picture((String) pictureUrl.get("url")) : new Picture());
                article.setPictureUrl(pictureUrl.get("url").toString());

                Source source = new Source();
                source.setSourceName((String) map.get(Config.ARTICLE_KEY_SOURCE_NAME));
                source.setSourceUrl((String) map.get(Config.ARTICLE_KEY_LINK));
                article.setSource(source);

                listNews.add(ArticleParser.parse(article));
            }*/

        return parsedArticlesList;
    }
}