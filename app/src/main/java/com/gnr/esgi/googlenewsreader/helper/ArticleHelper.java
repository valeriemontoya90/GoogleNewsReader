package com.gnr.esgi.googlenewsreader.helper;

import android.util.Log;
import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.constants.APIConstants;
import com.gnr.esgi.googlenewsreader.constants.TagConstants;
import com.gnr.esgi.googlenewsreader.factory.ArticleFactory;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.gnr.esgi.googlenewsreader.utils.DateUtil;
import com.gnr.esgi.googlenewsreader.utils.NetworkUtil;
import com.gnr.esgi.googlenewsreader.utils.URLBuilder;
import com.gnr.esgi.googlenewsreader.tasks.ArticleSearchTask;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticleHelper {

    public static final String TAG = "ArticleHelper";

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

    // Get news fromCursor tag of first page
    public static String getUrl(String tagName) {
        return getUrl(tagName, 0);
    }

    // Get news fromCursor tag of specific page
    public static String getUrl(String tagName, Integer page) {
        URLBuilder builder = buildUrl();

        builder.addParameter(APIConstants.PARAMETER_TAG, tagName);
        builder.addParameter(APIConstants.PARAMETER_START, page);

        return builder.getUrl();
    }

    private static URLBuilder buildUrl() {
        URLBuilder builder = new URLBuilder(APIConstants.BASE_URL);

        builder.addParameter(APIConstants.PARAMETER_VERSION, Config.API_VERSION);
        builder.addParameter(APIConstants.PARAMETER_EDITION, Config.API_EDITION);
        //builder.addParameter(APIConstants.PARAMETER_ORDER, Config.API_ORDER);
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

    public static void setRead(Article article) {
        article.setRead(true);
        GNRApplication.getDbHelper().updateArticle(article);
    }

    public static Article getArticle(Long articleId) {
        return ArticleFactory.fromCursor(
                GNRApplication.getDbHelper().getArticle(
                        articleId
                )
        );
    }

    public static List<Article> getBookmarkedArticles() {
        return ArticleFactory.fromCursorToList(
                GNRApplication.getDbHelper().getBookmarkedArticles()
        );
    }

    public static List<Article> getArticles() {
        Tag currentTag = GNRApplication.getUser().getCurrentTag();

        // If current tag is All, or there is no tag (default current tag is ALL)
        // Get all articles fromCursor database
        if (currentTag.getName().equals(TagConstants.ALL))
            return ArticleFactory.fromCursorToList(
                    GNRApplication.getDbHelper().getArticles()
            );

        // Else get articles fromCursor specified tag
        return ArticleFactory.fromCursorToList(
                GNRApplication.getDbHelper().getArticles(
                        currentTag.getName()
                )
        );
    }

    public static void refreshArticles() {
        if(NetworkUtil.checkInternetConnection()) {
            // Clear database
            GNRApplication.getDbHelper().deleteArticles();

            searchArticles(0);
        }
        else {
            NetworkUtil.showInvalidNetworkMessage();
        }
    }

    public static void moreArticles(Integer page) {
        if(NetworkUtil.checkInternetConnection()) {
             searchArticles(page);
        }
        else {
            NetworkUtil.showInvalidNetworkMessage();
        }
    }

    // Repopulate with fresh online news
    private static void searchArticles(Integer page) {
        // If user has tags, search articles for those tags
        if(!TagHelper.getTags().isEmpty()) {
            Tag currentTag = GNRApplication.getUser().getCurrentTag();

            // If user wants all tags
            if(currentTag.getName().equals(TagConstants.ALL)) {
                for (final Tag tag : TagHelper.getTags()) {
                    new ArticleSearchTask(page).execute(tag);
                }
            }
            // Else search only selected tag
            else {
                new ArticleSearchTask(page).execute(currentTag);
            }
        }
        // Else get headlines articles
        else {
            new ArticleSearchTask(page).execute();
        }
    }

    public static void saveInDataBase(Article article) {
        if(Config.DISPLAY_LOG)
            Log.d(TAG, "saveArticleInDataBase");

        GNRApplication.getDbHelper().addArticle(article);
    }

    public static void sortByDate(List<Article> articles) {
        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article article2, Article article1) {
                return (DateUtil.parse(
                            article1.getCreatedAt()))
                        .compareTo(
                                DateUtil.parse(
                                        article2.getCreatedAt()));
            }
        });
    }
}
