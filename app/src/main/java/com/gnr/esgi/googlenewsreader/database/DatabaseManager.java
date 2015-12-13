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
            //tag = NewsReader.readNews(tag);
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

    public ArrayList<News> getAllNews() {
        ArrayList<News> newsList = new ArrayList<News>();

        for(Tag tag : _tags)
            newsList.addAll(tag.getNews());

        return newsList;
    }

    public void refresh() {
        new DatabaseManager();
    }
}