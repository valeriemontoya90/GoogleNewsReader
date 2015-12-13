package com.gnr.esgi.googlenewsreader.database;

import com.gnr.esgi.googlenewsreader.model.News;
import com.gnr.esgi.googlenewsreader.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ismail on 13-12-2015.
 */
public class DatabaseManager {
    private List<Tag> _tags;

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
}
