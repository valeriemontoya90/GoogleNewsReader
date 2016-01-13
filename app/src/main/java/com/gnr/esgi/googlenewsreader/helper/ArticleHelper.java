package com.gnr.esgi.googlenewsreader.helper;

import com.gnr.esgi.googlenewsreader.constants.ArticleConstants;
import com.gnr.esgi.googlenewsreader.model.Article;
import com.gnr.esgi.googlenewsreader.parser.HtmlParser;

import java.util.List;

public class ArticleHelper {

    public static String getUrl(String tagName) {
        return HtmlParser.escapeSpace(ArticleConstants.KEY_API_URL + tagName);
    }

    public static int countRecentNews(List<Article> recent, List<Article> old) {
        int count = recent.size();

        for (Article recentNews : recent)
            for (Article oldNews : old)
                if(recentNews == oldNews)
                    count--;

        return count;
    }

}
