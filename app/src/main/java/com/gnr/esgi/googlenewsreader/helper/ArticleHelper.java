package com.gnr.esgi.googlenewsreader.helper;

import android.util.Log;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.constants.APIConstants;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.parser.HtmlParser;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.gnr.esgi.googlenewsreader.utils.URLBuilder;
import com.gnr.esgi.googlenewsreader.webservices.ArticleSearchTask;

import java.util.List;

public class ArticleHelper {

    // Get headlines of first page
    public static String getUrl() {
        return getUrl(0);
    }

    // Get headlines of specific page
    public static String getUrl(Integer page) {
        URLBuilder builder = buildUrl();

        // If no tag is found, show headlines news
        builder.addParameter(APIConstants.PARAMETER_TOPIC, Config.API_TOPIC);
        builder.addParameter(APIConstants.PARAMETER_START, page);

        return builder.getUrl();
    }

    // Get news from tag of first page
    public static String getUrl(String tagName) {
        return getUrl(tagName, 0);
    }

    // Get news from tag of specific page
    public static String getUrl(String tagName, Integer page) {
        URLBuilder builder = buildUrl();

        builder.addParameter(APIConstants.PARAMETER_TAG, tagName);
        builder.addParameter(APIConstants.PARAMETER_START, page);

        return builder.getUrl();
    }

    private static URLBuilder buildUrl() {
        URLBuilder builder = new URLBuilder(APIConstants.BASE_URL);

        builder.addParameter(APIConstants.PARAMETER_VERSION, Config.API_VERSION);
        builder.addParameter(APIConstants.PARAMETER_ORDER, Config.API_ORDER);
        builder.addParameter(APIConstants.PARAMETER_RESULTS, Config.API_RESULTS);

        return builder;
    }

    public static Integer countRecentNews(List<Article> recent, List<Article> old) {
        Integer count = recent.size();

        for (Article recentNews : recent)
            for (Article oldNews : old)
                if(recentNews == oldNews)
                    count--;

        return count;
    }

    public static void refreshArticles() {
        // Clear database
        GNRApplication.getDbHelper().deleteArticles();

        // Repopulate with fresh online news
        for (final Tag tag : GNRApplication.getUser().getData().getTags()) {
            ArticleSearchTask search = new ArticleSearchTask();
            search.execute(tag);
        }
    }

    public static void saveArticleInDataBase(Article article, Tag tag) {
        if(Config.DISPLAY_LOG)
            Log.d(Config.LOG_PREFIX, "saveArticleInDataBase");

            article.setLinkTagName(tag.getTagName());
            article.setHasAlreadyReadValue(false);

            GNRApplication.getDbHelper().addArticle(article);
    }

    public static void saveArticlesListInDataBase(Tag tag) {
        if(Config.DISPLAY_LOG)
            Log.d(Config.LOG_PREFIX, "saveArticlesListInDataBase");

        for (Article article : tag.getArticlesList()) {
            article.setLinkTagName(tag.getTagName());
            article.setHasAlreadyReadValue(false);

            GNRApplication.getDbHelper().addArticle(article);
        }
    }
}
