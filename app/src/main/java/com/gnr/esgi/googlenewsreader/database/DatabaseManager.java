package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.model.Article;
import com.gnr.esgi.googlenewsreader.model.Tag;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    private List<Tag> _tags;

    public DatabaseManager() {
        _tags = new ArrayList<>();

        // FOR TEST
            _tags.add(new Tag("apple"));
            _tags.add(new Tag("PSG"));
            _tags.add(new Tag("Inde"));
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        _tags = tags;
    }

    public List<Article> findArticlesByTagId(Integer id) {
        List<Article> articleList = new ArrayList<>();

        for(Tag _tag : _tags)
            if(_tag.getId().compareTo(id) == 0)
                articleList = _tag.getArticles();

        return articleList;
    }

    public List<Article> getAllArticles() {
        List<Article> articleList = new ArrayList<>();

        for(Tag tag : _tags)
            articleList.addAll(tag.getArticles());

        return setIndex(escapeDuplicates(articleList));
    }

    public int countLatest() {

        return 99;
    }

    private List<Article> setIndex(List<Article> articleList) {
        for(int i=0; i< articleList.size(); i++)
            articleList.get(i).setId(i);

        return articleList;
    }

    private List<Article> escapeDuplicates(List<Article> articleList) {
        Set<Article> articleSet = new LinkedHashSet<>(articleList);

        articleList.clear();
        articleList.addAll(articleSet);

        return articleList;
    }

    public Article findArticlesById(Integer id) {
        for(Article article : getAllArticles())
            if(article.getId().compareTo(id) == 0)
                return article;

        return null;
    }

    public void refresh() {
        new DatabaseManager();
    }
}