package com.gnr.esgi.googlenewsreader.factory;

import android.util.Log;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.parser.ArticleParser;
import com.gnr.esgi.googlenewsreader.utils.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ArticleFactory {

    public static List<Article> createArticlesList(String response) {
        ArrayList<Article> parsedArticlesList = new ArrayList<>();

        try {
            JSONObject source = new JSONObject(response);

            JSONArray array = source.getJSONObject("responseData").getJSONArray("results");

            for (int i=0; i < array.length(); i++)
                parsedArticlesList.add(ArticleParser.parse(new Article(array.getJSONObject(i))));

        } catch (JSONException e) {
            if(Config.DISPLAY_LOG)
                Log.w("ArticleFactory", e);
        }

        return parsedArticlesList;
    }
}