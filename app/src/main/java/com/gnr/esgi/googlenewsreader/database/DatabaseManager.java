package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
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

        retrieveTags();
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        for (Tag newTag : tags)
            for(Tag oldTag : _tags)
                newTag.setCounter(oldTag.getName().compareTo(newTag.getName()) == 0
                                    ? ArticleHelper.countRecentNews(newTag.getArticles(), oldTag.getArticles())
                                    : newTag.getArticles().size()
                                );

        _tags = tags;
    }

    private void retrieveTags() {
        /*
            Retrieve tags from SQLLite Database
            Add each of them to _tags
         */

        // FOR TEST
        _tags.add(new Tag("apple"));
        _tags.add(new Tag("PSG"));
        _tags.add(new Tag("Inde"));
    }

    public List<Article> findArticlesByTagId(Integer id) {
        List<Article> articleList = new ArrayList<>();

        for (Tag _tag : _tags)
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
        int count = 0;

        for (Tag tag : _tags)
            count += tag.getCounter();

        return count;
    }

    public int countLatest(Tag tag) {
        return tag.getCounter();
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

    public Article findArticleById(Integer id) {
        for(Article article : getAllArticles())
            if(article.getId().compareTo(id) == 0)
                return article;

        return null;
    }
}