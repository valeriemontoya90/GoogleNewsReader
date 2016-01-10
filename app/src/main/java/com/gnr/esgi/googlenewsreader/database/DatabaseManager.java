package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.helper.NewsHelper;
import com.gnr.esgi.googlenewsreader.model.News;
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
                                    ? NewsHelper.countRecentNews(newTag.getNews(), oldTag.getNews())
                                    : newTag.getNews().size()
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

    public List<News> findNewsByTagId(Integer id) {
        List<News> newsList = new ArrayList<>();

        for (Tag _tag : _tags)
            if(_tag.getId().compareTo(id) == 0)
                newsList = _tag.getNews();

        return newsList;
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();

        for(Tag tag : _tags)
            newsList.addAll(tag.getNews());

        return setIndex(escapeDuplicates(newsList));
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

    private List<News> setIndex(List<News> newsList) {
        for(int i=0; i<newsList.size(); i++)
            newsList.get(i).setId(i);

        return newsList;
    }

    private List<News> escapeDuplicates(List<News> newsList) {
        Set<News> newsSet = new LinkedHashSet<>(newsList);

        newsList.clear();
        newsList.addAll(newsSet);

        return newsList;
    }

    public News findNewsById(Integer id) {
        for(News news : getAllNews())
            if(news.getId().compareTo(id) == 0)
                return news;

        return null;
    }
}