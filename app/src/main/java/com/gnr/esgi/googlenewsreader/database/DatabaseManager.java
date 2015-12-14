package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Tag;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ismail on 13-12-2015.
 */
public class DatabaseManager {
    private List<Tag> _tags;

    public DatabaseManager() {
        _tags = new ArrayList<>();

        // FOR TEST
            _tags.add(new Tag("apple"));
            _tags.add(new Tag("PSG"));
            _tags.add(new Tag("Inde"));

        NewsReader newsReader = new NewsReader(_tags);
        newsReader.execute();

        _tags = newsReader.getTags();
    }

    public List<Tag> getTags() {
        return _tags;
    }

    public void setTags(List<Tag> tags) {
        _tags = tags;
    }

    public List<News> findNewsByTagId(Integer id) {
        List<News> newsList = new ArrayList<>();

        for(Tag _tag : _tags)
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

    public void refresh() {
        new DatabaseManager();
    }
}