package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.helper.ArticleHelper;
import com.gnr.esgi.googlenewsreader.models.Article;
import com.gnr.esgi.googlenewsreader.models.Tag;
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
        {
            for(Tag oldTag : _tags)
                newTag.setCurrentCounter(oldTag.getTagName().compareTo(newTag.getTagName()) == 0
                                ? ArticleHelper.countRecentNews(newTag.getArticlesList(), oldTag.getArticlesList())
                                : newTag.getArticlesList().size()
                );
        }

        _tags = tags;
    }

    private void retrieveTags() {
        /*
            Retrieve tags from SQLLite Database
            Add each of them to _tags

            Or use sharedPreferences
         */

        // FOR TEST
        _tags.add(new Tag("Kebab"));
        _tags.add(new Tag("PSG"));
        _tags.add(new Tag("Inde"));
    }

    public List<Article> findArticlesByTagId(Integer id) {
        List<Article> articleList = new ArrayList<>();

        for (Tag _tag : _tags)
            if(_tag.getTagId().compareTo(id) == 0)
                articleList = _tag.getArticlesList();

        return articleList;
    }

    public List<Article> getAllArticles() {
        List<Article> articleList = new ArrayList<>();

        for(Tag tag : _tags)
            articleList.addAll(tag.getArticlesList());

        return escapeDuplicates(articleList);
    }

    public int countLatest() {
        int count = 0;

        for (Tag tag : _tags)
            count += tag.getCurrentCounter();

        return count;
    }

    public int countLatest(Tag tag) {
        return tag.getCurrentCounter();
    }

    private List<Article> escapeDuplicates(List<Article> articleList) {
        Set<Article> articleSet = new LinkedHashSet<>(articleList);

        articleList.clear();
        articleList.addAll(articleSet);

        return articleList;
    }
}