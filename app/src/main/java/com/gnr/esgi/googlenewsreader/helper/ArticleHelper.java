package com.gnr.esgi.googlenewsreader.helper;

import android.util.Log;

import com.gnr.esgi.googlenewsreader.GNRApplication;
import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
import com.gnr.esgi.googlenewsreader.parser.HtmlParser;
import com.gnr.esgi.googlenewsreader.utils.Config;
import com.gnr.esgi.googlenewsreader.webservices.ArticleSearchTask;

import java.util.List;

public class ArticleHelper {

    public static String getUrl(String tagName) {
        return HtmlParser.escapeSpace(ArticleConstants.BASE_API_URL + tagName);
    }

    public static int countRecentNews(List<Article> recent, List<Article> old) {
        int count = recent.size();

        for (Article recentNews : recent)
            for (Article oldNews : old)
                if(recentNews == oldNews)
                    count--;

        return count;
    }

    public static void refreshArticles() {
        // Clear database
        GNRApplication.getDbHelper().deleteAllArticles();

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
