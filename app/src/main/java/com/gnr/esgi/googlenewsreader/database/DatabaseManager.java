package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Source;
import com.gnr.esgi.googlenewsreader.model.Tag;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ismail on 13-12-2015.
 */
public class DatabaseManager {
    private List<Tag> _tags;

    public DatabaseManager() {
        _tags = new ArrayList<Tag>();

        // FOR TEST
        for(int i=0; i<5; i++) {
            Tag tag = new Tag();
            tag.setName("apple");
            _tags.add(tag);
        }

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
        List<News> newsList = new ArrayList<News>();

        for(Tag _tag : _tags)
            if(_tag.getId().compareTo(id) == 0)
                newsList = _tag.getNews();

        return newsList;
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<News>();

        for(Tag tag : _tags)
            newsList.addAll(tag.getNews());

        return setIndex(escapeDoubles(newsList));
    }

    private List<News> setIndex(List<News> newsList) {
        for(int i=0; i<newsList.size(); i++)
            newsList.get(i).setId(i);

        return newsList;
    }

    private List<News> escapeDoubles(List<News> newsList) {
        for(int i=0; i<newsList.size(); i++)
            for(int j=newsList.size()-1; j>0; j--)
                if(newsList.get(i) == newsList.get(j))
                    newsList.remove(j);

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