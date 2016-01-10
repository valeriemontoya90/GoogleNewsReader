package com.gnr.esgi.googlenewsreader.parser;

import com.gnr.esgi.googlenewsreader.model.Article;

public class ArticleParser {
    public static Article parse(Article article) {

        article.setTitle(HTMLParser.parse(article.getTitle()));
        article.setContent(HTMLParser.parse(article.getContent()));

        return article;
    }
}
