package com.gnr.esgi.googlenewsreader.model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Integer _id;
    private String _name;
    private List<News> _news;
    private int _recentCounter;

    public Tag(String name) {
        _id = 0;
        _name = name;
        _news = new ArrayList<>();
        _recentCounter = 0;
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

    public int getCounter() {
        return _recentCounter;
    }

    public void setCounter(int count) {
        _recentCounter = count;
    }
}
