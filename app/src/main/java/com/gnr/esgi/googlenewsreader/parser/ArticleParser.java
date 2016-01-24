package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.models.Article;

public class ArticleParser {
    public static Article parse(Article article) {

        article.setTitle(HtmlParser.parse(article.getTitle()));
        article.setContent(HtmlParser.parse(article.getContent()));

        return article;
    }
}
