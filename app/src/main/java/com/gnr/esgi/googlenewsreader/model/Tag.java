package com.gnr.esgi.googlenewsreader.model;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ismail on 13-12-2015.
 */
public class Tag {
    private Integer _id;
    private String _name;
    private List<News> _news;

    public Tag() throws MalformedURLException {
        _id = 0;
        _name = "Tag name";
        _news = new ArrayList<News>();

        // FOR TEST
        for(int i=0; i<15; i++)
            _news.add(new News());
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public List<News> getNews() {
        return _news;
    }

    public void setNews(List<News> news) {
        _news = news;
    }
}
