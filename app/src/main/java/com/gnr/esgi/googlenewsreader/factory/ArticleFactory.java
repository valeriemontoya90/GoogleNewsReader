package com.gnr.esgi.googlenewsreader.factory;

import android.database.Cursor;
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

    public static List<Article> fromJson(String response) {
        ArrayList<Article> articlesList = new ArrayList<>();

        try {
            JSONObject source = new JSONObject(response);

            JSONArray array = source.getJSONObject("responseData").getJSONArray("results");

            for (int i=0; i < array.length(); i++)
                articlesList.add(ArticleParser.parse(new Article(array.getJSONObject(i))));

        } catch (JSONException e) {
            if(Config.DISPLAY_LOG)
                Log.w("ArticleFactory", e);
        }

        return articlesList;
    }

    public static List<Article> fromCursor(Cursor cursor) {
        ArrayList<Article> articlesList = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            articlesList.add(new Article(cursor));

        return articlesList;
    }
}